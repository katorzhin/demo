package com.test.project.jooq.demo.repository.impl;

import com.test.project.jooq.demo.dto.Country;
import com.test.project.jooq.demo.mapper.CountryRecordMapper;
import com.test.project.jooq.demo.mapper.CountryRecordUnmapper;
import com.test.project.jooq.demo.repository.CrudRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CountryRepository implements CrudRepository<Country> {

    private final DSLContext dsl;
    private final CityRepository cityRepository;
    private final CountryRecordMapper countryRecordMapper;
    private final CountryRecordUnmapper countryRecordUnmapper;

    @Override
    public Country insert(Country country) {
        return dsl.insertInto(Countries.COUNTRIES)
                .set(dsl.newRecord(Countries.COUNTRIES, country))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error inserting entity: " + country.getId()))
                .into(Country.class);
    }

    public Country insertWithUnmapper(Country country) {
        return dsl.insertInto(Countries.COUNTRIES)
                .set(countryRecordUnmapper.unmap(country))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error inserting entity: " + country.getId()))
                .into(Country.class);
    }

    public Country insertSettingEachField(Country country) {
        return dsl.insertInto(Countries.COUNTRIES)
                .set(Countries.COUNTRIES.NAME, country.getName())
                .set(Countries.COUNTRIES.POPULATION, country.getPopulation())
                .set(Countries.COUNTRIES.GOVERNMENT_FORM, nameOrNull(country.getGovernmentForm()))
                .returning()
                .fetchOne()
                .into(Country.class);
    }

    public Long insertAndReturnId(Country country) {
        return dsl.insertInto(Countries.COUNTRIES)
                .set(dsl.newRecord(Countries.COUNTRIES, country))
                .returning(Countries.COUNTRIES.ID)
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error inserting entity: " + country.getId()))
                .get(Countries.COUNTRIES.ID);
    }

    @Override
    public Country update(Country country) {
        return dsl.update(Countries.COUNTRIES)
                .set(dsl.newRecord(Countries.COUNTRIES, country))
                .where(Countries.COUNTRIES.ID.eq(country.getId()))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error updating entity: " + country.getId()))
                .into(Country.class);
    }

    public Country updateEachField(Country country) {
        return dsl.update(Countries.COUNTRIES)
                .set(Countries.COUNTRIES.NAME, country.getName())
                .set(Countries.COUNTRIES.POPULATION, country.getPopulation())
                .set(Countries.COUNTRIES.GOVERNMENT_FORM, nameOrNull(country.getGovernmentForm()))
                .where(Countries.COUNTRIES.ID.eq(country.getId()))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException("Error updating entity: " + country.getId()))
                .into(Country.class);
    }

    @Override
    public Country find(Long id) {
        return dsl.selectFrom(Countries.COUNTRIES)
                .where(Countries.COUNTRIES.ID.eq(id))
                .fetchAny()
                .map(r -> {
                    Country country = r.into(Country.class);
                    country.setCities(cityRepository.findAll(Cities.CITIES.COUNTRY_ID.eq(country.getId())));
                    return country;
                });
    }

    public Country findWithCustomMapper(Long id) {
        return dsl.selectFrom(Countries.COUNTRIES)
                .where(Countries.COUNTRIES.ID.eq(id))
                .fetchAny()
                .map(r -> countryRecordMapper.map((CountriesRecord) r));
    }

    @Override
    public List<Country> findAll(Condition condition) {
        return dsl.selectFrom(Countries.COUNTRIES)
                .where(condition)
                .fetch()
                .map(r -> {
                    Country country = r.into(Country.class);
                    country.setCities(cityRepository.findAll(Cities.CITIES.COUNTRY_ID.eq(country.getId())));
                    return country;
                });
    }

    @Override
    public Boolean delete(Long id) {
        return dsl.deleteFrom(Countries.COUNTRIES)
                .where(Countries.COUNTRIES.ID.eq(id))
                .execute() == SUCCESS_CODE;
    }

    public static <T> String nameOrNull(T enumeration) {
        return Objects.isNull(enumeration) ? null : enumeration instanceof Enum ? ((Enum) enumeration).name() : null;
    }
}