package victor.training.jpa.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import victor.training.jpa.app.domain.entity.ErrorLog;

import java.util.List;

public interface ErrorLogRepo extends JpaRepository<ErrorLog, Long> {

   List<ErrorLog> findByMessageLike(String part);

   @Query("FROM ErrorLog where id = :id")
   ErrorLog findLikeAHipster(@Param("id") long id);
}
