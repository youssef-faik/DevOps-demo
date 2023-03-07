package com.example.devopsdemo.repositories;

import com.example.devopsdemo.entities.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends PagingAndSortingRepository<UserEntity, Long> {
  UserEntity findByEmail(String email);
}
