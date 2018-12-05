package edu.uit.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import edu.uit.dao.YoutubeDataRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = YoutubeDataRepository.class)
public class SpringBootMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(SpringBootMain.class, args);
	}
}
