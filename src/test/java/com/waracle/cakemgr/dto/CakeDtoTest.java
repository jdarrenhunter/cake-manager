package com.waracle.cakemgr.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.skyscreamer.jsonassert.JSONCompareMode.LENIENT;

class CakeDtoTest {

    public Validator validator;


    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldSerialise() throws JSONException, JsonProcessingException {
        CakeDto cakeDto = CakeDto.builder()
                .title("title")
                .desc("description")
                .image("http://image.jpg")
                .build();

        JSONObject expected = new JSONObject()
                .put("title", "title")
                .put("desc", "description")
                .put("image", "http://image.jpg");

        String actual = new ObjectMapper().writeValueAsString(cakeDto);
        JSONObject actualAsJson = new JSONObject(actual);

        JSONAssert.assertEquals(expected, actualAsJson, LENIENT);
    }

    @Test
    public void validate_shouldValidateSuccessfully() {
        CakeDto underTest = CakeDto.builder()
                .title("title")
                .desc("description")
                .image("http://image.jpg")
                .build();

        Set<ConstraintViolation<CakeDto>> actual = validator.validate(underTest);

        assertThat(actual).isEmpty();
    }

    @Test
    public void validate_shouldFailWhenTitleIsNull() {
        CakeDto underTest = CakeDto.builder()
                .desc("description")
                .image("http://image.jpg")
                .build();

        Set<ConstraintViolation<CakeDto>> actual = validator.validate(underTest);

        assertThat(actual)
                .hasSize(1)
                .extracting(violation -> violation.getMessage())
                .matches(message -> message.contains("Cake.Title.NotNull"));
    }

    @Test
    public void validate_shouldFailWhenTitleIsBlank() {
        CakeDto underTest = CakeDto.builder()
                .title("")
                .desc("description")
                .image("http://image.jpg")
                .build();

        Set<ConstraintViolation<CakeDto>> actual = validator.validate(underTest);

        assertThat(actual)
                .hasSize(1)
                .extracting(violation -> violation.getMessage())
                .matches(message -> message.contains("Cake.Title.Size"));
    }

    @Test
    public void validate_shouldFailWhenTitleIsTooLarge() {
        CakeDto underTest = CakeDto.builder()
                .title(StringUtils.repeat("t", 101))
                .desc("description")
                .image("http://image.jpg")
                .build();

        Set<ConstraintViolation<CakeDto>> actual = validator.validate(underTest);

        assertThat(actual)
                .hasSize(1)
                .extracting(violation -> violation.getMessage())
                .matches(message -> message.contains("Cake.Title.Size"));
    }

    @Test
    public void validate_shouldFailWhenDescriptionIsNull() {
        CakeDto underTest = CakeDto.builder()
                .title("title")
                .image("http://image.jpg")
                .build();

        Set<ConstraintViolation<CakeDto>> actual = validator.validate(underTest);

        assertThat(actual)
                .hasSize(1)
                .extracting(violation -> violation.getMessage())
                .matches(message -> message.contains("Cake.Desc.NotNull"));
    }

    @Test
    public void validate_shouldFailWhenDescriptionIsBlank() {
        CakeDto underTest = CakeDto.builder()
                .title("title")
                .desc("")
                .image("http://image.jpg")
                .build();

        Set<ConstraintViolation<CakeDto>> actual = validator.validate(underTest);

        assertThat(actual)
                .hasSize(1)
                .extracting(violation -> violation.getMessage())
                .matches(message -> message.contains("Cake.Desc.Size"));
    }

    @Test
    public void validate_shouldFailWhenDescriptionIsTooLarge() {
        CakeDto underTest = CakeDto.builder()
                .title("title")
                .desc(StringUtils.repeat("t", 101))
                .image("http://image.jpg")
                .build();

        Set<ConstraintViolation<CakeDto>> actual = validator.validate(underTest);

        assertThat(actual)
                .hasSize(1)
                .extracting(violation -> violation.getMessage())
                .matches(message -> message.contains("Cake.Desc.Size"));
    }

    @Test
    public void validate_shouldFailWhenImageIsNull() {
        CakeDto underTest = CakeDto.builder()
                .title("title")
                .desc("description")
                .build();

        Set<ConstraintViolation<CakeDto>> actual = validator.validate(underTest);

        assertThat(actual)
                .hasSize(1)
                .extracting(violation -> violation.getMessage())
                .matches(message -> message.contains("Cake.Image.NotNull"));
    }

    @Test
    public void validate_shouldFailWhenImageIsBlank() {
        CakeDto underTest = CakeDto.builder()
                .title("title")
                .desc("description")
                .image("")
                .build();

        Set<ConstraintViolation<CakeDto>> actual = validator.validate(underTest);

        assertThat(actual)
                .hasSize(1)
                .extracting(violation -> violation.getMessage())
                .matches(message -> message.contains("Cake.Image.Size"));
    }

    @Test
    public void validate_shouldFailWhenImageIsTooLarge() {
        CakeDto underTest = CakeDto.builder()
                .title("title")
                .desc("description")
                .image(StringUtils.repeat("i", 301))
                .build();

        Set<ConstraintViolation<CakeDto>> actual = validator.validate(underTest);

        assertThat(actual)
                .hasSize(1)
                .extracting(violation -> violation.getMessage())
                .matches(message -> message.contains("Cake.Image.Size"));
    }

}