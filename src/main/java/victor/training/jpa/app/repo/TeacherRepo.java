package victor.training.jpa.app.repo;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.common.CustomJpaRepository;
import victor.training.jpa.app.entity.Subject;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.entity.Teacher.Grade;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;

public interface TeacherRepo extends CustomJpaRepository<Teacher, Long>, JpaSpecificationExecutor<Teacher> {

	@Query(value = "INSERT INTO TEACHER(ID) VALUES (?1)", nativeQuery = true)
	@Modifying // requires a transaction
	@Transactional
	void nativeInsert(Long id);

	@Query(value = "30 lines long legacy queries ", nativeQuery = true)
	void nativeSEelct15yOld(Long id);

	@Query("SELECT DISTINCT a.dayOfWeek FROM Teacher t JOIN t.activities a WHERE t.id=?1")
	public Set<DayOfWeek> getBusyDaysOfTeacher(long teacherId);
	
	@Query("SELECT DISTINCT a.subject FROM Teacher t JOIN t.activities a WHERE t.id=?1")
	public Set<Subject> getSubjectsKnownByTeacher(long teacherId);

	@Query("SELECT DISTINCT t FROM TeachingActivity a JOIN a.teachers t "
			+ "WHERE a.group.id =?1 or a.year.id=(select g.year.id from StudentsGroup g where g.id=?1)")
	public Set<Teacher> getTeachersKnownByGroup(long groupId);
	
	@Query("SELECT DISTINCT a.subject FROM TeachingActivity a WHERE a.roomId=?1")
	public Set<Subject> getSubjectsInRoom(String roomId);

	// TODO make return null!
	Optional<Teacher> findByName(String name);

	@Query("SELECT t FROM Teacher t " +
		   "WHERE (:name is null OR UPPER(t.name) LIKE UPPER('%' || :name || '%'))" +
		   "AND (:grade is null OR t.grade = :grade)" +
		   "AND (cast(:teachingCourses as int) = 0 OR " +
		   "		EXISTS (SELECT 1 FROM CourseActivity c JOIN c.teachers tt WHERE tt.id = t.id) )")
	Page<Teacher> searchFixedJqpl(@Nullable String name, @Nullable Grade grade, boolean teachingCourses, Pageable pageRequest);

	@Query("SELECT t FROM Teacher t " +
		   "WHERE (:#{#criteria.name} is null OR UPPER(t.name) LIKE UPPER('%' || :#{#criteria.name} || '%'))" +
		   "AND (:#{#criteria.grade} is null OR t.grade = :#{#criteria.grade})" +
		   "AND (cast(:#{#criteria.teachingCourses} as int) = 0 OR EXISTS (SELECT 1 FROM CourseActivity c JOIN c.teachers tt WHERE tt.id = t.id) )")
	Page<Teacher> searchFixedJqplSpel(TeacherSearchCriteria criteria, Pageable pageRequest);
}
