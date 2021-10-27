package com.waracle.cakemgr.service;

import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.dto.CakeDto;
import com.waracle.cakemgr.dto.CakeDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RestProxyCakeService implements CakeService {

    private final CakeDtoMapper cakeDtoMapper;

    private final WebClient webClient;

    public RestProxyCakeService(CakeDtoMapper cakeDtoMapper, WebClient webClient) {
        this.cakeDtoMapper = cakeDtoMapper;
        this.webClient = webClient;
    }

    @Override
    public List<Cake> getCakes() {
        CakeDto[] cakeDtos = webClient.get()
                .retrieve()
                .bodyToMono(CakeDto[].class)
                .block();
        return Arrays.stream(cakeDtos)
                .map((cakeDto) -> cakeDtoMapper.toCake(cakeDto))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Cake> addCake(Cake cake) {
        CakeDto cakeDto = webClient.post()
                .body(Mono.just(cakeDtoMapper.fromCake(cake)), CakeDto.class)
                .retrieve()
                .bodyToMono(CakeDto.class)
                .block();
        return Optional.of(cakeDtoMapper.toCake(cakeDto));
    }
}