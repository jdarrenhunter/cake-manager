package com.waracle.cakemgr.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.skyscreamer.jsonassert.JSONCompareMode.LENIENT;

class ErrorDtoTest {

    @Test
    public void shouldSerialise() throws JSONException, JsonProcessingException {
        ErrorDto errorDto = ErrorDto.builder()
                .message("message")
                .build();

        JSONObject expected = new JSONObject()
                .put("message", "message");

        String actual = new ObjectMapper().writeValueAsString(errorDto);
        JSONObject actualAsJson = new JSONObject(actual);

        JSONAssert.assertEquals(expected, actualAsJson, LENIENT);
    }

}