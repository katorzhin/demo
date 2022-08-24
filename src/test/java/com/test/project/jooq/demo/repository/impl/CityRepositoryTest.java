package com.test.project.jooq.demo.repository.impl;

import com.test.project.jooq.demo.DemoApplicationTests;
import com.test.project.jooq.demo.dto.City;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;



class CityRepositoryTest extends DemoApplicationTests {

    @Autowired
    private CityRepository repository;

    @Test
    void insert() {
        City city = repository.insert(createCity());
        System.out.println();
    }

    private City createCity() {
        City city = new City();
        city.setName("Washington");
        return city;
    }

    @Test
    void update() {
    }

    @Test
    void find() {
    }

    @Test
    void findAll() {
    }

    @Test
    void delete() {
    }
}