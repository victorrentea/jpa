package victor.training.jpa.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import victor.training.jpa.app.entity.ErrorComment;
import victor.training.jpa.app.entity.ErrorLog;
import victor.training.jpa.app.entity.ErrorTag;

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

    ErrorLog errorLog = new ErrorLog("message");
    em.persist(errorLog);
    id =errorLog.getId();

    // add comments w/o cascade, persist them explicitly
    ErrorComment c1 = new ErrorComment("First Comment");
    ErrorComment c2 = new ErrorComment("Second Comment");
    errorLog.getComments().add(c1);
    errorLog.getComments().add(c2);
    em.persist(c1);
    em.persist(c2);

    // link an existing tag
    errorLog.getTags().add(beTag);

    log.info("Created log id: " + errorLog.getId());
  }

  @Transactional
  public String readFromBackend(String username) throws JsonProcessingException {
    ErrorLog fromDB = em.find(ErrorLog.class, id);
    String json = jackson.writeValueAsString(fromDB);
    log.info("JSON sent to client {}, eg on opening the EDIT screen: {}", username, json);
    return json;
  }

  @Transactional
  public void client1(String jsonFromServer) throws JsonProcessingException {
    ErrorLog copyInClient = jackson.readValue(jsonFromServer, ErrorLog.class);
    // ------- Pretend: in the browser/client -------
    log.debug("Client1 receives JSON from BE: " + jackson.writeValueAsString(copyInClient));
    copyInClient.setMessage("Client1 changed");
    // TODO change fields
    // TODO add a comment + merge parent ==> cascade
    // TODO remove a comment (private child) ==> orphanRemoval
    // TODO link to +1 / other ErrorTag
    log.debug("Client1 sends back updated JSON: " + jackson.writeValueAsString(copyInClient));
    // -------- leave the browser ---------
    em.merge(copyInClient);
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
