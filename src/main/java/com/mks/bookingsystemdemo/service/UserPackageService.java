package com.mks.bookingsystemdemo.service;

import com.mks.bookingsystemdemo.entity.ClassSchedule;
import com.mks.bookingsystemdemo.entity.PackageInfo;
import com.mks.bookingsystemdemo.entity.User;
import com.mks.bookingsystemdemo.entity.UserPackage;
import com.mks.bookingsystemdemo.repository.PackageInfoRepository;
import com.mks.bookingsystemdemo.repository.UserPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserPackageService {
    @Autowired
    private UserPackageRepository userPackageRepository;
    @Autowired
    private PackageInfoRepository packageInfoRepository;
    @Autowired
    private PackageInfoService packageInfoService;

    public List<UserPackage> getUserPackages(User user) {
        return userPackageRepository.findByUser(user);
    }

    public UserPackage purchasePackage(User user, PackageInfo packageInfo) {
        // Check if the package is valid for purchase
        if (!isPackageValidForPurchase(user, packageInfo)) {
            throw new RuntimeException("Package is not valid for purchase.");
        }

        // Calculate purchase date and expiry date
        LocalDateTime purchaseDate = LocalDateTime.now();
        LocalDateTime expiryDate = purchaseDate.plusDays(packageInfo.getDurationDays());

        UserPackage userPackage = new UserPackage(user, packageInfo, purchaseDate, expiryDate);

        // Save the purchased package
        return userPackageRepository.save(userPackage);
    }

    public boolean isPackageValidForPurchase(User user, PackageInfo packageInfo) {

        UserPackage userPackage = userPackageRepository.findByUser_id(user.getId());

        if(userPackage != null){
            Optional<PackageInfo> packageInfo1 = packageInfoRepository.findPackageInfoById(userPackage.getPackageInfo().getId());
            if(packageInfo.getCountry().equals(packageInfo1.get().getCountry())){
                return false;
            }
        }
        return true;
    }


    public boolean isPackageValidForClass(PackageInfo packageInfo, ClassSchedule classSchedule) {
        return packageInfo.getCountry().equals(classSchedule.getCountry());
    }


}
