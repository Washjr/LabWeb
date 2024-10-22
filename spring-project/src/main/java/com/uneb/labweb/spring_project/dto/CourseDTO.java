package com.uneb.labweb.spring_project.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.uneb.labweb.spring_project.enums.Category;
import com.uneb.labweb.spring_project.enums.validation.ValueOfEnum;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CourseDTO(
        Long id,
        @NotBlank @NotNull @Length(min = 5, max = 100) String name,
        @NotNull @Length(max = 10) @ValueOfEnum(enumClass = Category.class) String category,
        @NotNull @NotEmpty @Valid List<LessonDTO> lessons
) {

}
