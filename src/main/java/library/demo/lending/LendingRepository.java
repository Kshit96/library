package library.demo.lending;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LendingRepository extends JpaRepository<LendingRecord,Integer> {
}
