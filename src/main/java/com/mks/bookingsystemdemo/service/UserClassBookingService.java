package com.mks.bookingsystemdemo.service;

import com.mks.bookingsystemdemo.entity.ClassSchedule;
import com.mks.bookingsystemdemo.entity.UserClassBooking;
import com.mks.bookingsystemdemo.entity.UserPackage;
import com.mks.bookingsystemdemo.repository.ClassScheduleRepository;
import com.mks.bookingsystemdemo.repository.UserClassBookingRepository;
import com.mks.bookingsystemdemo.repository.UserPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserClassBookingService {
    @Autowired
    private UserClassBookingRepository userClassBookingRepository;

    @Autowired
    private UserPackageRepository userPackageRepository;

    @Autowired
    private ClassScheduleRepository classScheduleRepository;

    @Autowired
    private ClassScheduleService classScheduleService;

    @Autowired
    private PackageInfoService packageInfoService;

    public List<UserClassBooking> getUserBookings(Long userId) {
        return userClassBookingRepository.findByUserId(userId);
    }

    public boolean isUserAlreadyBooked(Long userId, Long classId) {
        return userClassBookingRepository.existsByUserIdAndClassSchedule_Id(userId, classId);
    }

    public int getBookedSlotsForClass(Long classId) {
        List<UserClassBooking> bookings = userClassBookingRepository.findByClassSchedule_Id(classId);
        return bookings.size();
    }

    public boolean bookClass(Long userId, Long classId) {
        // Check if the user is already booked for the class
        if (userClassBookingRepository.existsByUserIdAndClassSchedule_Id(userId, classId)) {
            return false; // User is already booked for this class
        }

        // Check if the class has available slots
        ClassSchedule classSchedule = classScheduleService.getClassScheduleById(classId);
        if (classSchedule == null || classSchedule.getAvailableSlots() <= 0) {
            return bookClassInternal(userId, classId, true);
        }

        // Create a new UserClassBooking entity
        UserClassBooking userClassBooking = new UserClassBooking(userId, classSchedule, LocalDateTime.now());

        // Save the booking
        userClassBookingRepository.save(userClassBooking);

        // Update available slots in the class schedule
        classScheduleService.decreaseAvailableSlots(classId);


        UserPackage userPackage = userPackageRepository.findByUser_id(userId);
        if (userPackage.getPackageInfo().getCredits() <= 0) {
            throw new RuntimeException("Not enough credits in the package.");
        }

        // Update the user's package, deduct credits, set the class schedule, etc.
        userPackage.getPackageInfo().setCredits(userPackage.getPackageInfo().getCredits() - 1);

        // Calculate purchase date and expiry date (assuming a fixed duration for simplicity)
        LocalDateTime purchaseDate = LocalDateTime.now();
        LocalDateTime expiryDate = purchaseDate.plusDays(userPackage.getPackageInfo().getDurationDays());


        // Save the updated package and the booked class
        packageInfoService.savePackage(userPackage.getPackageInfo());

        return true;
    }

    public boolean cancelBooking(Long bookingId) {
        // Retrieve the booking by ID
        UserClassBooking userClassBooking = userClassBookingRepository.findById(bookingId).orElse(null);

        // Check if the booking exists
        if (userClassBooking == null) {
            return false; // Booking not found
        }

        // Check if it's within the allowed cancellation time (e.g., 4 hours before class start time)
        if (isCancellationAllowed(userClassBooking.getClassSchedule().getStartTime())) {

            // Delete the booking
            userClassBookingRepository.delete(userClassBooking);

            // Update available slots in the class schedule
            classScheduleService.increaseAvailableSlots(userClassBooking.getClassSchedule().getId());

            return true;
        }

        return false;
    }


    private boolean bookClassInternal(Long userId, Long classId, boolean waitlist) {
        if (!isUserAlreadyBookedOrWaitlisted(userId, classId)) {
            // Create a new UserClassBooking entity
            ClassSchedule classSchedule = classScheduleRepository.findByIdOrderByClassName(classId);
            UserClassBooking userClassBooking = new UserClassBooking();
            userClassBooking.setUserId(userId);
            userClassBooking.setClassSchedule(classSchedule);
            userClassBooking.setWaitlist(waitlist);

            // Save the booking to the repository
            userClassBookingRepository.save(userClassBooking);

            // Update the class booking details
            classScheduleService.updateClassBooking(classId, waitlist);

            return true;
        } else {
            // User is already booked or on the waitlist
            return false;
        }
    }

    private boolean isUserAlreadyBookedOrWaitlisted(Long userId, Long classId) {
        // Check if the user is already booked or on the waitlist for this class
        return userClassBookingRepository.existsByUserIdAndClassSchedule_IdAndWaitlist(userId, classId, false) ||
                userClassBookingRepository.existsByUserIdAndClassSchedule_IdAndWaitlist(userId, classId, true);
    }

    public boolean checkIn(Long bookingId) {

        return false;
    }

    // Check if it's within the allowed cancellation time (e.g., 4 hours before class start time)
    private boolean isCancellationAllowed(LocalDateTime classStartTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return classStartTime.isAfter(currentDateTime.plusHours(4));
    }


}
