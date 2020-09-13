package org.dfm.piggyurl.rest;

import org.dfm.piggyurl.domain.model.Group;
import org.dfm.piggyurl.domain.model.User;
import org.dfm.piggyurl.domain.port.RequestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/piggyurls")
public class UserResource {

  @Autowired
  @Qualifier("requestUser")
  private RequestUser requestUser;

  @GetMapping("/user/logins/{userName}")
  public ResponseEntity<User> getUserLoginDetail(@PathVariable final String userName,
      @RequestParam final String password) {
    return ResponseEntity.ok(requestUser.getUserLoginDetail(userName, password));
  }

  @PostMapping("/user/groups/create")
  public ResponseEntity<Group> createUserGroup(@RequestParam final String userNameOfCreator,
      @RequestBody final Group groupToBeCreated) {
    return ResponseEntity.ok(requestUser.createGroup(userNameOfCreator, groupToBeCreated));
  }

  @PostMapping("/user/create")
  public ResponseEntity<User> createUserGroup(@RequestParam final String userNameOfCreator,
      @RequestBody final User userToBeCreated) {
    return ResponseEntity.ok(requestUser.createUser(userNameOfCreator, userToBeCreated));
  }
}
