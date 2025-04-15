package com.javarush.stepanov.mvc.service;

import com.javarush.stepanov.mvc.mapper.StoryMapper;
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
    private final StoryMapper mapper;
    private final CreatorRepo creatorRepo;

    public List<Story.Out> getAll() {
        return storyRepo
                .findAll()
                .stream()
                .map(mapper::toOut)
                .toList();
    }

    public List<Story.Out> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return storyRepo.
                findAll(pageable)
                .map(mapper::toOut)
                .getContent();
    }

    public Story.Out get(Long id) {
        return storyRepo
                .findById(id)
                .map(mapper::toOut)
                .orElseThrow();
    }

    @Transactional
    public Story.Out createStory(Story.In request) {
        Story story = mapper.toEntityWithMarks(request, markRepo);
        Story savedStory = storyRepo.save(story);
        return mapper.toOut(savedStory);
    }



    @Transactional
    public void delete(Long id) {
        Story story = storyRepo.findById(id).orElseThrow(()-> new NoSuchElementException("Story not found with id: " + id));
        storyRepo.deleteById(id);
    }

}
