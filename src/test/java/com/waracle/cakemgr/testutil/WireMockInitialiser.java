package com.waracle.cakemgr.testutil;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class WireMockInitialiser implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final int WIREMOCK_PORT = 6666;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().port(WIREMOCK_PORT));

        MappingBuilder cakesMapping = get(urlEqualTo("/cakes")).willReturn(
                aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withStatus(200)
                        .withBody(ResourceUtils.readResourceFileToString("src/test/resources/payloads/cakes-response.json"))
        );
        wireMockServer.stubFor(cakesMapping);

        wireMockServer.start();

        applicationContext.addApplicationListener(applicationEvent -> {
            if(applicationEvent instanceof ContextClosedEvent) {
                wireMockServer.stop();
            }
        });

        applicationContext.getBeanFactory().registerSingleton("wireMockServer", wireMockServer);

        TestPropertyValues
                .of(Map.of("todo_base_url", wireMockServer.baseUrl()))
                .applyTo(applicationContext);
    }
}
