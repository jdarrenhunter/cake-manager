package com.waracle.cakemgr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.client.CakeClient;
import com.waracle.cakemgr.dto.CakeDtoMapper;
import com.waracle.cakemgr.repository.CakeDaoMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class CakeManagerConfig {

    @Bean
    public CakeClient CakeClient(CakeDtoMapper cakeDtoMapper,
                                 @Value("${cake-manager.data-url}") String cakesUrl,
                                 ObjectMapper objectMapper) {
        return new CakeClient(cakeDtoMapper, cakesUrl, objectMapper);
    }

    @Bean
    public WebClient webClient(@Value("${cake-manager.resource-url}") String resourceUrl) {
        return WebClient
                .builder()
                .baseUrl(resourceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public CakeDtoMapper cakeDtoMapper() {
        return new CakeDtoMapper();
    }

    @Bean
    public CakeDaoMapper cakeDaoMapper() {
        return new CakeDaoMapper();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.waracle.cakemgr"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Waracle Cake Manager")
                .description("Management Api for all things cake")
                .contact(new Contact("Captain Cake", "www.waracle.com", "waracle@waracle.com"))
                .version("1.0-SNAPSHOT").build();
    }
}