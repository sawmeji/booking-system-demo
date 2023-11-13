package com.mks.bookingsystemdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class UserPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private PackageInfo packageInfo;

    private LocalDateTime purchaseDate;
    private LocalDateTime expiryDate;

    public UserPackage() {
    }

    public UserPackage(User user, PackageInfo packageInfo, LocalDateTime purchaseDate, LocalDateTime expiryDate) {
        this.user = user;
        this.packageInfo = packageInfo;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
    }


}
