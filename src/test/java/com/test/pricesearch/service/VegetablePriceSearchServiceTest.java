package com.test.pricesearch.service;

import com.test.pricesearch.config.url.FruitUrlConfig;
import com.test.pricesearch.config.url.VegetableUrlConfig;
import com.test.pricesearch.domain.Item;
import com.test.pricesearch.domain.ItemType;
import com.test.pricesearch.exception.PriceSearchError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class VegetablePriceSearchServiceTest {

    VegetablePriceSearchService vegetablePriceSearchService;

    @BeforeEach
    void init(){
        WebClient webClient= WebClient.builder().build();
        VegetableUrlConfig vegetableUrlConfig=new VegetableUrlConfig();
        vegetableUrlConfig.setBaseUrl("Wg1W7UW1VPKy2qmchUr2kgFbS7gzlSVKZxre47WwoknunQeHEJUe6VwY9chgSnHo");
        vegetableUrlConfig.setItemListPath("/item");
        vegetableUrlConfig.setItemPricePath("/item?name={name}");
        vegetableUrlConfig.setTokenPath("/token");

        vegetablePriceSearchService=new VegetablePriceSearchService(webClient,vegetableUrlConfig);
    }

    @Test
    void 채소가게_accessToken_발급() {
        Mono<AccessToken> accessToken= vegetablePriceSearchService.getAccessToken();
//        accessToken.subscribe(token -> System.out.println(token.getAccessToken()));

        StepVerifier.create(accessToken)
                .assertNext(token -> assertNotNull(token))
                .verifyComplete();
    }

    @Test
    void 채소가게_목록조회(){
        vegetablePriceSearchService.init();
        Mono<List<String>> items=vegetablePriceSearchService.getItemNameList();
        StepVerifier.create(items)
                .assertNext(list -> assertThat(list).contains("치커리", "토마토", "깻잎", "상추") )
                .verifyComplete();
    }

    @Test
    void 채소가게_가격조회_성공(){
        vegetablePriceSearchService.init();
        Mono<Item> item=vegetablePriceSearchService.getPrice("깻잎");

        StepVerifier.create(item)
                .assertNext(i -> assertEquals(i,Item.builder().price(1500).name("깻잎").itemType(ItemType.VEGETABLE).build()))
                .verifyComplete();
    }

    @Test
    void 채소가게_가격조회_실패(){
        vegetablePriceSearchService.init();
        Mono<Item> item=vegetablePriceSearchService.getPrice("없음");

        StepVerifier.create(item)
                .expectError(PriceSearchError.class)
                .verify();
    }
}