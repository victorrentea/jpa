package victor.training.jpa.perf.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class PostSearchResult {
   long id;
   String title;
   LocalDate publishDate;
}
