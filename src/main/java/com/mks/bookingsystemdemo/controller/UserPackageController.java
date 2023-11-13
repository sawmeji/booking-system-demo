package com.mks.bookingsystemdemo.controller;

import com.mks.bookingsystemdemo.entity.PackageInfo;
import com.mks.bookingsystemdemo.entity.User;
import com.mks.bookingsystemdemo.entity.UserPackage;
import com.mks.bookingsystemdemo.service.ClassScheduleService;
import com.mks.bookingsystemdemo.service.PackageInfoService;
import com.mks.bookingsystemdemo.service.UserPackageService;
import com.mks.bookingsystemdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-packages")
public class UserPackageController {
    @Autowired
    private UserPackageService userPackageService;
    @Autowired
    private UserService userService;
    @Autowired
    private PackageInfoService packageInfoService;
    @Autowired
    private ClassScheduleService classScheduleService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserPackage>> getUserPackages(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        List<UserPackage> userPackages = userPackageService.getUserPackages(user);
        return new ResponseEntity<>(userPackages, HttpStatus.OK);
    }

    @PostMapping("/purchase/{userId}/{packageId}")
    public ResponseEntity<UserPackage> purchasePackage(
            @PathVariable Long userId,
            @PathVariable Long packageId
    ) {
        User user = userService.getUserById(userId);
        PackageInfo packageInfo = packageInfoService.getPackageById(packageId);

        UserPackage purchasedPackage = userPackageService.purchasePackage(user, packageInfo);
        return new ResponseEntity<>(purchasedPackage, HttpStatus.CREATED);
    }

}
