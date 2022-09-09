package victor.training.jpa.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.ErrorComment;
import victor.training.jpa.app.entity.ErrorLog;
import victor.training.jpa.app.entity.ErrorTag;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.ErrorTagRepo;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@Slf4j
public class MergePlayground {
   private final ErrorTagRepo errorTagRepo;
   private final ErrorLogRepo errorLogRepo;
   private final EntityManager em;

   private ErrorTag feTag;
   private ErrorTag beTag;
   private Long logId;
   private ObjectMapper jackson = new ObjectMapper();

   {
      jackson.registerModule(new JavaTimeModule());
      jackson.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
   }

   @Transactional
   public void persistInitialData() {
      feTag= errorTagRepo.save(new ErrorTag("FE"));
      beTag= errorTagRepo.save(new ErrorTag("BE"));
      ErrorLog errorLog = new ErrorLog("message");
      errorLog.setCreatedTime(LocalDateTime.now());
      errorLog.getComments().add(new ErrorComment("First Comment"));
      errorLog.getComments().add(new ErrorComment("Second Comment"));
      errorLog.getTags().add(beTag);
      logId = errorLogRepo.save(errorLog).getId();


   }

   @Transactional
   // ne inchipuim ca asta e un endpoint REST care intoarce un JSON cu datele acestui ErrorLog
   public String readFromBackend(String username) throws JsonProcessingException {
      ErrorLog fromDB = errorLogRepo.findById(logId).get();
      String json = jackson.writeValueAsString(fromDB);
      log.info("JSON sent to client {}, eg on opening the EDIT screen: {}",username, json);
      return json;
   }

   @Transactional
   public void client1(String jsonReceivedFromServer) throws JsonProcessingException {
      ErrorLog copyInClient = jackson.readValue(jsonReceivedFromServer, ErrorLog.class);
      // ------- enter the browser -------
      log.debug("Client1 receives JSON from BE: " + jackson.writeValueAsString(copyInClient));
      copyInClient.setMessage("Client1 changed"); // changed message
      //        change fields MERGE e usor.
      copyInClient.getComments().remove(0);
      copyInClient.getComments().get(0).setText("Copil changed");
      copyInClient.getComments().add(new ErrorComment("Cevanou"));
      // TODO add a comment + merge parent ==> cascade
      // TODO remove a comment (private child) ==> orphanRemoval
      log.debug("Client1 sends back updated JSON: " + jackson.writeValueAsString(copyInClient));
      // -------- leave the browser ---------
      System.out.println("time=  " +copyInClient.getCreatedTime());
// inainte de merge, restaurezi coloanele ce NU aveau cum sa se modifice din FE
      ErrorLog errorLog = errorLogRepo.findById(copyInClient.getId()).orElseThrow();
      copyInClient.setCreatedTime(errorLog.getCreatedTime()); // restaurezi datele vechi. pe parinte si pe copii!
      errorLogRepo.save(copyInClient); // aici salvez in DB obiectul modificat primit din FE
      // merge problems:
      // 1 coloane readonly pt FE: nu le primesti
      // 2 coloane invizibile nu le trimiti
      // ==> daca te superi pe merge:
      // faci e=r.find si apoi e.setCeSaSchimbat() repo.save()
   }
   // TODO concurrent access:
   //    1)  add @Version for optimistic locking
   //    1)  set 'underEditBy' for pesimistic locking

   @Transactional
   public void client2(String jsonReceivedFromServer) throws JsonProcessingException {
      ErrorLog copyInClient = jackson.readValue(jsonReceivedFromServer, ErrorLog.class);
      // ------- enter the browser -------
      log.debug("Client2 receives JSON from BE: " + jackson.writeValueAsString(copyInClient));
      copyInClient.setMessage("Client2 changed"); // changed message
      log.debug("Client2 sends back updated JSON: " + jackson.writeValueAsString(copyInClient));
      // -------- leave the browser ---------
      errorLogRepo.save(copyInClient);
   }


   @Transactional
   public void printFinalData() {
      ErrorLog errorLog = errorLogRepo.findById(logId).orElseThrow();
      log.debug(errorLog.toString());
   }
}
