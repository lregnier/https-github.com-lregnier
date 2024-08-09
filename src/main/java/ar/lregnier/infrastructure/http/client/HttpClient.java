package ar.lregnier.infrastructure.http.client;

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
    this.webClient = webClientBuilder.build();
  }

  public <T> Mono<List<T>> getAll(String url, Class<T> clazz) {
    return webClient.get()
        .uri(url)
        .retrieve()
        .bodyToFlux(clazz)
        .collectList()
        .doOnNext(responseEntity ->
            System.out.println("Processing HTTP client code on thread: " + Thread.currentThread().getName())
        );
  }

}