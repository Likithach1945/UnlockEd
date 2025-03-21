package com.unlocked.unlocked.controllers;

import com.unlocked.unlocked.models.Enrollment;
import com.unlocked.unlocked.repositories.EnrollmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentController(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    //  Enroll a User in a Course
    @PostMapping
    public ResponseEntity<Enrollment> enrollUser(@RequestParam String userId, @RequestParam String courseId) {
        Enrollment enrollment = new Enrollment(userId, courseId);
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return ResponseEntity.ok(savedEnrollment);
    }

    //  Get All Enrolled Courses for a User
    @GetMapping("/{userId}")
    public ResponseEntity<List<Enrollment>> getEnrolledCourses(@PathVariable String userId) {
        List<Enrollment> enrollments = enrollmentRepository.findByUserId(userId);
        return ResponseEntity.ok(enrollments);
    }

    // unenrolling an User from a Course
    @DeleteMapping
    public ResponseEntity<Void> unenrollUser(@RequestParam String userId, @RequestParam String courseId) {
        enrollmentRepository.deleteByUserIdAndCourseId(userId, courseId);
        return ResponseEntity.noContent().build();
    }
}
