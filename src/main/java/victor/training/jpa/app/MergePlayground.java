package victor.training.jpa.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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


   @Transactional
   public void persistInitialData() {
      feTag= errorTagRepo.save(new ErrorTag("FE"));
      beTag= errorTagRepo.save(new ErrorTag("BE"));
      ErrorLog errorLog = new ErrorLog("message");
      errorLog.getComments().add(new ErrorComment("First Comment"));
      errorLog.getComments().add(new ErrorComment("Second Comment"));
      errorLog.getTags().add(beTag);
      logId = errorLogRepo.save(errorLog).getId();
   }

   @Transactional
   public String readFromBackend(String username) throws JsonProcessingException {
      ErrorLog fromDB = errorLogRepo.findById(logId).get();
      String json = jackson.writeValueAsString(fromDB);
      log.info("JSON sent to client {}, eg on opening the EDIT screen: {}",username, json);
      return json;
   }

   @Transactional
   public void user1Browser(String jsonReceivedFromServer) throws JsonProcessingException {
      ErrorLog copyInClient = jackson.readValue(jsonReceivedFromServer, ErrorLog.class);
      // ------- enter the browser ------- JavaScript:
      log.debug("Client1 receives JSON from BE: " + jackson.writeValueAsString(copyInClient));
      copyInClient.setMessage("User1 changed the message");
      copyInClient.getComments().get(0).setText("Comment changed");
      copyInClient.getComments().remove(1);
      copyInClient.getComments().add(new ErrorComment("One More"));
      // TODO change fields
      // TODO add a comment + merge parent ==> cascade
      // TODO remove a comment (private child) ==> orphanRemoval
      // TODO link to +1 / other ErrorTag
      log.debug("Client1 clicks the 'save' button updated JSON: " + jackson.writeValueAsString(copyInClient));
      // -------- leave the browser ---------
      errorLogRepo.save(copyInClient); // inside, EntityManager#merge is called
   }
   // TODO concurrent access:
   //    1)  add @Version for optimistic locking
   //    1)  set 'underEditBy' for pesimistic locking


   @Transactional
   public void printFinalData() {
      ErrorLog errorLog = errorLogRepo.findById(logId).orElseThrow();
      log.debug(errorLog.toString());
//      errorLogRepo.findAll().forEach(errorLogRepo::delete);
   }
}
















