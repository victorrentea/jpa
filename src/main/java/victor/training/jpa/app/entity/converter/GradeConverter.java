package victor.training.jpa.app.entity.converter;

import victor.training.jpa.app.entity.Teacher;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter // (autoApply = true)
public class GradeConverter implements AttributeConverter<Teacher.Grade, String> {

    @Override
    public String convertToDatabaseColumn(Teacher.Grade attribute) {
        return attribute == null? null:attribute.dbValue;
    }

    @Override
    public Teacher.Grade convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        Teacher.Grade[] allValues = Teacher.Grade.values();
        return Arrays.stream(allValues)
                .filter(g -> g.dbValue.equals(dbData))
                .findFirst()
                .orElseThrow();
    }
    // VARCHAR vs CLOB
}
