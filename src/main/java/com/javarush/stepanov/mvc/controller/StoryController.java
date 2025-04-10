package com.javarush.stepanov.mvc.controller;


import com.javarush.stepanov.mvc.model.story.Story;
import com.javarush.stepanov.mvc.service.StoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1.0/stories")
public class StoryController {

    private final StoryService storyService;

    @GetMapping
    public Collection<Story.Out> getAll() {
        return storyService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Story.Out create(@RequestBody @Valid Story.In inputDto) {
        return storyService.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Story.Out update(@RequestBody @Valid Story.In inputDto) {
        try {
            return storyService.update(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public Story.Out read(@PathVariable long id) {
        return storyService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean delete = storyService.delete(id);
        if (!delete) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
