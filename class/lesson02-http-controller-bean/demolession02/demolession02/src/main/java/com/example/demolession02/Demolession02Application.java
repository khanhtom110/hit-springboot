package com.example.demolession02;

import com.example.demolession02.entity.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Demolession02Application {

	public static void main(String[] args) {

		SpringApplication.run(Demolession02Application.class, args);

		InitializeData.init();

	}


}
