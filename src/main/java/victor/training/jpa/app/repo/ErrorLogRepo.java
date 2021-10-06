package victor.training.jpa.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import victor.training.jpa.app.domain.entity.ErrorLog;

public interface ErrorLogRepo extends JpaRepository<ErrorLog, Long> {

   // --- calling stored procedures ---

   @Query(value = "SELECT FUNC_MESSAGES() FROM DUAL", nativeQuery = true) // causes auto-flush before
   String selectFunction();

   @Query(value = "CALL PROC_MESSAGES()", nativeQuery = true) // causes auto-flush before
   void callProcedure();

   @Query(value = "CALL FUNC_ADD(?1,?2)", nativeQuery = true) // causes auto-flush before
   int callFunctionViaJpa(int a, int b);

   @Procedure("PROC_MESSAGES") // this does NOT flush the Persistence Context
   void callProcedureViaJpa();

   @Query("SELECT m FROM ErrorLog m WHERE m.id = ?1")
   ErrorLog customFind(Long persistedId);

   @Query("SELECT e FROM ErrorLog e LEFT JOIN FETCH e.tags")
   ErrorLog queryCustomizat(Long id);
}
