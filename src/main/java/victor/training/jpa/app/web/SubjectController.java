package victor.training.jpa.app.web;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import victor.training.jpa.app.facade.TheFacade;
import victor.training.jpa.app.facade.dto.SubjectDto;
import victor.training.jpa.app.repo.SubjectRepo;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

	@Autowired
	private TheFacade facade;

	@Autowired
	private SubjectRepo subjectRepo;
	
	@GetMapping
	public List<SubjectDto> getAll() {
		return subjectRepo.findAll().stream().map(SubjectDto::new).collect(toList());
	}
	
	@GetMapping("{subjectId}")
	public SubjectDto getById(@PathVariable Long subjectId) {
		return facade.getSubjectById(subjectId);
	}
	
	@PostMapping
	public Long create(@RequestBody SubjectDto subjectDto) {
		return facade.createSubject(subjectDto);
	}

	@GetMapping("search")
	public SubjectDto getByName(@RequestParam("name") String name) {
		return new SubjectDto(subjectRepo.getByName(name));
	}

	@GetMapping("open-edit/{id}")
	public SubjectDto openEdit(@PathVariable long id) {
		return facade.openUpdate(id);
	}
	
	@PutMapping
	public void update(@RequestBody SubjectDto subjectDto) {
//		facade.updateSubject(subjectDto);
	}

}
