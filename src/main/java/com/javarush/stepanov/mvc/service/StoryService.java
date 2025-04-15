package com.javarush.stepanov.mvc.service;

import com.javarush.stepanov.mvc.mapper.StoryDto;
import com.javarush.stepanov.mvc.model.mark.Mark;
import com.javarush.stepanov.mvc.model.story.Story;
import com.javarush.stepanov.mvc.repository.impl.CreatorRepo;
import com.javarush.stepanov.mvc.repository.impl.MarkRepo;
import com.javarush.stepanov.mvc.repository.impl.StoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class StoryService {

    private final StoryRepo storyRepo;
    private final MarkRepo markRepo;
    private final StoryDto mapper;
    private final CreatorRepo creatorRepo;

    public List<Story.Out> getAll() {
        return storyRepo
                .findAll()
                .stream()
                .map(mapper::out)
                .toList();
    }

    public List<Story.Out> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return storyRepo.
                findAll(pageable)
                .map(mapper::out)
                .getContent();
    }

    public Story.Out get(Long id) {
        return storyRepo
                .findById(id)
                .map(mapper::out)
                .orElseThrow();
    }

    @Transactional
    public Story.Out createStory(Story.In request) {
        // Получаем или создаем метки
        Set<Mark> marks = request.getMarks().stream()
                .map(markName -> markRepo.findByName(markName)
                        .orElseGet(() -> markRepo.save(Mark.builder().name(markName).build())))
                .collect(Collectors.toSet());
        // Создаем историю
        Story story = Story.builder()
                .creatorId(request.getCreatorId())
                .title(request.getTitle())
                .content(request.getContent())
                .marks(marks)
                .build();

        Story savedStory = storyRepo.save(story);

        return convertToResponse(savedStory);
    }

    private Story.Out convertToResponse(Story story) {
        return Story.Out.builder()
                .id(story.getId())
                .creatorId(story.getCreatorId())
                .title(story.getTitle())
                .content(story.getContent())
                .created(story.getCreated())
                .modified(story.getModified())
                .marks(story.getMarks().stream()
                        .map(Mark::getName)
                        .collect(Collectors.toSet()))
                .build();
    }



    @Transactional
    public void delete(Long id) {
        Story story = storyRepo.findById(id).orElseThrow(()-> new NoSuchElementException("Story not found with id: " + id));
        storyRepo.deleteById(id);
    }

}
