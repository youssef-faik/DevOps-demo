package com.example.devopsdemo.services;

import com.example.devopsdemo.entities.UserEntity;
import com.example.devopsdemo.exceptions.UsersServiceException;
import com.example.devopsdemo.repositories.UsersRepository;
import com.example.devopsdemo.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

@Service("usersService")
public class UsersServiceImpl implements UsersService {
  
  private final UsersRepository usersRepository;
  
  @Autowired
  public UsersServiceImpl(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }
  
  @Override
  public UserDto createUser(UserDto user) {
    
    if (usersRepository.findByEmail(user.getEmail()) != null) {
      throw new UsersServiceException("Record already exists");
    }
    
    ModelMapper modelMapper = new ModelMapper();
    UserEntity userEntity = modelMapper.map(user, UserEntity.class);
    
    String publicUserId = UUID.randomUUID()
                              .toString();
    userEntity.setUserId(publicUserId);
    userEntity.setEncryptedPassword(user.getPassword());
    
    UserEntity storedUserDetails = usersRepository.save(userEntity);
    
    UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);
    
    return returnValue;
  }
  
  @Override
  public List<UserDto> getUsers(int page, int limit) {
    
    if (page > 0) {
      page -= 1;
    }
    
    Pageable pageableRequest = PageRequest.of(page, limit);
    
    Page<UserEntity> usersPage = usersRepository.findAll(pageableRequest);
    List<UserEntity> users = usersPage.getContent();
    
    Type listType = new TypeToken<List<UserDto>>() {
    }.getType();
    
    return new ModelMapper().map(users, listType);
  }
  
  @Override
  public UserDto getUser(String email) {
    UserEntity userEntity = usersRepository.findByEmail(email);
    
    if (userEntity == null) {
      throw new UsersServiceException("Username Not Found");
    }
    
    UserDto returnValue = new UserDto();
    BeanUtils.copyProperties(userEntity, returnValue);
    
    return returnValue;
  }
  
  
}
