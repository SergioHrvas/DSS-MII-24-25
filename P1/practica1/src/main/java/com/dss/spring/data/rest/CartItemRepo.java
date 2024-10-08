package com.dss.spring.data.rest;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(collectionResourceRel="tasks",path="tasks")
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

}
