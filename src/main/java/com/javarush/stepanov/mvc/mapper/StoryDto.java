package com.javarush.stepanov.mvc.mapper;

import com.javarush.stepanov.mvc.model.story.Story;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StoryDto {
    Story.Out out(Story entity);

    Story in(Story.In inputDto);
}
