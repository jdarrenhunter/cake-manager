package com.waracle.cakemgr.dto;

import com.waracle.cakemgr.domain.Cake;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CakeDtoMapperTest {

    private CakeDtoMapper underTest = new CakeDtoMapper();

    @Test
    void shouldMapfromCake() {
        Cake cake = Cake.builder()
                .title("title")
                .desc("description")
                .image("http://image.jpg")
                .build();

        CakeDto actual = underTest.fromCake(cake);

        CakeDto expected = CakeDto.builder()
                .title("title")
                .desc("description")
                .image("http://image.jpg")
                .build();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldMapToCake() {
        CakeDto cakeDto = CakeDto.builder()
                .title("title")
                .desc("description")
                .image("http://image.jpg")
                .build();

        Cake actual = underTest.toCake(cakeDto);

        Cake expected = Cake.builder()
                .title("title")
                .desc("description")
                .image("http://image.jpg")
                .build();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}