package com.uneb.labweb.spring_project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.uneb.labweb.spring_project.dto.CourseDTO;
import com.uneb.labweb.spring_project.dto.CoursePageDTO;
import com.uneb.labweb.spring_project.dto.mapper.CourseMapper;
import com.uneb.labweb.spring_project.exception.RecordNotFoundException;
import com.uneb.labweb.spring_project.model.Course;
import com.uneb.labweb.spring_project.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Validated
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CoursePageDTO list(@PositiveOrZero int page, @Positive @Max(100) int pageSize) {
        
        Page<Course> pageCourse = courseRepository.findAll(PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC,"name")));
        
        List<CourseDTO> courses = pageCourse
                .get()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());

        return new CoursePageDTO(courses, pageCourse.getTotalElements(), pageCourse.getTotalPages());
    }

    public CourseDTO findById(@NotNull @Positive Long id) {
        return courseRepository.findById(id)
                .map(courseMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(@Valid @NotNull CourseDTO course) {
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO courseDTO) {
        return courseRepository.findById(id)
                .map(recordFound -> {             
                    recordFound.setName(courseDTO.name());
                    recordFound.setCategory(courseMapper.convertCategoryValue(courseDTO.category()));
                    
                    Course course = courseMapper.toEntity(courseDTO);
                    
                    recordFound.getLessons().clear();                    
                    course.getLessons().forEach(recordFound.getLessons()::add);
                    
                    return courseMapper.toDTO(courseRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        courseRepository.delete(courseRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));         
    }
}
