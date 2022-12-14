package org.example.libdemo.lib.service;

import domain.CatFact;
import org.example.libdemo.lib.api.v1.Breed;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Read ImportantData from ... , or Send ImportantData to.
 */
// @Component
public class CatFactService {
    public final static String DEFAULT_HOST = "https://catfact.ninja";

    public final static String RANDOM_FACT_PATH = "/fact";
    public final static String FACTS_PATH = "/facts";
    public final static String BREEDS_PATH = "/breeds";

    private final WebClient webClient;

    public CatFactService(WebClient webClient) {
        this.webClient = webClient;
    }

    public CatFact getDefaultFact(){
        return webClient.get()
                .uri(RANDOM_FACT_PATH)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, r -> { throw new IllegalStateException("Status " + r.statusCode());} )
                .onStatus(HttpStatus::is4xxClientError, r -> {
                    var exception = switch(r.rawStatusCode()) {
                        case 404 -> new IllegalStateException("404 not found");
                        case 403 -> new IllegalStateException("403 Not allowed for you");
                        default -> new IllegalStateException("Other " + r.statusCode());
                    };
                    throw exception;
                } )
                .bodyToMono(org.example.libdemo.lib.api.v1.CatFact.class)
                .map(api -> new CatFact(api.fact()))
                .block();
    }

    public List<CatFact> getFacts() {
        return List.of();
    }

    public List<Breed> getBreeds() {
        return List.of();
    }

}
