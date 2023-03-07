package com.example.devopsdemo.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
public class UserEntity implements Serializable {
  
  private static final long serialVersionUID = 5313493413859894403L;
  
  @Id
  @GeneratedValue
  private long id;
  
  @Column(nullable = false)
  private String userId;
  
  @Column(nullable = false, length = 50)
  private String firstName;
  
  @Column(nullable = false, length = 50)
  private String lastName;
  
  @Column(nullable = false, length = 120)
  private String email;
  
  @Column(nullable = false)
  private String encryptedPassword;
  
}
