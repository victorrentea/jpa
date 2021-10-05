package victor.training.jpa.app.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.app.common.data.EntityRepository;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.domain.entity.Teacher;

public interface SubjectRepo extends EntityRepository<Subject, Long> {

   @Query("SELECT s.holderTeacher  FROM Subject s WHERE s.id = ?1 AND s.holderTeacher.details.cv is not null")
   Teacher play(Long subjectId);

   @Query("SELECT s FROM Subject s INNER JOIN s.activities a WHERE s.active = true AND a.roomId=?1")
   // Subj ... Act1("EC105")   Act("EC105")
   Set<Subject> play2(String roomId);

//   @Query("SELECT s FROM Subject s LEFT JOIN FETCH s.students WHERE s.holderTeacher.name = ?1")
//   List<Subject> subiecteleProfuluiNumit(String name);


	public Subject getByName(String name);

	public List<Subject> getByHolderTeacherName(String teacherName);

	public List<Subject> getByActiveTrue();
}
