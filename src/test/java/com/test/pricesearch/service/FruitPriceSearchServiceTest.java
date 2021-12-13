package com.test.pricesearch.service;

import com.test.pricesearch.config.url.FruitUrlConfig;
import com.test.pricesearch.domain.Item;
import com.test.pricesearch.domain.ItemType;
import com.test.pricesearch.exception.PriceSearchError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class FruitPriceSearchServiceTest {
    FruitPriceSearchService fruitPriceSearchService;

    @BeforeEach
    void init(){
        WebClient webClient= WebClient.builder().build();
        FruitUrlConfig fruitUrlConfig=new FruitUrlConfig();
        fruitUrlConfig.setBaseUrl("WgH09ZKHtL1bsnlv7HGoMz+Iba5xVoPI/IM8ff4gmxU=");
        fruitUrlConfig.setItemListPath("/product");
        fruitUrlConfig.setItemPricePath("/product?name={name}");
        fruitUrlConfig.setTokenPath("/token");

        fruitPriceSearchService=new FruitPriceSearchService(webClient,fruitUrlConfig);
    }

    @Test
    void 과일가게_accessToken_발급() {
        Mono<AccessToken> accessToken=fruitPriceSearchService.getAccessToken();

        StepVerifier.create(accessToken)
                .assertNext(token -> assertNotNull(token.getAccessToken()))
                .verifyComplete();
    }

    @Test
    void 과일가게_목록조회(){
       fruitPriceSearchService.init();
       Mono<List<String>> itemNameList = fruitPriceSearchService.getItemNameList();
       StepVerifier.create(itemNameList)
                .assertNext(list -> assertThat(list).contains("배", "토마토", "사과", "바나나") )
                .verifyComplete();
    }

    @Test
    void 과일가게_가격조회_성공(){
        fruitPriceSearchService.init();
        Mono<Item> item = fruitPriceSearchService.getPrice("배");
        StepVerifier.create(item)
                .assertNext(i -> assertEquals(i,Item.builder().price(3000).name("배").itemType(ItemType.FRUIT).build()))
                .verifyComplete();
    }

    @Test
    void 과일가게_가격조회_실패(){
        fruitPriceSearchService.init();
        Mono<Item> item=fruitPriceSearchService.getPrice("없음");

        StepVerifier.create(item)
                .expectError(PriceSearchError.class)
                .verify();
    }
}