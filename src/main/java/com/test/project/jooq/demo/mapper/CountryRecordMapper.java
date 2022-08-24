package com.test.project.jooq.demo.mapper;

import com.test.project.jooq.demo.dto.Country;
import com.test.project.jooq.demo.repository.impl.CityRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CountryRecordMapper implements RecordMapper<CountriesRecord, Country> {

    private final CityRepository cityRepository;

    @Override
    public Country map(CountriesRecord record) {
        Country country = record.into(Country.class);
        country.setCities(cityRepository.findAll(Cities.CITIES.COUNTRY_ID.eq(country.getId())));
        return country;
    }
}