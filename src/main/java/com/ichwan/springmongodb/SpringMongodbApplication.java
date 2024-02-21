package com.ichwan.springmongodb;

import com.ichwan.springmongodb.api.Person;
import com.ichwan.springmongodb.api.PersonRepository;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SpringMongodbApplication implements CommandLineRunner {

	@Autowired
	PersonRepository personRepository;

	MongoOperations operations = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(), "belajar"));

	public static void main(String[] args) {
		SpringApplication.run(SpringMongodbApplication.class, args);
	}

	@Override
	public void run(String... args) {
		//createPerson();
		//getAllPerson();
		//getPersonByName("Ichwan");
		//getPersonByAddress("Jakarta");
		//updateName("Abdullah", "3");
		//insertData();
		//upsert("5",30);
		//updateWithModify();
		updateWithReplace();
	}

	public void createPerson(){
		personRepository.save(new Person("5","Mahmud","Palembang",20));
		personRepository.save(new Person("6","Paul","Medan",21));
		personRepository.save(new Person("7","Pablo","Banjar",22));
		personRepository.save(new Person("8","Aldo","Riau",23));
	}

	public void getAllPerson(){
		personRepository.findAll().forEach(item ->
				System.out.println(getPersonDetails(item))
		);
	}

	public void getPersonByName(String name){
		Person personByName = personRepository.findPersonByName(name);
		System.out.println(getPersonDetails(personByName));
	}

	public void getPersonByAddress(String address){
		Person byAddress = personRepository.findByAddress(address);
		System.out.println(getPersonDetails(byAddress));
	}

	public String getPersonDetails(Person person) {
		return "Person{" +
				"id='" + person.getId() + '\'' +
				", name='" + person.getName() + '\'' +
				", address='" + person.getAddress() + '\'' +
				", age=" + person.getAge() +
				'}';
	}

	public void updateName(String name, String id) {
		operations.updateFirst(Query.query(Criteria.where("_id").is(id)), Update.update("name",name), Person.class);
		Person person = personRepository.findById(id).get();
		System.out.println(getPersonDetails(person));
		//operations.update(Person.class).matching(Criteria.where())
	}

	public void insertData(){
		Person inserted = operations.insert(new Person("9", "Ammar", "Bekasi", 25));
		Query name = Query.query(Criteria.where("name").is(inserted.getName()));
		inserted.setName("Ahsan");
		operations.replace(name, inserted, ReplaceOptions.none());
	}

	public void upsert(String id, int age){
		Query query = Query.query(Criteria.where("_id").is(id));
		Update update = new Update().set("age", age);
		operations.upsert(query, update, Person.class);
	}

	public void updateWithModify(){
		Query query = Query.query(Criteria.where("name").is("Pablo"));
		Update update = new Update().inc("age",1);

		operations.update(Person.class)
				.matching(query)
				.apply(update)
				.findAndModifyValue();
	}

	public void updateWithReplace(){
		operations.update(Person.class)
				.matching(Query.query(Criteria.where("name").is("Ahsan")))
				.replaceWith(new Person("9","Ihsan","Malang",27))
				.withOptions(FindAndReplaceOptions.options().upsert())
				.findAndReplace();
	}
}
