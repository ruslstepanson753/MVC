package com.javarush.stepanov.mvc.mapper;

import com.javarush.stepanov.mvc.model.mark.Mark;
import com.javarush.stepanov.mvc.model.story.Story;
import com.javarush.stepanov.mvc.repository.impl.MarkRepo;
import org.mapstruct.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StoryDto {

    @Mapping(target = "marks", ignore = true)
    Story in(Story.In request, @Context MarkRepo MarkRepo);

    @AfterMapping
    default void afterMapping(
            Story.In request,
            @MappingTarget Story story,
            @Context MarkRepo MarkRepo
    ) {
        if (request.getMarks() != null) {
            Set<Mark> marks = request.getMarks().stream()
                    .map(name -> getOrCreateMark(name, MarkRepo))
                    .collect(Collectors.toSet());
            story.setMarks(marks);
        }
    }

    @Mapping(target = "marks", expression = "java(mapMarksToNames(story.getMarks()))")
    Story.Out out(Story story);

    default Set<String> mapMarksToNames(Set<Mark> marks) {
        if (marks == null) {
            return Collections.emptySet();
        }
        return marks.stream()
                .map(Mark::getName)
                .collect(Collectors.toSet());
    }

    private Mark getOrCreateMark(String markName, MarkRepo MarkRepo) {
        return MarkRepo.findByName(markName)
                .orElseGet(() -> MarkRepo.save(
                        Mark.builder().name(markName).build()
                ));
    }
}
