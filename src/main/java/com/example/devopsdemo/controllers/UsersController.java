package com.example.devopsdemo.controllers;

import com.example.devopsdemo.services.UsersService;
import com.example.devopsdemo.shared.UserDetailsRequestModel;
import com.example.devopsdemo.shared.UserDto;
import com.example.devopsdemo.shared.UserRest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UsersController {
  
  private final UsersService usersService;
  
  @PostMapping
  public UserRest createUser(@RequestBody @Valid UserDetailsRequestModel userDetails)
          throws Exception {
    ModelMapper modelMapper = new ModelMapper();
    
    UserDto userDto = new ModelMapper().map(userDetails, UserDto.class);
    UserDto createdUser = usersService.createUser(userDto);
    
    return modelMapper.map(createdUser, UserRest.class);
  }
  
  @GetMapping
  public List<UserRest> getUsers(
          @RequestParam(value = "page", defaultValue = "0") int page,
          @RequestParam(value = "limit", defaultValue = "2") int limit) {
    List<UserDto> users = usersService.getUsers(page, limit);
    
    Type listType = new TypeToken<List<UserRest>>() {
    }.getType();
    
    return new ModelMapper().map(users, listType);
  }
}
