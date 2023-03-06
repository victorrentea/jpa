package victor.training.jpa.app.web;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.Sort.Direction.ASC;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.facade.TheFacade;
import victor.training.jpa.app.facade.dto.ContactChannelDto;
import victor.training.jpa.app.facade.dto.TeacherDetailsDto;
import victor.training.jpa.app.facade.dto.TeacherDto;
import victor.training.jpa.app.facade.dto.TeacherSearchCriteria;
import victor.training.jpa.app.repo.TeacherRepo;
import victor.training.jpa.app.repo.TeacherSearchRepo;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
	private final TeacherRepo teacherRepo;
	private final TheFacade facade;
	
	@GetMapping
	public List<TeacherDto> getAll() {
		return teacherRepo.findAll().stream().map(TeacherDto::new).collect(toList());
	}
	
	@PostMapping
	public Long create(@RequestBody TeacherDetailsDto dto) {
		return facade.createTeacher(dto);
	}
	
	@GetMapping("{teacherId}")
	public TeacherDto getById(@PathVariable Long teacherId) {
		return facade.getTeacherById(teacherId);
	}
//	@GetMapping("{teacherId}/details")
//	public TeacherDetailsDto getByIdWithDetails(@PathVariable Long teacherId) {
//		return facade.getTeacherWithDetails(teacherId);
//	}

	@GetMapping("{teacherId}/channels")
	public List<ContactChannelDto> getTeacherContactChannels(@PathVariable long teacherId) {
		return facade.getTeacherChannels(teacherId);
	}
	@PutMapping("{teacherId}/channels")
	public void setTeacherContactChannels(@PathVariable long teacherId, @RequestBody List<ContactChannelDto> channelDtos) {
		facade.setTeacherChannels(teacherId, channelDtos);
	}

	@GetMapping("/by-name/{name}")
	public void searchByName(@PathVariable String name) {
		System.out.println("Teacher: " + teacherRepo.findByName("Test"));
	}

	@PostMapping("search")
	public List<Teacher> search(@RequestBody TeacherSearchCriteria criteria) {
		PageRequest pageRequest = PageRequest.of(0, 10, ASC, "name");
		return teacherSearchRepo.specifications(criteria, pageRequest);
	}

	private final TeacherSearchRepo teacherSearchRepo;
	
}
