package victor.training.jpa.app;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.entity.TeacherDetails;
import victor.training.jpa.app.repo.TeacherDetailsRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JpaPlayground {
   private final TeacherRepo teacherRepo;

   public void play() throws Exception {
      Teacher teacher = new Teacher();
      teacher.setName("John Doe");
      TeacherDetails teacherDetails = new TeacherDetails();
      teacherDetails.setCv("BETON");
      teacher.setDetails(teacherDetails);
      log.info("Before save: {}", teacher.getId());
      teacherRepo.save(teacher); // intoarce entity modificata, dupa setId pe ea , aici == teacher
      log.info("After save: {}", teacher.getId());
   }
}
