package ar.lregnier;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

  private final UserService apiService;

  public UserController(final UserService apiService) {
    this.apiService = apiService;
  }

  @GetMapping("/users")
  public Mono<ResponseEntity<String>> getData() {
    return apiService.findAll()
        .publishOn(SchedulerConfig.API_SCHEDULER)
        .doOnNext(responseEntity ->
            System.out.println("Processing API Controller code on thread: " + Thread.currentThread().getName())
        );
  }

}
