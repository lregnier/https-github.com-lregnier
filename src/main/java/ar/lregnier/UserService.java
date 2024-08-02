package ar.lregnier;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

  private final HttpClient httpClient;

  public UserService(final HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public Mono<ResponseEntity<String>> findAll() {
    return httpClient.getDataFromExternalService()
        .publishOn(SchedulerConfig.SERVICE_SCHEDULER)
        .doOnNext(responseEntity ->
            System.out.println("Processing Service code on thread: " + Thread.currentThread().getName())
        );
  }

}
