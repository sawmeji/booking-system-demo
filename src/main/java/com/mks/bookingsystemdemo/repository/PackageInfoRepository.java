package com.mks.bookingsystemdemo.repository;

import com.mks.bookingsystemdemo.entity.PackageInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PackageInfoRepository extends JpaRepository<PackageInfo, Long> {
    List<PackageInfo> findByCountry(String country);

    Optional<PackageInfo> findPackageInfoById(Long id);

//    PackageInfo findById (Long id);
}
