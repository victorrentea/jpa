package victor.training.jpa.app.facade.dto;

import java.util.ArrayList;
import java.util.List;

import victor.training.jpa.app.entity.StudentsGroup;
import victor.training.jpa.app.entity.StudentsYear;

public class YearWithGroupsDto {

	public Long id;
	public String code;
	public List<StudentsGroupDto> groups = new ArrayList<>();
	
	public YearWithGroupsDto() {
	}
	public YearWithGroupsDto(StudentsYear year) {
		id = year.getId();
		code = year.getCode();
		for (StudentsGroup group : year.getGroups()) {
			groups.add(new StudentsGroupDto(group));
		}
	}

}
