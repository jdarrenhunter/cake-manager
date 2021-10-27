package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dto.CakeDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RestProxyCakeService implements CakeService {

    private final WebClient webClient;

    public RestProxyCakeService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<CakeDto> getCakes() {
        CakeDto[] cakeDtos = webClient.get()
                .retrieve()
                .bodyToMono(CakeDto[].class)
                .block();
        return Arrays.stream(cakeDtos)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CakeDto> addCake(CakeDto cake) {
        CakeDto cakeDto = webClient.post()
                .body(Mono.just(cake), CakeDto.class)
                .retrieve()
                .bodyToMono(CakeDto.class)
                .block();
        return Optional.of(cakeDto);
    }
}