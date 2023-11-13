package com.mks.bookingsystemdemo.controller;

import com.mks.bookingsystemdemo.entity.ClassSchedule;
import com.mks.bookingsystemdemo.entity.UserClassBooking;
import com.mks.bookingsystemdemo.repository.ClassScheduleRepository;
import com.mks.bookingsystemdemo.repository.UserClassBookingRepository;
import com.mks.bookingsystemdemo.service.UserClassBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-bookings")
public class UserClassBookingController {
    @Autowired
    private UserClassBookingService userClassBookingService;
    @Autowired
    private ClassScheduleRepository classScheduleRepository;
    @Autowired
    private UserClassBookingRepository userClassBookingRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserClassBooking>> getUserBookings(@PathVariable Long userId) {
        List<UserClassBooking> userBookings = userClassBookingService.getUserBookings(userId);
        return new ResponseEntity<>(userBookings, HttpStatus.OK);
    }

    @PostMapping("/book/{userId}/{classId}")
    public ResponseEntity<String> bookClass(@PathVariable Long userId, @PathVariable Long classId) {
        if (!userClassBookingService.isUserAlreadyBooked(userId, classId)) {
            // User is not already booked, proceed with booking
            boolean success = userClassBookingService.bookClass(userId, classId);
            if (success) {
                return new ResponseEntity<>("Class booked successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to book class. Please try again.", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("User is already booked for this class.", HttpStatus.BAD_REQUEST);
        }
    }

    @Scheduled(fixedDelay = 60000) // Runs every minute, adjust as needed
    public void processWaitlist() {
        List<ClassSchedule> fullClasses = classScheduleRepository.findFullClasses();

        for (ClassSchedule classSchedule : fullClasses) {
            List<UserClassBooking> waitlistedUsers = userClassBookingRepository.findByClassSchedule_IdAndWaitlist(classSchedule.getId(), true);

            if (!waitlistedUsers.isEmpty()) {
                // Update the first user from the waitlist to be booked
                UserClassBooking firstWaitlistedUser = waitlistedUsers.get(0);
                firstWaitlistedUser.setWaitlist(false);
                userClassBookingRepository.save(firstWaitlistedUser);

                // Update class schedule details
                classSchedule.setAvailableSlots(classSchedule.getAvailableSlots() - 1);
                classSchedule.setWaitlistCount(classSchedule.getWaitlistCount() - 1);
                classScheduleRepository.save(classSchedule);
            }
        }
    }

    @PostMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        boolean success = userClassBookingService.cancelBooking(bookingId);
        if (success) {
            return new ResponseEntity<>("Booking canceled successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to cancel booking. Please try again.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/check-in/{bookingId}")
    public ResponseEntity<String> checkIn(@PathVariable Long bookingId) {
        return new ResponseEntity<>("Check-in successful.", HttpStatus.OK);
    }


}
