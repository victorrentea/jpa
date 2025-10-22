package victor.training.jpa.app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;


@Getter
@Setter

@Entity
public class Subject  {
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private boolean active;
	
	@ManyToOne // OWNER SIDE
	private Teacher holderTeacher;
	
	@OneToMany(mappedBy="subject")
	private List<TeachingActivity> activities = new ArrayList<>();
	
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;
	
	@LastModifiedBy
	private String lastModifiedBy;

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Subject() {
	}

  public Subject setHolderTeacher(Teacher holderTeacher) {
    this.holderTeacher = holderTeacher;
    return this;
  }

  public Subject(String name) {
		this.name = name;
	}
}
