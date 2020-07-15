package com.greenlaw110.di_benchmark.temp;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestBean {

	public TestBean() {
		System.out.println("in TestBean");
	}

	public Fruit fruit;

	public Fruit getFruit() {
		return fruit;
	}

	@Autowired
	@Named("fruitApple")
	public void pp(Fruit fruit) {
		this.fruit = fruit;
	}

}
