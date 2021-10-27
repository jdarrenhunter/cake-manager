package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dto.CakeDto;

import java.util.List;
import java.util.Optional;

public interface CakeService {
    List<CakeDto> getCakes();
    Optional<CakeDto> addCake(CakeDto cake);
}
