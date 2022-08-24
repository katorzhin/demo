package com.test.project.jooq.demo.mapper;

import com.test.project.jooq.demo.dto.Country;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.exception.MappingException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CountryRecordUnmapper implements RecordUnmapper<Country, CountriesRecord> {

    private final DSLContext dsl;

    @Override
    public CountriesRecord unmap(Country country) throws MappingException {
        CountriesRecord record = dsl.newRecord(Countries.COUNTRIES, country);
        record.setPopulation(-1);
        return record;
    }
}