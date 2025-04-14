package com.javarush.stepanov.mvc.controller;

import com.javarush.stepanov.mvc.model.creator.Creator;
import com.javarush.stepanov.mvc.service.CreatorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

//swagger http://localhost:24110/swagger-ui/index.html#/
//swagger json http://localhost:24110/v3/api-docs
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1.0/creators")
public class CreatorController {

    private final CreatorService creatorService;

    @GetMapping("/{id}")
    public Creator.Out getCreatorById(@PathVariable Long id) {
        return creatorService.get(id);

    }

//    @GetMapping
//    public List<Creator.Out> getAllCreators(
//            @Min(0) @RequestParam(defaultValue = "0") int page,
//            @Min(1) @RequestParam(defaultValue = "10") int size
//    )   {
//        return creatorService.getAll(page, size);
//    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Creator.Out> getAllCreators2(
    )   {
        return creatorService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Creator.Out createCreator(@RequestBody @Valid Creator.In input) {
        try {
            return creatorService.create(input);
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Creator.Out  updateCreator(@RequestBody @Valid Creator.In input) {
        try {
            return creatorService.update(input);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteCreator(@PathVariable Long id) {
        creatorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}