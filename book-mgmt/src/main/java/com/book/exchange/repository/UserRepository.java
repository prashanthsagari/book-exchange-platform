package com.book.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.exchange.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByUsername(String username);

}