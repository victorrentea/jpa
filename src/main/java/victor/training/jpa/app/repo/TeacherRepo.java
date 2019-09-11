package victor.training.jpa.app.repo;

import java.time.DayOfWeek;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import victor.training.jpa.app.common.data.EntityRepository;
import victor.training.jpa.app.domain.entity.Subject;
import victor.training.jpa.app.domain.entity.Teacher;

public interface TeacherRepo extends EntityRepository<Teacher, Long> {
    @Query("FROM Teacher t LEFT JOIN FETCH t.heldSubjects")
    Set<Teacher> findAllFetchingSubjects();


//	public ? getBusyDaysOfTeacher(long teacherId);
//
//	public ? getSubjectsKnownByTeacher(long teacherId);
//
//	public ? getTeachersKnownByGroup(long groupId);
//
//	public ? getSubjectsInRoom(String roomId);
//
	Optional<Teacher> findByName(String name);

}
