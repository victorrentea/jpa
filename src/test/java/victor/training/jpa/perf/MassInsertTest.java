package victor.training.jpa.perf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toList;

@SpringBootTest
@Transactional
@Rollback(false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MassInsertTest {
  private static final Logger log = LoggerFactory.getLogger(MassInsertTest.class);

  @Autowired
  private IDDocumentRepo documentRepo;
  @Autowired
  private IDDocumentTypeRepo documentTypeRepo;
  private List<Long> docTypeIds;

  @BeforeEach
  final void before() {
    List<IDDocumentType> docTypes = IntStream.range(1, 20).mapToObj(i -> "DocType" + i).map(IDDocumentType::new).collect(toList());
    docTypeIds = documentTypeRepo.saveAll(docTypes).stream().map(IDDocumentType::getId).collect(toList());
    TestTransaction.end(); // flush and close the Persistence Context
  }

  @Test
  public void assignIdentifiers() {
    long t0 = currentTimeMillis();
    for (int page = 0; page < 20; page++) {
      TestTransaction.start();
      log.debug("--- PAGE " + page);
      for (int i = 0; i < 100; i++) {
        IDDocument document = new IDDocument();
        Long docTypeId = docTypeIds.get(i % docTypeIds.size());
        document.setType(documentTypeRepo.findById(docTypeId).orElseThrow());
        documentRepo.save(document);
      }
      TestTransaction.end(); // flush and close the Persistence Context
    }
    long t1 = currentTimeMillis();
    log.debug("Took {} ms (naive)", t1 - t0);

    // TODO FK to doctype
    // TODO docTypeId = docTypeRepo.findByName(""): preload a Map<String, Long> docTypeNameToId
    // TODO batching inserts
    // TODO identifiers: Sequence size (@see gaps!), IDENTITY, UUID
  }
}
