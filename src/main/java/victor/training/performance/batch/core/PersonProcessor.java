package victor.training.performance.batch.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import victor.training.performance.batch.core.domain.City;
import victor.training.performance.batch.core.domain.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PersonProcessor implements ItemProcessor<PersonXml, Person> {
    @Autowired
    private CityRepo cityRepo;

    private Map<String, Long> cityNameToId = new HashMap<>();
    @Override
    public Person process(PersonXml xml) {
        Person entity = new Person();
        entity.setName(xml.getName());


        // AICI
        if (cityNameToId.containsKey(xml.getCity())) {
            Long cityId = cityNameToId.get(xml.getCity());
            City cityReference = cityRepo.getOne(cityId);
            entity.setCity(cityReference);
        } else {
            City newCity = cityRepo.save(new City(xml.getCity()));
            cityNameToId.put(xml.getCity(), newCity.getId());
            entity.setCity(newCity);
        }

//        City city = cityRepo.findByName(xml.getCity())
//            .orElseGet(() -> cityRepo.save(new City(xml.getCity())));

//        entity.setCity(city);
        return entity;
    }

}
