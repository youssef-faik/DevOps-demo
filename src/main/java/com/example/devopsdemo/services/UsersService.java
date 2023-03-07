package com.example.devopsdemo.services;


import com.example.devopsdemo.shared.UserDto;

import java.util.List;

public interface UsersService {
  UserDto createUser(UserDto user);
  
  List<UserDto> getUsers(int page, int limit);
  
  UserDto getUser(String email);
}
