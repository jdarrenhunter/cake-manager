package com.waracle.cakemgr.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.waracle.cakemgr.domain.Cake;
import com.waracle.cakemgr.testutil.WireMockInitialiser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static com.waracle.cakemgr.fixtures.CakeFixtures.allCakes;
import static com.waracle.cakemgr.fixtures.CakeFixtures.victoriaSponge;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(properties = "cake-manager.data-url=http://localhost:6666/cakes", webEnvironment = RANDOM_PORT)
@ContextConfiguration(initializers = {WireMockInitialiser.class})
class SimpleCakeServiceIntegrationTest {

    @Autowired
    private SimpleCakeService underTest;

    @Autowired
    private WireMockServer wireMockServer;

    @BeforeEach()
    public void start() {
        wireMockServer.start();
    }

    @AfterEach()
    public void stop() {
        wireMockServer.stop();
    }

    @Test
    public void shouldGetCakes() {
        List<Cake> actual = underTest.getCakes();
        List<Cake> expected = allCakes();
        assertThat(actual).usingFieldByFieldElementComparator().hasSameElementsAs(expected);
    }

    @Test
    @DirtiesContext
    public void shouldAddCake() {
        Cake expected = Cake.builder()
                .title("title")
                .desc("description")
                .image("http://image.jpg")
                .build();
        Optional<Cake> actual = underTest.addCake(expected);
        assertThat(actual).isNotEmpty();
        assertThat(actual.get()).usingRecursiveComparison().isEqualTo(expected);
        List<Cake> cakes = underTest.getCakes();
        assertThat(cakes).usingFieldByFieldElementComparator().contains(expected);
    }

    @Test
    public void shouldNotAddCakeIfAlreadyExists() {
        Cake expected = victoriaSponge();
        Optional<Cake> actual = underTest.addCake(expected);
        assertThat(actual).isEmpty();
        List<Cake> cakes = underTest.getCakes();
        assertThat(cakes.size()).isEqualTo(5);
        assertThat(cakes).usingFieldByFieldElementComparator().contains(expected);
    }

}