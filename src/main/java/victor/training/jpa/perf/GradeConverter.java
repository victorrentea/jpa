package victor.training.jpa.perf;

import victor.training.jpa.app.entity.Teacher;
import victor.training.jpa.app.entity.Teacher.Grade;
import victor.training.jpa.perf.UberEntity.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter // (autoApply = true)
public class GradeConverter
        implements AttributeConverter<Status, String> {

  @Override
  public String convertToDatabaseColumn(Status attribute) {
    return attribute.getCode();
  }

  @Override
  public Status convertToEntityAttribute(String dbData) {
    return Arrays.stream(Status.values())
            .filter(g -> g.getCode().equals(dbData))
            .findFirst()
            .orElseThrow();
  }
}
