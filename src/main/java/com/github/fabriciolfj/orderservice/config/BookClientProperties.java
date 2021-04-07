package com.github.fabriciolfj.orderservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import java.net.URI;

@Data
@ConfigurationProperties(prefix = "polar")
public class BookClientProperties {

    @NotNull
    private URI catalogServiceUrl;
}
