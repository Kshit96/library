package library.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import library.demo.customer.Customer;
import library.demo.customer.CustomerRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
public class CustomerControllerTest {

    @Autowired
    MockMvc app;

    @Autowired
    CustomerRepository customerRepository;

    @After
    public void tearUp(){
        customerRepository.deleteAll();
    }

    @Test
    public void fetchAll_shouldfetchAllCustomersInTheLibrary_whenInvoked() throws Exception{
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/1980");
        java.sql.Date dateFormatted = new java.sql.Date(date.getTime());
        Customer customer1 = new Customer("Kshitiz", 23, 'M', dateFormatted);
        Customer customer2 = new Customer("Aaron", 25, 'M', dateFormatted);
        List<Customer> expectedListOfCustomers = Arrays.asList(customer1,customer2);
        ObjectMapper mapper = new ObjectMapper();

        customerRepository.save(customer1);
        customerRepository.save(customer2);

        app.perform(get("/library/fetchAllCustomers"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedListOfCustomers)));

    }


}
