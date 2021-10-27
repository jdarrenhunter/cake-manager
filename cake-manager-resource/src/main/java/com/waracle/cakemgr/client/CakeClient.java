package com.waracle.cakemgr.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.dto.CakeDtoMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CakeClient {

    private final CakeDtoMapper cakeDtoMapper;
    private final String cakesUrl;
    private final ObjectMapper objectMapper;

    public CakeClient(CakeDtoMapper cakeDtoMapper,
                      @Value("${cake-manager.data-url}") String cakesUrl,
                      ObjectMapper objectMapper) {
        this.cakeDtoMapper = cakeDtoMapper;
        this.cakesUrl = cakesUrl;
        this.objectMapper = objectMapper;
    }

    public List<Cake> getCakes() throws JsonProcessingException {
        WebClient webClient = WebClient.create();
        String responseJson = webClient.get()
                .uri(cakesUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        CakeDto[] cakeDtos = objectMapper.readValue(responseJson, CakeDto[].class);
        return Arrays.stream(cakeDtos)
                .map((cakeDto) -> cakeDtoMapper.toCake(cakeDto))
                .collect(Collectors.toList());
    }
}