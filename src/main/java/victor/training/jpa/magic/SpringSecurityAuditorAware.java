//package victor.training.jpa.magic;
//
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Component
//public class SpringSecurityAuditorAware implements AuditorAware<String> {
//
//   @Override
//   public Optional<String> getCurrentAuditor() {
//      return Optional.ofNullable(SecurityContextHolder.getContext())
//          .map(SecurityContext::getAuthentication)
//          .filter(Authentication::isAuthenticated)
//          .map(Authentication::getName);
//   }
//}