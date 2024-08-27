package com.study.resttemplatetest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true, value = "pageable")
public class BeerDtoPage extends PageImpl<BeerDto> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BeerDtoPage(@JsonProperty("content") List<BeerDto> content,
                       @JsonProperty("number") int page,
                       @JsonProperty("size") int size,
                       @JsonProperty("totalElements") long total) {
        super(content, PageRequest.of(page, size), total);
    }
}
