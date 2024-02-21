package com.ichwan.springmongodb;

import com.ichwan.springmongodb.api.Person;
import com.ichwan.springmongodb.api.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SpringMongodbApplication implements CommandLineRunner {

	@Autowired
	PersonRepository personRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringMongodbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createPerson();
	}

	public void createPerson(){
		personRepository.save(new Person("1","Ichwan","Lampung",20));
		personRepository.save(new Person("2","Abdul","Jakarta",21));
		personRepository.save(new Person("3","Ahmad","Bandung",22));
		personRepository.save(new Person("4","Ujang","Bogor",23));
	}
}