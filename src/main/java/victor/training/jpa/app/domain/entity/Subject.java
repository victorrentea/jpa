package victor.training.jpa.app.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;


@Getter
@Setter
public class Subject  {
	private Long id;
	
	private String name;
	
	private boolean active;
	
	private Teacher holderTeacher;
	
	private List<TeachingActivity> activities = new ArrayList<>();
	
	private LocalDateTime lastModifiedDate;
	
	private String lastModifiedBy;

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Subject() {
	}

	public Subject(String name) {
		this.name = name;
	}
}
