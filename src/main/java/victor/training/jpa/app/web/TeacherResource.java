package victor.training.jpa.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import victor.training.jpa.app.domain.entity.Teacher;
import victor.training.jpa.app.facade.dto.TeacherDto;
import victor.training.jpa.app.repo.TeacherDao;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TeacherResource {
    @Autowired
    private TeacherDao repo;

    @PostMapping("/teachers/search")
    public List<TeacherDto> searchTeachers(@RequestBody  TeacherSearchCriteria criteria) {
        List<Teacher> teachers = repo.search(criteria);
        return teachers.stream().map(TeacherDto::new).collect(Collectors.toList());
    }
}


