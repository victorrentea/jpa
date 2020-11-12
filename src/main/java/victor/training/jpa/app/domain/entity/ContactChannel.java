package victor.training.jpa.app.domain.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Embeddable
public class ContactChannel {

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
//	@Column(nullable = false)
	private Type type;
	
	private String value;
	
	protected ContactChannel() { // for the eyes of Hibernate
	}

	public ContactChannel(Type type, String value) {
		this.type = requireNonNull(type);
		this.value = requireNonNull(value);
	}

	public Type getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ContactChannel that = (ContactChannel) o;
		return type == that.type &&
				 Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, value);
	}
}
