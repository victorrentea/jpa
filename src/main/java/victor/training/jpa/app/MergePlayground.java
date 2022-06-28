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
   public void client1(String json) throws JsonProcessingException {
      ErrorLog copy1 = jackson.readValue(json, ErrorLog.class);
      log.debug("Client1 receives JSON from BE: " + jackson.writeValueAsString(copy1));
      // TODO change fields
      // TODO add a comment + merge parent ==> cascade
      // TODO remove a comment (private child) ==> orphanRemoval
      // TODO link to +1 / other ErrorTag
      log.debug("Client1 sends back updated JSON: " + jackson.writeValueAsString(copy1));
      errorLogRepo.save(copy1);
   }
   // TODO concurrent access:
   //    1)  add @Version for optimistic locking
   //    1)  set 'underEditBy' for pesimistic locking
}
