package ar.lregnier;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class HttpUserRepository implements UserRepository {

  private final HttpClient httpClient;

  public HttpUserRepository(final HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public Mono<ResponseEntity<String>> findAll() {
    return httpClient.getUsers()
        .publishOn(SchedulerConfig.SERVICE_SCHEDULER)
        .doOnNext(responseEntity ->
            System.out.println("Processing Service code run on thread: " + Thread.currentThread().getName())
        );
  }

}
