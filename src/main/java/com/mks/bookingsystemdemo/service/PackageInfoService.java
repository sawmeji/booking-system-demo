package com.mks.bookingsystemdemo.service;

import com.mks.bookingsystemdemo.entity.PackageInfo;
import com.mks.bookingsystemdemo.repository.PackageInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageInfoService {
    @Autowired
    private PackageInfoRepository packageInfoRepository;

    public List<PackageInfo> getPackagesByCountry(String country) {
        return packageInfoRepository.findByCountry(country);
    }

    public PackageInfo getPackageById(Long packageId) {
        return packageInfoRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found with id: " + packageId));
    }

    public void savePackage(PackageInfo packageInfo) {
        packageInfoRepository.save(packageInfo);
    }

    public List<PackageInfo> getAllPackages() {
        return packageInfoRepository.findAll();
    }

    public PackageInfo createPackage(PackageInfo packageInfo) {
        return packageInfoRepository.save(packageInfo);
    }

    public PackageInfo updatePackage(Long id, PackageInfo updatedPackage) {
        PackageInfo existingPackage = getPackageById(id);
        existingPackage.setName(updatedPackage.getName());
        existingPackage.setCountry(updatedPackage.getCountry());
        existingPackage.setCredits(updatedPackage.getCredits());
        existingPackage.setPrice(updatedPackage.getPrice());
        existingPackage.setDurationDays(updatedPackage.getDurationDays());

        return packageInfoRepository.save(existingPackage);
    }

    public void deletePackage(Long id) {
        PackageInfo existingPackage = getPackageById(id);
        packageInfoRepository.delete(existingPackage);
    }
}
