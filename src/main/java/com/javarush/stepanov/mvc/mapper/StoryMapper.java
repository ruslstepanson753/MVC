package com.javarush.stepanov.mvc.mapper;

import com.javarush.stepanov.mvc.model.mark.Mark;
import com.javarush.stepanov.mvc.model.story.Story;
import com.javarush.stepanov.mvc.repository.impl.MarkRepo;
import org.mapstruct.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StoryMapper { // Лучше использовать "Mapper" вместо "Dto" в имени

    // Изменение: уберем @Context из сигнатуры метода toEntity и передадим отдельно при вызове
    @Mapping(target = "marks", ignore = true)
    Story toEntity(Story.In request);

    // Метод, который будет вызываться извне и который будет использовать контекст
    default Story toEntityWithMarks(Story.In request, MarkRepo markRepo) {
        Story story = toEntity(request);

        // Непосредственно применяем логику из afterMapping прямо здесь
        if (request.getMarks() != null && !request.getMarks().isEmpty()) {
            Set<Mark> marks = request.getMarks().stream()
                    .map(name -> getOrCreateMark(name, markRepo))
                    .collect(Collectors.toSet());
            story.setMarks(marks);
        }

        return story;
    }

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

    private Mark getOrCreateMark(String markName, MarkRepo markRepo) {
        return markRepo.findByName(markName)
                .orElseGet(() -> markRepo.save(
                        Mark.builder().name(markName).build()
                ));
    }
}
