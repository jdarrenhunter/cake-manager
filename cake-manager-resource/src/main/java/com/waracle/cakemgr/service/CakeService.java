package com.waracle.cakemgr.service;

import com.waracle.cakemgr.domain.Cake;

import java.util.List;
import java.util.Optional;

public interface CakeService {
    List<Cake> getCakes();
    Optional<Cake> addCake(Cake cake);
}
