package victor.training.jpa.app.facade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import victor.training.jpa.app.entity.ErrorLog;
import victor.training.jpa.app.entity.Subject;
import victor.training.jpa.app.repo.SubjectRepo;

@RequiredArgsConstructor
@Slf4j
@Service
public class AnotherService {
	private final SubjectRepo subjectRepo;

	public void checkPermissionsOnSubject(long subjectId) {
		Subject subject = subjectRepo.findOneById(subjectId);
		log.debug("Checking permissions on instance from database: " + System.identityHashCode(subject));
		
	}
}
// use @Transactional for selects only?