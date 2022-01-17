package victor.training.jpa.perf.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import victor.training.jpa.perf.entity.PostSearchView;

import java.util.List;

public interface PostSearchViewRepo extends JpaRepository<PostSearchView, Long> {
   @Query("SELECT pv " +
          " FROM PostSearchView pv JOIN Post p ON pv.id = p.id " +
          " WHERE p.postType = 'PHILOSOPHY' ")
      // you can go back to your Entity to select via your entity model
   List<PostSearchView> queryViaRootEntityModel();
}
