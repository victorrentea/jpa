package victor.training.jpa.perf;

import lombok.Value;

// NOT part of Domain, but kept outside
@Value
public class UberSearchResult { // sent as JSON
   Long id;
   String firstName;
   String lastName;
}
