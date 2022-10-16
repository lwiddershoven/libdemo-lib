package org.example.libdemo.lib.config;

import org.example.libdemo.lib.service.CatFactService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

// Both @Configuration and @AutoConfiguration work.
// @AutoConfiguration runs after all @Configurations which enables the library to only
// create a bean if no other bean of that type exists. E.g. provide a default instance without having to resort
// to @Primary
@AutoConfiguration
@EnableConfigurationProperties(CatFactServiceConfigProperties.class)
// @Import( other @Configuration or @AutoConfiguration classes )
public class CatFactServiceAutoConfig {

    @Bean
    @ConditionalOnMissingBean(CatFactService.class) // The app should not have defined its own instance
    // @ConditionalOnProperty(value="usecatfactservice", havingValue = "true")
    public CatFactService catFactService(
            CatFactServiceConfigProperties props,
            WebClient.Builder catClientBuilder
    ) {
        var catClient = catClientBuilder
                .baseUrl(props.baseUrl())
                .build();
        return new CatFactService(catClient);
    }
}

