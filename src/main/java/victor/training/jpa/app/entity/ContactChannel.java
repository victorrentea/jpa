package victor.training.jpa.app.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
public class ContactChannel {
	@Id
	private Long id;

	public enum Type {
		PERSONAL_PHONE,
		WORK_PHONE,
		PERSONAL_EMAIL,
		WORK_EMAIL,
		TWITTER,
		FACEBOOK,
		LINKED_IN
	}

	@Enumerated(EnumType.STRING)
	private Type type;
	private String value;

	private ContactChannel() {
	}

	public ContactChannel(Type type, String value) {
		this.type = type;
		this.value = value;
	}

}
