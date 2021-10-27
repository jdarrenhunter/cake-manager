package com.waracle.cakemgr.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.waracle.cakemgr.testutil.WireMockInitialiser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.waracle.cakemgr.fixtures.CakeFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.skyscreamer.jsonassert.JSONCompareMode.LENIENT;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "cake-manager.data-url=http://localhost:6666/cakes", webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {WireMockInitialiser.class})
class CakeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WireMockServer wireMockServer;

    @BeforeEach()
    public void start() {
        wireMockServer.start();
    }

    @AfterEach()
    public void stop() {
        wireMockServer.stop();
    }

    @Test
    public void get_cakes_shouldReturnAListOfCakes() throws Exception {
        MvcResult result = mockMvc.perform(get("/cakes"))
                .andExpect(status().isOk())
                .andReturn();
        JSONArray actual = new JSONArray(result.getResponse().getContentAsString());
        JSONArray expected = allCakesJson();
        JSONAssert.assertEquals(expected, actual, LENIENT);
    }

    @Test
    @DirtiesContext
    public void post_cakes_shouldAddACake() throws Exception {
        JSONObject expected = cakeJson("title", "desc", "http://image.jpg");

        MvcResult result = mockMvc.perform(
                post("/cakes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expected.toString())
                )
                .andExpect(status().isCreated())
                .andReturn();

        JSONObject actual = new JSONObject(result.getResponse().getContentAsString());
        JSONAssert.assertEquals(expected, actual, LENIENT);
    }


    @Test
    public void post_cakes_shouldReturnConflictWhenAddingDuplicateCake() throws Exception {
        JSONObject cakeJson = bananaCakeJson();

        MvcResult result = mockMvc.perform(
                        post("/cakes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(cakeJson.toString())
                )
                .andExpect(status().is(HttpStatus.CONFLICT.value()))
                .andReturn();

        JSONObject actual = new JSONObject(result.getResponse().getContentAsString());
        String expectedMessage = String.format("%s not added because it already exists", bananaCake().getTitle());
        JSONObject expected = new JSONObject().put("message", expectedMessage);
        JSONAssert.assertEquals(expected, actual, LENIENT);
    }

    @Test
    public void post_cakes_shouldReturnBadRequestWhenAddingInvalidCake() throws Exception {
        JSONObject cakeJson = cakeJson("", "desc", "http://image.jpg");

        MvcResult result = mockMvc.perform(
                        post("/cakes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(cakeJson.toString())
                )
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();

        JSONObject responseBodyJson = new JSONObject(result.getResponse().getContentAsString());
        String actual = responseBodyJson.getString("message");

        assertThat(actual).contains("Input validation error(s)", "Title.Size");
    }
}