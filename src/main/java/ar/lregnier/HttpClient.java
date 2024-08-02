package ar.lregnier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

  public Mono<ResponseEntity<String>> getDataFromExternalService() {
    return webClient.get()
        .uri("/users")
        .retrieve()
        .toEntity(String.class)
        .doOnNext(responseEntity ->
            System.out.println("Processing HTTP client code on thread: " + Thread.currentThread().getName())
        );
  }
}