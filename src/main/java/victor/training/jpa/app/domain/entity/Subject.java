package victor.training.jpa.app.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import victor.training.jpa.app.util.MyTrackingEntityListener;
import victor.training.jpa.app.util.MyTrackingEntityListener.Trackable;


@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) // second level cache not transaction-bound but application-global
public class Subject {

	enum Status  {
		DRAFT, ACTIVE,  DELETED
	}
	@Id
	@GeneratedValue
	@Getter @Setter
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	 Y y;

	public Y getY() {
		return y;
	}

	public Subject setY(Y y) {
		this.y = y;
		return this;
	}

	@Override
	public String toString() {
		return "Subject{" +
				 "id=" + id +
				 ", status=" + status +
				 ", name='" + name + '\'' +
				 '}';
	}

	@Getter
	private Status status = Status.DRAFT;

	private LocalDateTime activationTime;

	public void activate() {
		if (status != Status.DRAFT) {
			throw new IllegalStateException();
		}
		status = Status.ACTIVE;
		activationTime = LocalDateTime.now();
	}

	@Getter @Setter
	private String name;
	@Getter @Setter
	private boolean active;
	@Getter @Setter
	@ManyToOne
	@JoinColumn(name = "T_ID")
	private Teacher holderTeacher;
	@Getter @Setter
	@OneToMany(mappedBy="subject")
	private List<TeachingActivity> activities = new ArrayList<>();
	@Getter @Setter
//	@LastModifiedDate // SOLUTION
	private LocalDateTime lastModifiedDate;
	@Getter @Setter
//	@LastModifiedBy // SOLUTION
	private String lastModifiedBy;

	
//	@PrePersist
//	@PreUpdate
//	public void automaticUpdateTrackingColumns() {
//		System.out.println("Before persist/update Subject");
//		lastModifiedDate = LocalDateTime.now();
//		lastModifiedBy = MyUtil.getUserOnCurrentThread();
//	}
	
	
	
	public Subject() {
	}
	
	
	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public Subject(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Teacher getHolderTeacher() {
		return holderTeacher;
	}

	Subject setHolderTeacher(Teacher holder) {
		this.holderTeacher = holder;
		return this;
	}

}
