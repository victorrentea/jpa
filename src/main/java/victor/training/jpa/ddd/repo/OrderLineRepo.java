package victor.training.jpa.ddd.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.ddd.model.OrderLine;

public interface OrderLineRepo extends JpaRepository<OrderLine, Long> {
  @Modifying
  @Query("DELETE FROM Order")  //  JPQL
//  @Query(value = "DELETE FROM Order cascade ", nativeQuery = true) // SQL
  void deleteAllOrders();
}

// traditional way: repo.delete()

