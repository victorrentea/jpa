package victor.training.jpa.app.entity.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import victor.training.jpa.app.entity.MoreTeacherDetails;
import victor.training.jpa.app.entity.Teacher;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MoreTeacherDetailsConverter implements AttributeConverter<MoreTeacherDetails, String> {

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(MoreTeacherDetails attribute) {
        return new ObjectMapper().writeValueAsString(attribute);
    }

    @SneakyThrows
    @Override
    public MoreTeacherDetails convertToEntityAttribute(String dbData) {
        return new ObjectMapper().readValue(dbData, MoreTeacherDetails.class);
    }
}
