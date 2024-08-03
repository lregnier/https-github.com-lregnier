package ar.lregnier;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface UserRepository {

  Mono<ResponseEntity<String>> findAll();

}
