package victor.training.jpa.app;

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
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JpaPlayground {
   private final TeacherRepo teacherRepo;

   public void play() throws Exception {
   }
}
