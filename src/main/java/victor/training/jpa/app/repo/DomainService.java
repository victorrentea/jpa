package victor.training.jpa.app.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static victor.training.jpa.app.repo.TeacherSpecifications.hasNameLike;

@Service
@RequiredArgsConstructor
public class DomainService {
   private final TeacherRepo repo;

   public void method() {
      repo.findAll(hasNameLike("teachername"));
   }
}
