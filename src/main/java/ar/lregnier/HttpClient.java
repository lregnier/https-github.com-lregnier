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

  public Mono<ResponseEntity<String>> getUsers() {
    return
        buildRequest()
            .subscribeOn(SchedulerConfig.TEST_SCHEDULER)
            .flatMap(this::executeRequest);

  }

  private static Mono<String> buildRequest() {
    return
        Mono.fromCallable(() -> {
          return "Building request";
        }).doOnNext(fakeRequest ->
            System.out.println(
                "Building HTTP Request code run on thread: " + Thread.currentThread().getName())
        );
  }

  private Mono<? extends ResponseEntity<String>> executeRequest(String request) {
    return
        webClient.get()
            .uri("/users")
            .retrieve()
            .toEntity(String.class)
            .doOnNext(responseEntity ->
                System.out.println(
                    "Executing HTTP Request code run on thread: " + Thread.currentThread().getName())
            );
  }


}