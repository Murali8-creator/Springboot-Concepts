package com.springboot_concepts.transactional_and_aop.repo;

import com.springboot_concepts.transactional_and_aop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {}
