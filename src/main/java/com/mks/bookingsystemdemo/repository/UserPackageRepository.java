package com.mks.bookingsystemdemo.repository;

import com.mks.bookingsystemdemo.entity.User;
import com.mks.bookingsystemdemo.entity.UserPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPackageRepository extends JpaRepository<UserPackage, Long> {
    List<UserPackage> findByUser(User user);

    UserPackage findByUser_id(Long userId);
}

