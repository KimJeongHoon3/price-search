package com.test.pricesearch.service;

import com.test.pricesearch.domain.Item;
import com.test.pricesearch.domain.ItemType;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PriceSearchService {
    Mono<AccessToken> getAccessToken();
    Mono<List<String>> getItemNameList();
    Mono<Item> getPrice(String name);
    ItemType getType();
}
