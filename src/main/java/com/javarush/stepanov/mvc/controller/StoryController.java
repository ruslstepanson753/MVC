package com.javarush.stepanov.mvc.controller;

import com.javarush.stepanov.mvc.model.story.Story;
import com.javarush.stepanov.mvc.service.StoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1.0/stories")
public class StoryController {

    private final StoryService service;

    @GetMapping("/{id}")
    public Story.Out getStoryById(@PathVariable Long id) {
        return service.get(id);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Story.Out> getAllStorys2(
    )   {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Story.Out createStory(@RequestBody @Valid Story.In input) {
        try {
            return service.createStory(input);
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

}