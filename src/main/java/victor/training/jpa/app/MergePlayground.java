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
      ErrorLog errorLog = new ErrorLog("message")
              .setCreationUser("Real author");
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
      copyInClient.setCreationUser("ThatGuyIHate");
      Long removedChildId = copyInClient.getComments().remove(1).getId();
      copyInClient.getComments().add(new ErrorComment("One More"));
//      copyInClient.getComments().add(new ErrorComment("In fact, I want this instead").setId(removedChildId));

      // TODO change fields
      // TODO add a comment + merge parent ==> cascade
      // TODO remove a comment (private child) ==> orphanRemoval
      // TODO link to +1 / other ErrorTag
      log.debug("Client1 clicks the 'save' button updated JSON: " + jackson.writeValueAsString(copyInClient));
      // -------- leave the browser ---------
//      consider copyInClient as a @RequestBody given to you by spring

      // dangers of relying on .merge like this:
      // in other words, why is it dangerous to just take the updated @Entity from the FE and
      // repo.save(it) over the previous

      // #1 concurrent modifications (between they GET the data to edit and they PUT back it to BE, someone else did that too)


      // #2 data in the @Entity that the FE should never change/see
      //FixA: Usual workaround to 'freeze' some fields in the entity by copying them from what's NOW in the DB
      ErrorLog original = errorLogRepo.findById(copyInClient.getId()).orElseThrow();
      copyInClient.setLastModifiedBy(original.getLastModifiedBy());
      //FixB: AVOID repo.save(). instead, you do e = findById(); then e.setField1(..); e.setField2(..)

      errorLogRepo.save(copyInClient); // inside, EntityManager#merge is called = overwrite all FIELDS
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
















