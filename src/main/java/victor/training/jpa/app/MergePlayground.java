package victor.training.jpa.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import victor.training.jpa.app.entity.ErrorComment;
import victor.training.jpa.app.entity.ErrorLog;
import victor.training.jpa.app.entity.ErrorTag;
import victor.training.jpa.app.util.MyUtil;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MergePlayground {
  private final EntityManager em;
  private static final ObjectMapper jackson = new ObjectMapper();
  private Long id;

  @Transactional
  public void persistInitialData() {
    ErrorTag feTag = new ErrorTag("FE");
    ErrorTag beTag = new ErrorTag("BE");
    em.persist(feTag);
    em.persist(beTag);

    ErrorLog errorLog = new ErrorLog("message 🍑")
        .setCreatedBy("bob");
    em.persist(errorLog);
    id =errorLog.getId();

    // add comments w/o cascade, persist them explicitly
    errorLog.getComments().add(new ErrorComment("First Comment"));
    errorLog.getComments().add(new ErrorComment("Second Comment"));

    // link an existing tag
    errorLog.getTags().add(beTag);

    log.info("Created log id: " + errorLog.getId());
  }

//  @Transactional // epic fail. ca doar citesti, da frameworkul cere pt lazy load.
  public String readFromBackend() throws JsonProcessingException {
    ErrorLog fromDB = em.find(ErrorLog.class, id);
    String json = jackson.writeValueAsString(fromDB);
    log.info("JSON sent to client eg on opening the EDIT screen: {}", json);
    return json;
  }

  @SneakyThrows
  @Transactional
  public void client1(String jsonFromServer, String change)  {
    // ------- Pretend: in the browser/client/android -------
    ErrorLog copyInClient = jackson.readValue(jsonFromServer, ErrorLog.class);
    log.debug("Client1 receives JSON from BE: " + jackson.writeValueAsString(copyInClient));
    copyInClient.setMessage(change);

    // copchii
    copyInClient.getComments().get(0).setText("EDITED"); //UPDATE
    copyInClient.getComments().remove(1); // DELETE merge daca orphanRemoval = true
    copyInClient.getComments().add(new ErrorComment("e de la tehnician")); //INSERT

//    copyInClient.setCreatedBy("alice");// ilegal
    // ! repara greasa PENIBILA de a PRIMI in JSON DTO 'createdBy' la update.
    copyInClient.setCreatedBy(null);// pt ca nu exista un asa camp in UpdateErrorLogRequestDto

    // TODO link to +1 / other ErrorTag
    log.debug("Client1 sends back updated JSON: " + jackson.writeValueAsString(copyInClient));
    // -------- leave the browser --------- toJson-->fromJson
    log.debug("PE SERVER: Urmeaza Merge la ce am creat din JSONul primit din client");
    // a) @Column(updatable=false)
    // b) setCreatedBy ignora/throws daca deja e setat campu

    // daca pe acest flux nu se poate modifica un camp, dar pe altele da
    ErrorLog oldDinDB = em.find(ErrorLog.class, id);
    em.lock(oldDinDB, LockModeType.PESSIMISTIC_WRITE);
    copyInClient.setCreatedBy(oldDinDB.getCreatedBy());
    log.info("Acum sleep (niste business logic, ev API calls)...");
    MyUtil.sleepMillis(1000);
    oldDinDB.counter ++;

    em.merge(copyInClient); // = OVERWRITE TOATE CAMPURILE
    em.flush();
  }
  // TODO concurrency control:
  //    1)  add @Version for optimistic locking
  //    1)  set 'underEditBy' for pesimistic locking

  @Transactional
  public void printFinalData() {
    ErrorLog errorLog = em.find(ErrorLog.class, id);
    if (errorLog == null) throw new IllegalStateException("ErrorLog not found for id=" + id);
    log.debug(errorLog.toString());
  }
}
