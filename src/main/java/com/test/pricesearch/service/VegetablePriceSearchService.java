package com.test.pricesearch.service;

import com.test.pricesearch.config.url.VegetableUrlConfig;
import com.test.pricesearch.domain.ItemType;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class VegetablePriceSearchService extends AbstractPriceSearchService{

    public VegetablePriceSearchService(WebClient webClient, VegetableUrlConfig vegetableUrlConfig) {
        super(webClient, vegetableUrlConfig);
        itemType=ItemType.VEGETABLE;
    }

    @Override
    public Mono<AccessToken> getAccessToken() {
        return webClient.get().uri(urlConfig.getTokenPath())
                .exchangeToMono(clientResponse ->
                        Mono.just(AccessToken.builder()
                                .accessToken(clientResponse.cookies().getFirst(HttpHeaders.AUTHORIZATION).getValue())
                                .build()));
    }
}
