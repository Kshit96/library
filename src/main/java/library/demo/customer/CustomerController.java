package library.demo.customer;

import library.demo.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/library")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/fetchAllCustomers")
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }
}
