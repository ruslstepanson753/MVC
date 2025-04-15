package com.javarush.stepanov.mvc.mapper;

import com.javarush.stepanov.mvc.model.mark.Mark;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MarkDto {

    Mark.Out out(Mark entity);

    Mark in(Mark.In inputDto);
}
