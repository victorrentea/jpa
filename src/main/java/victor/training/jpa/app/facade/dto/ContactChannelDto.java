package victor.training.jpa.app.facade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import victor.training.jpa.app.entity.ContactChannel;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactChannelDto {
	private ContactChannel.Type type;
	private String value;
	public ContactChannelDto(ContactChannel contactChannel) {
		type = contactChannel.getType();
		value = contactChannel.getValue();
	}

}
