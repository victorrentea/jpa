package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.CaptureSystemOutput.OutputCapture;
import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.repo.TeacherRepo;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LazyAttributeTest {
    @Autowired
    private LazyAttribute lazyAttribute;
    @Autowired
    private TeacherRepo teacherRepo;
    private Long id;

    @BeforeEach
    final void before() {
        id = teacherRepo.save(new Teacher()).getId();
    }

    @Test
    @CaptureSystemOutput
    void loaded(OutputCapture capture) {
        capture.reset();
        lazyAttribute.lazyLoadAttribute(id);

        assertThat(capture.toString()).containsIgnoringCase("lazy_attribute");
    }

    @Test
    @CaptureSystemOutput
    void notLoaded(OutputCapture capture) {
        capture.reset();
        lazyAttribute.noAccessToAttribute(id);

        // only passes when ran from 'mvn test', as the 'enhance' goal must be processed by mvn. in other words, IntelliJ does not run the plugin before launching the test.
        // https://intellij-support.jetbrains.com/hc/en-us/community/posts/206191209-Intellij-doesn-t-build-my-maven-projects-using-configured-plugins
        // https://docs.jboss.org/hibernate/orm/6.1/userguide/html_single/Hibernate_User_Guide.html#tooling-enhancement
        // https://github.com/spring-projects/spring-boot/issues/22109

        assertThat(capture.toString()).doesNotContainIgnoringCase("lazy_attribute");
    }
}

@RequiredArgsConstructor
@Service
class LazyAttribute {
    private final TeacherRepo teacherRepo;

    @Transactional
    public void lazyLoadAttribute(Long teacherId) {
        Teacher teacher = teacherRepo.findOneById(teacherId);
        System.out.println(teacher.getLazyAttribute());
    }

    @Transactional
    public void noAccessToAttribute(Long teacherId) {
        Teacher teacher = teacherRepo.findOneById(teacherId);
        System.out.println("Is teacher class proxied ? " + teacher.getClass());
    }

}
