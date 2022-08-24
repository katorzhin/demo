package com.test.project.jooq.demo.repository.impl;

import com.test.project.jooq.demo.dto.City;
import com.test.project.jooq.demo.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CityRepository implements CrudRepository<City> {

    private final DSLContext dsl;

    @Override
    public City insert(City city) {
        return dsl.insertInto(Cities.CITIES)
                .set(dsl.newRecord(Cities.CITIES, city))
                .returning()
                .fetchOne()
                .into(City.class);
    }

    @Override
    public City update(City city) {
        return dsl.update(Cities.CITIES)
                .set(dsl.newRecord(Cities.CITIES, city))
                .where(Cities.CITIES.ID.eq(city.getId()))
                .returning()
                .fetchOne()
                .into(City.class);
    }

    @Override
    public City find(Long id) {
        return dsl.selectFrom(Cities.CITIES)
                .where(Cities.CITIES.ID.eq(id))
                .fetchAny()
                .into(City.class);
    }

    @Override
    public List<City> findAll(Condition condition) {
        return dsl.selectFrom(Cities.CITIES)
                .where(condition)
                .fetch()
                .into(City.class);
    }

    @Override
    public Boolean delete(Long id) {
        return dsl.deleteFrom(Cities.CITIES)
                .where(Cities.CITIES.ID.eq(id))
                .execute() == SUCCESS_CODE;
    }
}