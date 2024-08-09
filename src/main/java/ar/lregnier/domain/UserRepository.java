package ar.lregnier.domain;

import java.util.List;
import reactor.core.publisher.Mono;

public interface UserRepository {

  Mono<List<UserEntity>> findAll();

}
