package ar.lregnier.infrastructure.persistence;

import ar.lregnier.SchedulerConfig;
import ar.lregnier.domain.UserEntity;
import ar.lregnier.domain.UserRepository;
import ar.lregnier.infrastructure.http.client.HttpClient;
import java.util.List;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class HttpUserRepository implements UserRepository {

  private final HttpClient httpClient;

  public HttpUserRepository(final HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  @Override
  public Mono<List<UserEntity>> findAll() {
    return httpClient.getDataFromExternalService()
        .publishOn(SchedulerConfig.SERVICE_SCHEDULER)
        .doOnNext(responseEntity ->
            System.out.println("Processing Service code on thread: " + Thread.currentThread().getName())
        );
  }

}
