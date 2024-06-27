package com.example.DOAN1;

import com.example.DOAN1.Entities.Faculty;
import com.example.DOAN1.Repositories.FacultyRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class Doan1Application {



	public static void main(String[] args) {
		SpringApplication.run(Doan1Application.class, args);


	}

}
