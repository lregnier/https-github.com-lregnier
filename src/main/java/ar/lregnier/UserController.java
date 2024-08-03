package ar.lregnier;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

  private final UserRepository userRepository;

  public UserController(final HttpUserRepository apiService) {
    this.userRepository = apiService;
  }

  @GetMapping("/users")
  public Mono<ResponseEntity<String>> getData() {
    return userRepository.findAll()
        .publishOn(SchedulerConfig.API_SCHEDULER)
        .doOnNext(responseEntity ->
            System.out.println("Processing API Controller code run on thread: " + Thread.currentThread().getName())
        );
  }

}
