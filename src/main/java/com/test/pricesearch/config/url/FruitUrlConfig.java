package com.test.pricesearch.config.url;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("fruit.api")
public class FruitUrlConfig extends UrlConfig{
}
