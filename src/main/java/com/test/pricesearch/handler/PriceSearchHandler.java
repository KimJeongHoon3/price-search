package com.test.pricesearch.handler;

import com.test.pricesearch.domain.Item;
import com.test.pricesearch.domain.ItemType;
import com.test.pricesearch.exception.PriceSearchError;
import com.test.pricesearch.service.PriceSearchService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PriceSearchHandler {
    private final List<PriceSearchService> priceSearchServices;

    public PriceSearchHandler(List<PriceSearchService> priceSearchServices) {
        this.priceSearchServices = priceSearchServices;
    }

    public Mono<ServerResponse> init(ServerRequest serverRequest) {
        Map<String, List<String>> map = Collections.singletonMap("itemType", Arrays.stream(ItemType.values()).map(ItemType::getTypeName).collect(Collectors.toList()));
        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("index",map);
    }

    public Mono<ServerResponse> findPrice(ServerRequest serverRequest) {
        String type = serverRequest.pathVariable("type");
        String name = serverRequest.pathVariable("name");
        Item item=validateAndMakeItem(type,name);

        return ServerResponse.ok()
                .body(priceSearchServices.stream()
                        .filter(priceSearchService -> priceSearchService.getType()==item.getItemType())
                        .findFirst()
                        .orElseThrow(()->new PriceSearchError(HttpStatus.BAD_REQUEST,"Should choose type"))
                        .getPrice(name)
                        , Item.class);
    }

    private Item validateAndMakeItem(String type, String name) {
        try{
            return Item.builder()
                    .itemType(ItemType.getItemType(type))
                    .name(name)
                    .build();
        }catch(Exception e){
            throw new PriceSearchError(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    public Mono<ServerResponse> findName(ServerRequest serverRequest) {
        String type = serverRequest.pathVariable("type");
        ItemType itemType = ItemType.getItemType(type);
        return ServerResponse.ok()
                .body(priceSearchServices.stream()
                                .filter(priceSearchService -> priceSearchService.getType() == itemType)
                                .findFirst()
                                .orElseThrow(() -> new PriceSearchError(HttpStatus.BAD_REQUEST, "Should choose type"))
                                .getItemNameList()
                        , new ParameterizedTypeReference<List<String>>() {});
    }
}
