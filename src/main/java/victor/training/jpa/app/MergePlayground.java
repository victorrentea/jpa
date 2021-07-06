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
      feTag= errorTagRepo.save(new ErrorTag("FE"));
      beTag= errorTagRepo.save(new ErrorTag("BE"));

      logId = errorLogRepo.save(new ErrorLog("Buuum")).getId();
   }

   @Transactional
   public void readFromDb() throws JsonProcessingException {
      ErrorLog fromDB = errorLogRepo.findById(logId).get();
      json = jackson.writeValueAsString(fromDB);
      log.info("JSON sent to client(s), eg on opening the EDIT screen: " + json);
   }
   @Transactional
   public void client1() throws JsonProcessingException {
      ErrorLog copy1 = jackson.readValue(json, ErrorLog.class);
      // TODO change fields
      // TODO change collections
      log.debug("Client1 sends back updated JSON: " + jackson.writeValueAsString(copy1));
      errorLogRepo.save(copy1);
   }
   // TODO concurrent access ?
}
