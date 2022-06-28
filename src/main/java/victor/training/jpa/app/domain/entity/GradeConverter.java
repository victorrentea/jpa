package victor.training.jpa.app.domain.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
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
