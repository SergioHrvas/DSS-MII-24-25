package com.dss.spring.data.rest;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(collectionResourceRel="tasks",path="tasks")
public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

}
