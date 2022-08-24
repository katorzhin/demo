package com.test.project.jooq.demo.dto;

import com.test.project.jooq.demo.dto.type.GovernmentForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    private Long id;
    private String name;
    private GovernmentForm governmentForm;
    private Integer population;

    private List<City> cities;
}
