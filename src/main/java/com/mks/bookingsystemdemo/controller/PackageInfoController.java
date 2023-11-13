package com.mks.bookingsystemdemo.controller;

import com.mks.bookingsystemdemo.entity.PackageInfo;
import com.mks.bookingsystemdemo.entity.User;
import com.mks.bookingsystemdemo.entity.UserPackage;
import com.mks.bookingsystemdemo.service.PackageInfoService;
import com.mks.bookingsystemdemo.service.UserPackageService;
import com.mks.bookingsystemdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
public class PackageInfoController {
    @Autowired
    private PackageInfoService packageInfoService;
    @Autowired
    private UserPackageService userPackageService;
    @Autowired
    private UserService userService;

    @GetMapping("/country/{country}")
    public ResponseEntity<List<PackageInfo>> getPackagesByCountry(@PathVariable String country) {
        List<PackageInfo> packages = packageInfoService.getPackagesByCountry(country);
        return new ResponseEntity<>(packages, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserPackage>> getUserPackages(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        List<UserPackage> userPackages = userPackageService.getUserPackages(user);
        return new ResponseEntity<>(userPackages, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PackageInfo>> getAllPackages() {
        List<PackageInfo> packageInfoList = packageInfoService.getAllPackages();
        return new ResponseEntity<>(packageInfoList, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<PackageInfo> getPackageById(@PathVariable Long id) {
        PackageInfo packageInfo = packageInfoService.getPackageById(id);
        return new ResponseEntity<>(packageInfo, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PackageInfo> createPackage(@RequestBody PackageInfo packageInfo) {
        PackageInfo createdPackage = packageInfoService.createPackage(packageInfo);
        return new ResponseEntity<>(createdPackage, HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<PackageInfo> updatePackage(@PathVariable Long id, @RequestBody PackageInfo packageInfo) {
        PackageInfo updatedPackage = packageInfoService.updatePackage(id, packageInfo);
        return new ResponseEntity<>(updatedPackage, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        packageInfoService.deletePackage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
