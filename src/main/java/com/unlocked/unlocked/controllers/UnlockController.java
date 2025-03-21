package com.unlocked.unlocked.controllers;

import com.unlocked.unlocked.models.Course;
import com.unlocked.unlocked.models.Module;
import com.unlocked.unlocked.repositories.CourseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/unlock")
public class UnlockController {

    private final CourseRepository courseRepository;

    public UnlockController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PostMapping("/module")
    public ResponseEntity<String> unlockNextModule(@RequestParam String courseId, @RequestParam String chapterId, @RequestParam String moduleId) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);

        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();

            course.getChapters().forEach(chapter -> {
                if (chapter.getChapterId().equals(chapterId)) {
                    for (int i = 0; i < chapter.getModules().size(); i++) {
                        Module module = chapter.getModules().get(i);
                        if (module.getModuleId().equals(moduleId) && i + 1 < chapter.getModules().size()) {
                            chapter.getModules().get(i + 1).setUnlocked(true);
                        }
                    }
                }
            });

            courseRepository.save(course);
            return ResponseEntity.ok("Next module unlocked!");
        }

        return ResponseEntity.badRequest().body("Course not found.");
    }
}
