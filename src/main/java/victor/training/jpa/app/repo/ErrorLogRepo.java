package victor.training.jpa.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ErrorLog;

public interface ErrorLogRepo extends JpaRepository<ErrorLog, Long> {
   @Query(value = "SELECT count(*) FROM dual", nativeQuery = true)
   int cheamaProcedura();

   @Query("SELECT COUNT(e) FROM ErrorLog e ")
   int findMy();

   @Query(value = "SELECT FUN_MESSAGES_CSV() FROM DUAL", nativeQuery = true) // causes auto-flush before
   String selectFunction();

   @Query(value = "CALL PROC_MESSAGES()", nativeQuery = true) // causes auto-flush before
   void callProcedure();

   @Procedure("PROC_MESSAGES") // this does NOT flush the Persistence Context
   void callProcedureViaJpa();

   @Modifying(clearAutomatically = true)
   @Query("UPDATE ErrorLog e SET e.message = 'BULK' ")
   void bulkUpdate();
}
