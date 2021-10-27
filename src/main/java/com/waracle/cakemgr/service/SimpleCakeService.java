package com.waracle.cakemgr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.waracle.cakemgr.client.CakeClient;
import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.repository.CakeDao;
import com.waracle.cakemgr.repository.CakeDaoMapper;
import com.waracle.cakemgr.repository.CakeRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Service
public class SimpleCakeService implements CakeService {

    private final CakeRepository cakeRepository;

    private final CakeDaoMapper cakeDaoMapper;

    private final CakeClient cakeClient;

    public SimpleCakeService(CakeRepository cakeRepository, CakeDaoMapper cakeDaoMapper, CakeClient cakeClient) {
        this.cakeRepository = cakeRepository;
        this.cakeDaoMapper = cakeDaoMapper;
        this.cakeClient = cakeClient;
    }

    @Override
    public List<Cake> getCakes() {
        return cakeRepository.findAll().stream()
                .map(cakeDao -> cakeDaoMapper.toCake(cakeDao))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Cake> addCake(Cake cake) {
        if (cake != null && !cakeRepository.cakeExists(cake.getTitle())) {
            CakeDao cakeDao = cakeRepository.saveAndFlush(cakeDaoMapper.fromCake(cake));
            return Optional.of(cakeDaoMapper.toCake(cakeDao));
        } else {
            return Optional.empty();
        }
    }

    @PostConstruct
    public void loadCakes() throws JsonProcessingException {
        List<Cake> cakes = cakeClient.getCakes();
        cakes.stream().forEach(this::addCake);
    }

}