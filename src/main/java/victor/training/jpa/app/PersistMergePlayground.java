package victor.training.jpa.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import victor.training.jpa.app.domain.entity.ErrorLog;
import victor.training.jpa.app.domain.entity.ErrorTag;
import victor.training.jpa.app.repo.ErrorLogRepo;
import victor.training.jpa.app.repo.TeacherRepo;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersistMergePlayground {
    private final EntityManager em;
    private final JdbcTemplate jdbc;
    private final TeacherRepo teacherRepo;
    private final ErrorLogRepo repo;
    private final AnotherClassToGoThroughProxies anotherClass;


    @Transactional
    public void firstTransaction() {
        log.debug("Function Begin");
        ErrorLog error = new ErrorLog("mesaj");

        ErrorTag child = new ErrorTag("tag");
        error.getTags().add(child);
        repo.save(error); // face persist ca ID= null

        log.debug("Function End");
    }

    public void secondTransaction() {
        ErrorLog error = anotherClass.openEditDialog(1L);
        ErrorLogDto dto = new ErrorLogDto();
        dto.id = error.getId();
        dto.message = error.getMessage();
        dto.incaCeva = error.getIncaCeva();
        dto.tags = error.getTags().stream().map(ErrorTagDto::new).collect(Collectors.toList());

        // in FE
        dto.message = "Alt mesaj";
        dto.tags.get(0).label = "altTag";

        //mapez
        ErrorLog input = new ErrorLog();
        input.setMessage(dto.message);
        input.setIncaCeva(dto.incaCeva);
        input.setId(dto.id);
        input.setTags(dto.tags.stream().map(ErrorTagDto::toEntity).collect(Collectors.toSet()));

        anotherClass.update(input);
    }
}
class ErrorTagDto {
    public Long id;
    public String label;
    public ErrorTagDto(ErrorTag entity) {
        id = entity.getId();
        label = entity.getLabel();
    }

    public ErrorTag toEntity() {
        ErrorTag tag = new ErrorTag();
        tag.setId(id);
        tag.setLabel(label);
        return tag;
    }
}
class ErrorLogDto {
    public String message;
    public List<ErrorTagDto> tags;
    public String incaCeva;
    public Long id;
}

@Component
@RequiredArgsConstructor
class AnotherClassToGoThroughProxies {
    private final ErrorLogRepo repo;
    private final EntityManager em;

    public ErrorLog openEditDialog(long id) {
        return repo.queryCustomizat(id);
    }

    public void update(ErrorLog error) {
//        em.merge(error);
        repo.save(error); // save va face merge pentru ca error are ID setat DEJA
    }
}