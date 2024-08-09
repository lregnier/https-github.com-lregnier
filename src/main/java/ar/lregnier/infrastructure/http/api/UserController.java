package ar.lregnier.infrastructure.http.api;

import ar.lregnier.SchedulerConfig;
import ar.lregnier.domain.UserEntity;
import ar.lregnier.domain.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

  private final UserRepository userRepository;

  public UserController(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/users")
  public Mono<List<UserRepresentation>> getUsers() {
    return userRepository.findAll()
        .publishOn(SchedulerConfig.API_SCHEDULER)
        .doOnNext(responseEntity ->
            System.out.println("Processing API Controller code on thread: " + Thread.currentThread().getName())
        ).map(this::mapToUserRepresentation);
  }

  private List<UserController.UserRepresentation> mapToUserRepresentation(List<UserEntity> userRepresentations) {
    return userRepresentations.stream()
        .map(user -> new UserRepresentation(user.getId(), user.getName(), user.getUsername(), user.getEmail()))
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
