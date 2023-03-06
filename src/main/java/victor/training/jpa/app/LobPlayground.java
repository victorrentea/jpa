package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.hibernate.engine.jdbc.ClobProxy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.entity.TeacherDetails;
import victor.training.jpa.app.repo.TeacherDetailsRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import java.io.*;
import java.sql.Clob;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
@Slf4j
public class LobPlayground {
   private final TeacherDetailsRepo repo;
   private final TeacherRepo teacherRepo;
   private Long teacherId;

   @Transactional
   public Long uploadLargeClob() throws IOException {
      File file = new File("pom.xml");
      if (!file.isFile()) {
         throw new IllegalArgumentException("File not found: " + file.getAbsolutePath());
      }
      FileReader fileReader = new FileReader(file);
//      Clob clob = ClobProxy.generateProxy(fileReader, file.length());
      String allContents = IOUtils.toString(fileReader);
//      file.delete();
      TeacherDetails details = new TeacherDetails().setCv(allContents);
      teacherId = teacherRepo.save(new Teacher().setDetails(details)).getId();
      return repo.save(details).getId();
   }
   @Transactional
   public void downloadLargeClob(Long uploadId) throws IOException, SQLException {
      TeacherDetails teacherDetails = repo.findById(uploadId).get();
      System.out.println("Loaded teacher details");
      IOUtils.write(teacherDetails.getCv(), new OutputStreamWriter(System.out));
   }

   public void getTeacherAutoLoadsTheClob() {
      Teacher teacher = teacherRepo.findOneById(teacherId);
      System.out.println("Loaded teacher");
      System.out.println("CV = " + teacher.getDetails().getCv());
   }
}
