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
import java.time.LocalDateTime;
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
        error.setCreatedAt(LocalDateTime.now());

        ErrorTag child = new ErrorTag("tag");
        error.getTags().add(child);
        repo.save(error); // face persist ca ID= null

        log.debug("Function End");
    }

    public void secondTransaction() {
        ErrorLogDto dto = loadDto(1L);

        // USERUL1
        dto.incaCeva = "date";
        dto.tags.get(0).label = "altTag";
        // aici userul da clik si tasteaza in UI   20min

        altu();

        updateFlow(dto);

    }

    private void altu() {
        // USERUL2
        ErrorLogDto dto = loadDto(1L);
        dto.message = "Mesaj updatat rapid (un user mai tanar)";
        // aici userul da clik si tasteaza in UI in 2min
        updateFlow(dto);
    }

    private ErrorLogDto loadDto(long id) {
        ErrorLog error = anotherClass.openEditDialog(id);
        ErrorLogDto dto = new ErrorLogDto();
        dto.id = error.getId();
        dto.message = error.getMessage();
        dto.incaCeva = error.getIncaCeva();
        dto.lastChange = error.getLastChange();
        dto.tags = error.getTags().stream().map(ErrorTagDto::new).collect(Collectors.toList());
        return dto;
    }

    private void updateFlow(ErrorLogDto dto) {
        //mapez
        ErrorLog input = new ErrorLog(dto.message);
        input.setIncaCeva(dto.incaCeva);
        input.setId(dto.id);
        input.setLastChange(dto.lastChange);
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
    public LocalDateTime lastChange;
}

@Component
@RequiredArgsConstructor
class AnotherClassToGoThroughProxies {
    private final ErrorLogRepo repo;
    private final EntityManager em;
    private final JdbcTemplate jdbc;

    public ErrorLog openEditDialog(long id) {
        return repo.queryCustomizat(id);
    }

    public void update(ErrorLog newError) {
        ErrorLog existing = repo.findById(newError.getId()).get();
        existing.setMessage(newError.getMessage());
        existing.setIncaCeva(newError.getIncaCeva());
        existing.setTags(newError.getTags());
        existing.setLastChange(newError.getLastChange());
        repo.save(existing); // save va face merge pentru ca error are ID setat DEJA
    }
}