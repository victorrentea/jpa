package victor.training.jpa.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.*;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.ErrorTagRepo;
import victor.training.jpa.app.repo.SubjectRepo;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.Arrays;
import java.util.Collections;

@RequiredArgsConstructor
@Component
@Slf4j
public class MergePlayground {
   private final ErrorTagRepo errorTagRepo;
   private final ErrorLogRepo errorLogRepo;
   private final EntityManager em;
   private String json; // imagine sent in frontend/other system

   private ErrorTag feTag;
   private ErrorTag beTag;
   private Long logId;
   private ObjectMapper jackson = new ObjectMapper();


   @Transactional
   public void persistInitialData() {
      feTag= errorTagRepo.save(new ErrorTag("FE")); // globale
      beTag= errorTagRepo.save(new ErrorTag("BE"));

      logId = errorLogRepo.save(new ErrorLog("Buuum")
          .setTags(Collections.singleton(feTag))
         .setComments(Arrays.asList(new ErrorComment("Jale mare de modificat!"), new ErrorComment("Mesaj2 De sters!")))
      ).getId();
   }

   @Transactional
   public void readFromDb() throws JsonProcessingException {
      ErrorLog fromDB = errorLogRepo.findById(logId).get();
      json = jackson.writeValueAsString(fromDB);
      log.info("JSON sent to client(s), eg on opening the EDIT screen: " + json);
   }

   @Transactional
   public void client1() throws JsonProcessingException {
      // dupa 1 min de gandire cu ecranul de EDIT in fata, userul apasa butonul "save"
      ErrorLog copy1 = jackson.readValue(json, ErrorLog.class); // pute a JS
      copy1.setMessage("Diferit");
      copy1.setI(42);
      copy1.getComments().get(0).setText("AltMesajInitial");
      copy1.getComments().remove(1);
      copy1.getComments().add(new ErrorComment("Necrolog"));

      copy1.getTags().clear();
      copy1.getTags().add(beTag);
      copy1.setMainTag(feTag);

//      copy1.
      // TODO change collections
      log.debug("Client1 sends back updated JSON: " + jackson.writeValueAsString(copy1));
      copy1 = errorLogRepo.save(copy1);
      em.lock(copy1, LockModeType.PESSIMISTIC_WRITE);
   }

//   public void client2() throws JsonProcessingException {
//      // dupa 2 min de gandire cu ecranul de EDIT in fata, userul apasa butonul "save"
//      ErrorLog copy2 = jackson.readValue(json, ErrorLog.class); // pute a JS
//      copy2.setMessage("Altfel");
//      log.debug("Client2 sends back updated JSON: " + jackson.writeValueAsString(copy2));
//      errorLogRepo.save(copy2);
//   }
   // TODO concurrent access ?
}
