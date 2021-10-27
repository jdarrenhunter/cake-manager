package com.waracle.cakemgr.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@Builder
@JsonDeserialize(builder = CakeDto.CakeDtoBuilder.class)
public class CakeDto {

    private String title;
    private String desc;
    private String image;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CakeDtoBuilder {}
}