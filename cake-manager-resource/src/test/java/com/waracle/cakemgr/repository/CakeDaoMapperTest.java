package com.waracle.cakemgr.repository;

import com.waracle.cakemgr.domain.Cake;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CakeDaoMapperTest {

    private CakeDaoMapper underTest = new CakeDaoMapper();

    @Test
    void shouldMapfromCake() {
        Cake cake = Cake.builder()
                .title("title")
                .desc("description")
                .image("http://image.jpg")
                .build();

        CakeDao actual = underTest.fromCake(cake);

        CakeDao expected = new CakeDao();
                expected.setTitle("title");
                expected.setDescription("description");
                expected.setImage("http://image.jpg");

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void shouldMapToCake() {
        CakeDao cakeDao = new CakeDao();
        cakeDao.setTitle("title");
        cakeDao.setDescription("description");
        cakeDao.setImage("http://image.jpg");

        Cake actual = underTest.toCake(cakeDao);

        Cake expected = Cake.builder()
                .title("title")
                .desc("description")
                .image("http://image.jpg")
                .build();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}