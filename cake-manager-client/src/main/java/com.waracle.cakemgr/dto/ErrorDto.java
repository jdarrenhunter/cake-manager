package com.waracle.cakemgr.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@Builder
@JsonDeserialize(builder = ErrorDto.ErrorDtoBuilder.class)
public class ErrorDto {

    private String message;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ErrorDtoBuilder {}
}
