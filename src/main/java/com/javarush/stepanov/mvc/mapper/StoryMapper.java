package com.javarush.stepanov.mvc.mapper;

import com.javarush.stepanov.mvc.model.mark.Mark;
import com.javarush.stepanov.mvc.model.story.Story;
import com.javarush.stepanov.mvc.repository.impl.MarkRepo;
import org.mapstruct.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StoryMapper {

    @Mapping(target = "marks", ignore = true)
    Story toEntity(Story.In request);

    @Mapping(target = "marks", expression = "java(mapMarksToNames(story.getMarks()))")
    Story.Out toOut(Story story);

    default Set<String> mapMarksToNames(Set<Mark> marks) {
        if (marks == null || marks.isEmpty()) {
            return Collections.emptySet();
        }
        return marks.stream()
                .map(Mark::getName)
                .collect(Collectors.toSet());
    }
}
