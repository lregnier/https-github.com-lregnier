package ar.lregnier;

import java.util.function.Function;
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
    return
        prepareRequest()
            .flatMap(executeRequest())
            .publishOn(SchedulerConfig.TEST_SCHEDULER)
            .doOnNext(responseEntity ->
                System.out.println("Changed scheduler now code on thread: " + Thread.currentThread().getName())
            );

  }

  private static Mono<String> prepareRequest() {
    return
        Mono.fromCallable(() -> {
          return "Fake request";
        }).doOnNext(fakeRequest ->
            System.out.println(
                "Preparing HTTP Request build code on thread: " + Thread.currentThread().getName())
        );
  }

  private Function<String, Mono<? extends ResponseEntity<String>>> executeRequest() {
    return request ->
        webClient.get()
            .uri("/users")
            .retrieve()
            .toEntity(String.class)
            .doOnNext(responseEntity ->
                System.out.println("Executing HTTP Request code on thread: " + Thread.currentThread().getName())
            );
  }


}