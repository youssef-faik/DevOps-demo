package com.example.devopsdemo.controllers;

import com.example.devopsdemo.services.UsersService;
import com.example.devopsdemo.shared.UserDetailsRequestModel;
import com.example.devopsdemo.shared.UserDto;
import com.example.devopsdemo.shared.UserRest;
import java.lang.reflect.Type;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public final class UsersController {
  /** UserService object field. */
  private final UsersService usersService;

  /**
   * end point for creating new users.
   *
   * @param userDetails
   * @return created user
   * @throws Exception
   */
  @PostMapping
  public UserRest createUser(
          @RequestBody @Valid final UserDetailsRequestModel userDetails
  )
      throws Exception {
    ModelMapper modelMapper = new ModelMapper();
    UserDto userDto = new ModelMapper().map(userDetails, UserDto.class);
    UserDto createdUser = usersService.createUser(userDto);
    return modelMapper.map(createdUser, UserRest.class);
  }

  /**
   * end point for retrieving all users.
   *
   * @param page
   * @param limit
   * @return list of all users
   */
  @GetMapping
  public List<UserRest> getUsers(
      @RequestParam(value = "page", defaultValue = "0") final int page,
      @RequestParam(value = "limit", defaultValue = "2") final int limit) {
    List<UserDto> users = usersService.getUsers(page, limit);
    Type listType = new TypeToken<List<UserRest>>() { }.getType();
    return new ModelMapper().map(users, listType);
  }
}
