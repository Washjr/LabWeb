package com.uneb.labweb.spring_project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.uneb.labweb.spring_project.enums.Category;
import com.uneb.labweb.spring_project.model.Course;
import com.uneb.labweb.spring_project.model.Lesson;
import com.uneb.labweb.spring_project.repository.CourseRepository;

@SpringBootApplication
public class SpringProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProjectApplication.class, args);
    }

    @Bean
    @Profile("dev")
    CommandLineRunner initDatabase(CourseRepository courseRepository) {
        return args -> {
            courseRepository.deleteAll();

            for (int i = 0; i < 20; i++) {

                Course course = new Course();
                course.setName("Curso Angular com Spring" + i);
                course.setCategory(Category.FRONT_END);

                Lesson lesson1 = new Lesson();
                lesson1.setName("Introdução");
                lesson1.setYoutubeUrl("01234567890");
                lesson1.setCourse(course);
                course.getLessons().add(lesson1);

                Lesson lesson2 = new Lesson();
                lesson2.setName("Angular");
                lesson2.setYoutubeUrl("01234567891");
                lesson2.setCourse(course);
                course.getLessons().add(lesson2);

                courseRepository.save(course);
            }
        };
    }
}
