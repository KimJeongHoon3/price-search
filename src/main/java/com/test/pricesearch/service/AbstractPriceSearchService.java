package com.test.pricesearch.service;

import com.test.pricesearch.config.url.UrlConfig;
import com.test.pricesearch.domain.Item;
import com.test.pricesearch.domain.ItemType;
import com.test.pricesearch.exception.PriceSearchError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
public abstract class AbstractPriceSearchService implements PriceSearchService{
    protected WebClient webClient;
    protected String accessToken;
    protected ItemType itemType;
    protected UrlConfig urlConfig;

    public AbstractPriceSearchService(WebClient webClient, UrlConfig urlConfig) {
        this.webClient = webClient
                .mutate()
                .baseUrl(urlConfig.getBaseUrl())
                .build();
        this.urlConfig=urlConfig;
    }

    @PostConstruct
    public void init(){
        try{
            accessToken = getAccessToken().block().getAccessToken();
        }catch(Exception e){
            log.error(itemType.getTypeName() + "API access token error",e);
            System.exit(1);
        }

    }

    @Override
    public ItemType getType() {
        return itemType;
    }

    @Override
    public Mono<List<String>> getItemNameList() {
        return webClient.get().uri(urlConfig.getItemListPath())
                .header(HttpHeaders.AUTHORIZATION,accessToken)
                .retrieve()
                .onStatus(HttpStatus::isError,this::handleError)
                .bodyToMono(new ParameterizedTypeReference<List<String>>(){});
    }

    @Override
    public Mono<Item> getPrice(String name) {
        return webClient.get().uri(urlConfig.getItemPricePath(),name)
                .header(HttpHeaders.AUTHORIZATION,accessToken)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,this::handleClientError)
                .onStatus(HttpStatus::isError,this::handleError)
                .bodyToMono(Item.class)
                .map(item -> {
                    item.setItemType(itemType);
                    return item;
                });
    }

    private Mono<? extends Throwable> handleError(ClientResponse clientResponse) {
        return Mono.error(new PriceSearchError(HttpStatus.INTERNAL_SERVER_ERROR,"server error"));
    }

    private Mono<? extends Throwable> handleClientError(ClientResponse clientResponse) {
        return clientResponse.statusCode() == HttpStatus.NOT_FOUND
                ? Mono.error(new PriceSearchError(HttpStatus.NOT_FOUND,"Cannot find name")) : Mono.error(new PriceSearchError(HttpStatus.INTERNAL_SERVER_ERROR,"server error")) ;
    }
}
