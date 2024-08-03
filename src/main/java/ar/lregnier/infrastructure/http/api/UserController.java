package ar.lregnier.infrastructure.http.api;

import ar.lregnier.SchedulerConfig;
import ar.lregnier.domain.UserEntity;
import ar.lregnier.domain.UserRepository;
import java.util.List;
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
  public Mono<List<UserEntity>> getUsers() {
    return userRepository.findAll()
        .publishOn(SchedulerConfig.API_SCHEDULER)
        .doOnNext(responseEntity ->
            System.out.println("Processing API Controller code on thread: " + Thread.currentThread().getName())
        );
  }

}
