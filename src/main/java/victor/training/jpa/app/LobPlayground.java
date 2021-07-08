package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.hibernate.engine.jdbc.ClobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import victor.training.jpa.app.domain.entity.TeacherDetails;
import victor.training.jpa.app.repo.TeacherDetailsRepo;

import java.io.*;
import java.sql.Clob;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
@Slf4j
public class LobPlayground {
   private final TeacherDetailsRepo repo;
   private Long id;

   @Transactional
   public void uploadLargeClob() throws IOException {
      File file = new File("pom.xml");
      if (!file.isFile()) {
         throw new IllegalArgumentException("File not found: " + file.getAbsolutePath());
      }
      try (FileReader fileReader = new FileReader(file)) {
         Clob clob = ClobProxy.generateProxy(fileReader, file.length());
         TeacherDetails entity = new TeacherDetails().setCv(clob);
         id = repo.save(entity).getId();
         repo.flush();
         publisher.publishEvent(new DeleteFileEvent(file));
      }
   }

   @Autowired
   ApplicationEventPublisher publisher;
   @Value
   static class DeleteFileEvent {
      private final File toDelete;
//      private final Reader reader;
   }

   @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
   public void deRulatLaFinal(DeleteFileEvent event) {
//      reader.c
      event.toDelete.delete();
   }

   @Transactional
   public void downloadLargeClob() throws IOException, SQLException {
      TeacherDetails teacherDetails = repo.findById(id).get();

      IOUtils.copy(teacherDetails.getCv().getCharacterStream(), new OutputStreamWriter(System.out));
   }
}
