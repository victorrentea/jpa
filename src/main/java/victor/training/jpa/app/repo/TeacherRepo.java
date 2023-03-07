package victor.training.jpa.app.repo;

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

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TeacherRepo extends CustomJpaRepository<Teacher, Long>,
        JpaSpecificationExecutor<Teacher> // implement this and you have methods taking Specifications from you
{

  @Query(value = "INSERT INTO TEACHER(ID) VALUES (?1)", nativeQuery = true)
  @Modifying // requires a transaction
  @Transactional
  void nativeInsert(Long id);

  @Query(value = "30 lines long legacy queries ", nativeQuery = true)
  void nativeSEelct15yOld(Long id);


  // JPQL equivalent of an SQL UNION between teachers
  @Query("SELECT t FROM TeachingActivity a JOIN a.teachers t WHERE "
         + "a.id IN (SELECT c.id FROM StudentsYear y JOIN y.courses c WHERE y.id = :yearId) "
         + "OR a.id IN (SELECT lab.id FROM StudentsYear y JOIN y.groups g JOIN g.labs lab WHERE y.id = :yearId)")
  List<Teacher> getAllTeachersForYear(long yearId);

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

  // ❤️ validated by JPA at startup (it's static a @Query)
  // + performance: the statement having the same form SQL it remains in the statement cache of RDMS Oracle
  @Query("SELECT t FROM Teacher t " +
         "WHERE (:name is null OR UPPER(t.name) LIKE UPPER('%' || :name || '%'))" +
         "AND (:grade is null OR t.grade = :grade)" +
         "AND (cast(:teachingCourses as int) = 0 OR " +
         "		EXISTS (SELECT 1 FROM CourseActivity c JOIN c.teachers tt WHERE tt.id = t.id) )")
  Page<Teacher> searchFixedJqpl(@Nullable String name,
                                @Nullable Grade grade,
                                boolean teachingCourses,  // Cons: too many parameters to this method
                                Pageable pageRequest); // allows Spring pagination

  @Query("SELECT t FROM Teacher t " +
         "WHERE (:#{#criteria.name} is null OR UPPER(t.name) LIKE UPPER('%' || :#{#criteria.name} || '%'))" +
         "AND (:#{#criteria.grade} is null OR t.grade = :#{#criteria.grade})" +
         "AND (cast(:#{#criteria.teachingCourses} as int) = 0 OR EXISTS (SELECT 1 FROM CourseActivity c JOIN c.teachers tt WHERE tt.id = t.id) )")
  Page<Teacher> searchFixedJqplSpel(TeacherSearchCriteria criteria, Pageable pageRequest);
  // Uses Spring Expression Language to read properties of the 'criteria' parameter
}


// if the app will have COMPLEX queries (static or dynamic), mid-sr team that want to learn ->
    //  QueryDSL, or Specifications
// if you have dynamic queries => fixed JPQL (above)
// if the UI has to compose OR AND the criteria => Specifications
// if mid-jr team: jpql+=