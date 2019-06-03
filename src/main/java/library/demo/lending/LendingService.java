package library.demo.lending;


public interface LendingService {

    void borrowBook(LendingRecord lendingRecord);
    void returnBook(LendingRecord lendingRecord);

}
