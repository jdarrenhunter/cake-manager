package com.waracle.cakemgr.dto;

import com.waracle.cakemgr.domain.Cake;

public class CakeDtoMapper {

    public CakeDto fromCake(Cake cake) {
        return CakeDto.builder()
                .title(cake.getTitle())
                .desc(cake.getDesc())
                .image(cake.getImage()).build();
    }

    public Cake toCake(CakeDto cakeDto) {
        return Cake.builder()
                .title(cakeDto.getTitle())
                .desc(cakeDto.getDesc())
                .image(cakeDto.getImage()).build();
    }
}
