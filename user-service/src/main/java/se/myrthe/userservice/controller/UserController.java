package se.myrthe.userservice.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.myrthe.commonmodel.model.User;
import se.myrthe.userservice.service.UserService;

@RestController
@RequestMapping(value = "/api/v1", consumes = MediaType.APPLICATION_JSON_VALUE, headers = {
    "Content-Type=application/json", "Accept=application/json"})
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  @Autowired
  private UserService service;

  @PostMapping("/user")
  public User createUser(@Valid @RequestBody final String username) {
    logger.info("Creating user with username {}", username);
    final User createdUser = service.createUser(username);
    logger.info("Succesfully created a new user with id {}", createdUser.getId());
    return createdUser;
  }
}
