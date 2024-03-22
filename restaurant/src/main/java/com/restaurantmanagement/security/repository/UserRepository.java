package com.restaurantmanagement.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.restaurantmanagement.security.model.User;



@Repository
@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
  
  List<User> findAll();	
  User findById(long userId);	
  User findByEmail(String email);
}
