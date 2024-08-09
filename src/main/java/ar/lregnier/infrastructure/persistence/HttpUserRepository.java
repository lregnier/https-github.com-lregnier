package ar.lregnier.infrastructure.persistence;

import ar.lregnier.SchedulerConfig;
import ar.lregnier.domain.UserEntity;
import ar.lregnier.domain.UserRepository;
import ar.lregnier.infrastructure.http.client.HttpClient;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class HttpUserRepository implements UserRepository {

  private static final String GET_ALL_URL = "https://jsonplaceholder.typicode.com/users";

  private final HttpClient httpClient;

  public HttpUserRepository(final HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  @Override
  public Mono<List<UserEntity>> findAll() {
    return httpClient.getAll(GET_ALL_URL, UserRepresentation.class)
        .publishOn(SchedulerConfig.PERSISTENCE_SCHEDULER)
        .doOnNext(responseEntity ->
            System.out.println("Processing Repository code on thread: " + Thread.currentThread().getName())
        ).map(this::mapToUserEntities);
  }

  private List<UserEntity> mapToUserEntities(List<UserRepresentation> userRepresentations) {
    return userRepresentations.stream()
        .map(user -> new UserEntity(user.getId(), user.getName(), user.getUsername(), user.getEmail()))
        .collect(Collectors.toList());
  }

  @Getter
  @RequiredArgsConstructor
  public static class UserRepresentation {

    private final String id;
    private final String name;
    private final String username;
    private final String email;
  }

}
