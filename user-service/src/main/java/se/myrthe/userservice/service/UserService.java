package se.myrthe.userservice.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.myrthe.commonmodel.model.User;
import se.myrthe.commonmodel.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  /**
   * TODO: implement validation if username exists etc.
   *
   * @param username the chosen name of the {@link User}
   * @return {@link User}
   */
  public User createUser(@Valid final String username) {
    return repository.save(new User.UserBuilder().setUsername(username).build());
  }
}
