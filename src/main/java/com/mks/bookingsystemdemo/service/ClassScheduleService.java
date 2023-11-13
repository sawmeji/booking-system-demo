package com.mks.bookingsystemdemo.service;

import com.mks.bookingsystemdemo.dto.ClassScheduleDto;
import com.mks.bookingsystemdemo.entity.ClassSchedule;
import com.mks.bookingsystemdemo.entity.UserClassBooking;
import com.mks.bookingsystemdemo.repository.ClassScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClassScheduleService {
    @Autowired
    private ClassScheduleRepository classScheduleRepository;
    @Autowired
    private UserClassBookingService userClassBookingService;

    public List<ClassSchedule> getClassSchedulesByCountry(String country) {
        return classScheduleRepository.findByCountry(country);
    }
    public ClassSchedule getClassScheduleById(Long classId){
        return classScheduleRepository.findClassScheduleById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found with id: "+ classId));
    }

    public boolean checkAvailability(long classId) {

        ClassSchedule classSchedule = classScheduleRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + classId));

        int bookedSlots = userClassBookingService.getBookedSlotsForClass(classId);

        return bookedSlots < classSchedule.getAvailableSlots();
    }

    public void createClassSchedule(ClassScheduleDto classSchedule) {
        ClassSchedule schedule = new ClassSchedule(classSchedule);
        classScheduleRepository.save(schedule);
    }

    public boolean updateClassSchedule(Long classId, ClassSchedule updatedClassSchedule) {
        Optional<ClassSchedule> optionalClassSchedule = classScheduleRepository.findById(classId);

        if (optionalClassSchedule.isPresent()) {
            ClassSchedule existingClassSchedule = optionalClassSchedule.get();
            // Update the fields of the existing class schedule
            existingClassSchedule.setClassName(updatedClassSchedule.getClassName());
            existingClassSchedule.setCountry(updatedClassSchedule.getCountry());
            existingClassSchedule.setStartTime(updatedClassSchedule.getStartTime());
            existingClassSchedule.setEndTime(updatedClassSchedule.getEndTime());
            existingClassSchedule.setRequiredCredits(updatedClassSchedule.getRequiredCredits());
            existingClassSchedule.setAvailableSlots(updatedClassSchedule.getAvailableSlots());

            // Save the updated class schedule
            classScheduleRepository.save(existingClassSchedule);
            return true;
        }

        return false;
    }

    public boolean deleteClassSchedule(Long classId) {
        Optional<ClassSchedule> optionalClassSchedule = classScheduleRepository.findById(classId);

        if (optionalClassSchedule.isPresent()) {
            // Delete the class schedule
            classScheduleRepository.delete(optionalClassSchedule.get());
            return true;
        }

        return false;
    }

    public void updateClassBooking(Long classId, boolean waitlist) {
        ClassSchedule classSchedule = classScheduleRepository.findById(classId).orElse(null);

        if (classSchedule != null) {
            if (waitlist) {
                // Update waitlist count
                classSchedule.setWaitlistCount(classSchedule.getWaitlistCount() + 1);
            } else {
                // Update available slots
                int availableSlots = classSchedule.getAvailableSlots();
                if (availableSlots > 0) {
                    classSchedule.setAvailableSlots(availableSlots - 1);
                } else {
                    // Handle the case where a booked user cancels, and a user from the waitlist is added
                    if (classSchedule.getWaitlistCount() > 0) {
                        classSchedule.setWaitlistCount(classSchedule.getWaitlistCount() - 1);
                    }
                }
            }

            // Save the updated class schedule
            classScheduleRepository.save(classSchedule);
        }
    }

    @CacheEvict(value = "classSchedules", key = "#classSchedule.country")
    public boolean bookClass(long userId, long classId) {

        // Check if the class is available
        if (!checkAvailability(classId)) {
            throw new RuntimeException("Class is already full. Unable to book.");
        }

        ClassSchedule classSchedule = classScheduleRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + classId));

        // Check if the user is already booked for this class
        if (userClassBookingService.isUserAlreadyBooked(userId, classId)) {
            throw new RuntimeException("User already booked for this class.");
        }

        // Book the class
        UserClassBooking userClassBooking = new UserClassBooking(userId, classSchedule, LocalDateTime.now());
        userClassBookingService.bookClass(userId, classId);

        return true;
    }

    public void decreaseAvailableSlots(Long classId) {
        ClassSchedule classSchedule = classScheduleRepository.findById(classId).orElse(null);

        if (classSchedule != null && classSchedule.getAvailableSlots() > 0) {
            classSchedule.setAvailableSlots(classSchedule.getAvailableSlots() - 1);
            classScheduleRepository.save(classSchedule);
        }
    }
    public void increaseAvailableSlots(Long classId) {
        ClassSchedule classSchedule = classScheduleRepository.findById(classId).orElse(null);

        if (classSchedule != null) {
            classSchedule.setAvailableSlots(classSchedule.getAvailableSlots() + 1);
            classScheduleRepository.save(classSchedule);
        }
    }


}
