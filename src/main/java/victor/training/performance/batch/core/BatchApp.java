package victor.training.performance.batch.core;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import victor.training.performance.batch.core.domain.Person;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;


@Slf4j
@SpringBootApplication
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchApp {
   private final JobBuilderFactory jobBuilder;
   private final StepBuilderFactory stepBuilder;

   public static void main(String[] args) throws IOException {
      XmlFileGenerator.generateFile(10_000);
      int dt = measureCall(() -> SpringApplication.run(BatchApp.class, args).close());
      System.out.println("Batch took " + dt + " ms");
   }
   public static int measureCall(Runnable r) {
      long t0 = System.currentTimeMillis();
      r.run();
      long t1 = System.currentTimeMillis();
      return (int) (t1-t0);
   }

   public Step basicChunkStep() {
      return stepBuilder.get("basicChunkStep")
          // TODO optimize: tune chunk size
          .<PersonXml, Person>chunk(5)
          .reader(xmlReader())
          // TODO optimize: reduce READS
          .processor(personProcessor())
          // TODO optimize: tune ID generation
          // TODO optimize: enable JDBC batch mode
          .writer(jpaWriter(null))
          .listener(new MyChunkListener())
          .listener(stepListener())
          .build();
         // TODO optimize: run insert in multithread. > SynchronizedItemStreamReader
         // TODO templetize input filename
         // [bonus] TODO implement progress tracking%
   }

   @Bean
   @StepScope
   public MyStepExecutionListener stepListener() {
      return new MyStepExecutionListener();
   }


   @Bean
   public PersonProcessor personProcessor() {
      return new PersonProcessor();
   }

   @Bean
   public JpaItemWriter<Person> jpaWriter(EntityManagerFactory emf) {
      JpaItemWriter<Person> writer = new JpaItemWriter<>();
      writer.setEntityManagerFactory(emf);
      return writer;
   }

   @SneakyThrows
   private ItemReader<PersonXml> xmlReader() {
      StaxEventItemReader<PersonXml> reader = new StaxEventItemReader<>();
      FileSystemResource inputFile = new FileSystemResource("data.xml");
      reader.setResource(inputFile);
      reader.setStrict(true);
      reader.setFragmentRootElementName("person");
      Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
      unmarshaller.setClassesToBeBound(PersonXml.class);
      reader.setUnmarshaller(unmarshaller);
      return reader;
   }

   @Bean
   public Job basicJob() {
      return jobBuilder.get("basicJob")
          .incrementer(new RunIdIncrementer())
          .start(basicChunkStep())
          .listener(new MyJobListener())
          .build();

   }
}

