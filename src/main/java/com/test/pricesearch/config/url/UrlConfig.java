package com.test.pricesearch.config.url;

import com.test.pricesearch.util.AesUtil;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
public abstract class UrlConfig {
    private String baseUrl;
    private String itemListPath;
    private String itemPricePath;
    private String tokenPath;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private volatile boolean isDecoded=false;

    public String getBaseUrl() {
        if(!isDecoded){
            try {
                baseUrl= AesUtil.decrypt(baseUrl);
                isDecoded=true;
            } catch (Exception e) {
                throw new RuntimeException("urlDecodeError");
            }
        }
        return baseUrl;
    }
}
