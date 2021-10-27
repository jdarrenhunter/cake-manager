package com.waracle.cakemgr.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Builder
@JsonDeserialize(builder = CakeDto.CakeDtoBuilder.class)
public class CakeDto {

    @NotNull(message = "Cake.Title.NotNull")
    @Size(min = 1, max = 100, message = "Cake.Title.Size")
    private String title;

    @NotNull(message = "Cake.Desc.NotNull")
    @Size(min = 1, max = 100, message = "Cake.Desc.Size")
    private String desc;

    @NotNull(message = "Cake.Image.NotNull")
    @Size(min = 10, max = 300, message = "Cake.Image.Size")
    private String image;

    @JsonPOJOBuilder(withPrefix = "")
    public static class CakeDtoBuilder {}
}