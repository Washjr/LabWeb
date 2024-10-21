package com.uneb.labweb.spring_project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
    CommandLineRunner initDatabase(CourseRepository courseRepository) {
        return args -> {
            Course course1 = new Course();
            course1.setName("Curso Angular");
            course1.setCategory(Category.FRONT_END);

            Lesson lesson1 = new Lesson();
			lesson1.setName("Introdução");
			lesson1.setYoutubeUrl("01234567890");
			lesson1.setCourse(course1);
			course1.getLessons().add(lesson1);

            Lesson lesson2 = new Lesson();
			lesson2.setName("Angular");
			lesson2.setYoutubeUrl("01234567891");
			lesson2.setCourse(course1);
			course1.getLessons().add(lesson2);

            courseRepository.save(course1);

            
            
            Course course2 = new Course();
            course2.setName("Curso Spring Boot");
            course2.setCategory(Category.BACK_END);

            Lesson lesson3 = new Lesson();
			lesson3.setName("Introdução");
			lesson3.setYoutubeUrl("01234567892");
			lesson3.setCourse(course2);
			course2.getLessons().add(lesson3);

            courseRepository.save(course2);
        };
    }
}

