package victor.training.jpa.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.app.ActivitySearchCriteria;
import victor.training.jpa.app.domain.entity.TeachingActivity;

import java.util.List;

public interface TeachingActivityRepo
        extends JpaRepository<TeachingActivity, Long>,
    TeachingActivityRepoCustom{
}
