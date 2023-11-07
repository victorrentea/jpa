package victor.training.jpa.app.entity.converter;

import victor.training.jpa.app.entity.Teacher;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;

@Converter // (autoApply = true)
public class GradeConverter implements AttributeConverter<Teacher.Grade, String> {

    @Override
    public String convertToDatabaseColumn(Teacher.Grade attribute) {
        return attribute.dbValue;
    }

    @Override
    public Teacher.Grade convertToEntityAttribute(String dbData) {
        return Arrays.stream(Teacher.Grade.values())
                .filter(g -> g.dbValue.equals(dbData))
                .findFirst()
                .orElseThrow();
    }
}
