package ar.lregnier.infrastructure.http.client;

import ar.lregnier.domain.UserEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class HttpClient {

  private final WebClient webClient;

  @Autowired
  public HttpClient(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com").build();
  }

  public Mono<List<UserEntity>> getDataFromExternalService() {
    return webClient.get()
        .uri("/users")
        .retrieve()
        .bodyToFlux(UserEntity.class)
        .collectList()
        .doOnNext(responseEntity ->
            System.out.println("Processing HTTP client code on thread: " + Thread.currentThread().getName())
        );
  }

}