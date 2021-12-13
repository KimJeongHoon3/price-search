package com.test.pricesearch.service;

import com.test.pricesearch.config.url.FruitUrlConfig;
import com.test.pricesearch.domain.ItemType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FruitPriceSearchService extends AbstractPriceSearchService{

    public FruitPriceSearchService(WebClient webClient, FruitUrlConfig fruitUrlConfig) {
        super(webClient, fruitUrlConfig);
        itemType=ItemType.FRUIT;
    }

    @Override
    public Mono<AccessToken> getAccessToken() {
        return webClient.get().uri(urlConfig.getTokenPath())
                .retrieve()
                .bodyToMono(AccessToken.class);
    }
}
