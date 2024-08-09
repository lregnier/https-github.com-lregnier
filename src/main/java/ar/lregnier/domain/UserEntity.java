package ar.lregnier.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserEntity {

  private final String id;
  private final String name;
  private final String username;
  private final String email;
}
