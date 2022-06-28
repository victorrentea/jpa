package victor.training.jpa.app;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import victor.training.jpa.app.CaptureSystemOutput.OutputCapture;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.facade.TheFacade;
import victor.training.jpa.app.facade.dto.*;
import victor.training.jpa.app.util.TestDBConnectionInitializer;

import java.time.DayOfWeek;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // DANGER
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // DANGER
@SpringBootTest
@ContextConfiguration(initializers = TestDBConnectionInitializer.class) // check the DB is reachable
public class AppFlowTest {

    @Autowired
    TheFacade facade;

    Long teacherId;
    private Long subjectId;
    private long labId;

    @Test
    @Order(10)
    void createTeacher_idsAreAssignedAtPersist() {
        teacherId = facade.createTeacher(new TeacherDetailsDto()
                .setName("Richard Feynman")
                .setGrade(Teacher.Grade.PROFESSOR)
                .setCv("Long impressive CV, with a Nobel Prize"));
        assertThat(teacherId).isNotNull();
    }

    @Order(20)
    @Test
    @CaptureSystemOutput
    void createSubject_linkExistingEntity_withoutQuery(OutputCapture output) {
        subjectId = facade.createSubject(new SubjectDto()
                .setName("Algorithms")
                .setHolderTeacherId(teacherId));

        assertThat(output.toString())
                .describedAs("No query for teacher should actually be performed. @See JpaRepository#getReference")
                .doesNotContainIgnoringCase("from teacher");
    }

    @Order(30)
    @Test
    void getSubject_withLinkedTeacher() {
        SubjectDto subjectDto = facade.getSubjectById(subjectId);
        assertThat(subjectDto.getName()).isEqualTo("Algorithms");
        assertThat(subjectDto.getHolderTeacherId()).isEqualTo(teacherId);
        assertThat(subjectDto.getHolderTeacherName()).isEqualTo("Richard Feynman");
    }

    @Order(40)
    @Test
    void updateSubject() {
        facade.updateSubject(new SubjectDto()
                .setId(subjectId)
                .setName("Algorithms and Data Structures")
                .setHolderTeacherId(teacherId)
        );
        assertThat(facade.getSubjectById(subjectId).getName())
                .describedAs("Name must have been updated")
                .isEqualTo("Algorithms and Data Structures");
    }

    @Order(50)
    @Test
    @CaptureSystemOutput
    void updateSubject_doesOnlyQueryOnceForSubject_using1stLevelCache(OutputCapture output) {
        facade.updateSubject(new SubjectDto()
                .setId(subjectId)
                .setName("Algorithms and Data Structures")
                .setHolderTeacherId(teacherId)
        );
        assertThat(output.toString()).containsOnlyOnce("from subject");
    }


    @Order(60)
    @Test
    void addLabToSubject_isPersisted() {
        TimeSlotDto timeSlotDto = new TimeSlotDto(DayOfWeek.MONDAY, 9, 2, "EC105");
        labId = facade.addLabToSubject(subjectId, timeSlotDto);

        SubjectWithActivitiesDto dto = facade.getSubjectWithActivities(subjectId);
        assertThat(dto.getActivities()).isNotEmpty();
    }


    @Order(70)
    @Test
    @CaptureSystemOutput
    void getSubjectWithActivities_doesNoLazyLoading(OutputCapture output) {
        facade.getSubjectWithActivities(subjectId);

        assertThat(output.toString()).containsOnlyOnce("|select ");
    }

    @Order(75)
    @Test
    @CaptureSystemOutput
    void getTeacherById_doesNotLoadLargeCV(OutputCapture output) {
        facade.getTeacherById(teacherId);

        assertThat(output.toString()).doesNotContain(".cv "); //because it's BIG
    }

    @Order(80)
    @Test
    void assignTeacherToLab_assignTheOwnerSide() {
        facade.assignTeacherToLab(teacherId, labId);

        assertThat(facade.getAllLabs()).hasSize(1);
        assertThat(facade.getAllLabs().get(0).getTeacherNames()).hasSize(1);
    }
}
