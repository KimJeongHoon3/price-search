package com.test.pricesearch.handler;

import com.test.pricesearch.domain.Item;
import com.test.pricesearch.domain.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class PriceSearchHandlerTest {
    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        webTestClient = webTestClient
                .mutate()
                .responseTimeout(Duration.ofHours(1))
                .build();
    }

    @Test
    void 타입_가져오기(){
        webTestClient.get().uri("/")
                .exchange()
                .expectHeader()
                .contentType(MediaType.TEXT_HTML)
                .expectStatus().is2xxSuccessful()
                .expectBody(String.class)
                .value(str -> assertThat(str).contains("과일","채소"));
    }

    @Test
    void 과일_이름_가져오기(){
        webTestClient.get().uri("/market/과일")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<List<String>>() {})
                .value(list -> assertThat(list).contains("배", "토마토", "사과", "바나나"));
    }

    @Test
    void 채소_이름_가져오기(){
        webTestClient.get().uri("/market/채소")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(new ParameterizedTypeReference<List<String>>() {})
                .value(list -> assertThat(list).contains("치커리", "토마토", "깻잎", "상추"));
    }

    @Test
    void 가격_조회_정상(){
        webTestClient.get().uri("/market/과일/배")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Item.class)
                .isEqualTo(Item.builder().name("배").itemType(ItemType.FRUIT).price(3000).build())
                .consumeWith(System.out::println);
    }

    @Test
    void 가격_조회_에러_해당type없음(){
        webTestClient.get().uri("/market/과/배")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    void 가격_조회_에러_해당name없음(){
        webTestClient.get().uri("/market/과일/없음")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .consumeWith(System.out::println);
    }

}