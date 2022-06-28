package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.hibernate.engine.jdbc.ClobProxy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
      FileReader fileReader = new FileReader(file);
//      Clob clob = ClobProxy.generateProxy(fileReader, file.length());
      String allContents = IOUtils.toString(fileReader);
      TeacherDetails entity = new TeacherDetails().setCv(allContents);
      id = repo.save(entity).getId();
   }
   @Transactional
   public void downloadLargeClob() throws IOException, SQLException {
      TeacherDetails teacherDetails = repo.findById(id).get();
      System.out.println("Loaded teacher details");
      IOUtils.write(teacherDetails.getCv(), new OutputStreamWriter(System.out));
   }
}
