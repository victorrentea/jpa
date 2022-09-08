package victor.training.jpa.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import victor.training.jpa.app.entity.ErrorLog;

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

//    boolean findByMessage(String name);

   @Query("FROM ErrorLog e WHERE e.message = ?1")
    boolean finderumeuCasSmecher(String name);

   @Query(value = "SELECT COUNT(*) FROM ERROR_LOG", nativeQuery = true)
   Long findByNativ(String aNull);
}
