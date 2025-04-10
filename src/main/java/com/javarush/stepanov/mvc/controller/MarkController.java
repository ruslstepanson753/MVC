package com.javarush.stepanov.mvc.controller;


import com.javarush.stepanov.mvc.model.mark.Mark;
import com.javarush.stepanov.mvc.service.MarkService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1.0/marks")
public class MarkController {

    private final MarkService service;

    @GetMapping
    public Collection<Mark.Out> getAll() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mark.Out create(@RequestBody @Valid Mark.In inputDto) {
        return service.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mark.Out update(@RequestBody @Valid Mark.In inputDto) {
        try {
            return service.update(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public Mark.Out read(@PathVariable long id) {
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean delete = service.delete(id);
        if (!delete) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
