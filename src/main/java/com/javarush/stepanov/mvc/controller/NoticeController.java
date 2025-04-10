package com.javarush.stepanov.mvc.controller;


import com.javarush.stepanov.mvc.model.notice.Notice;
import com.javarush.stepanov.mvc.service.NoticeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1.0/notices")
public class NoticeController {

    private final NoticeService service;

    @GetMapping
    public Collection<Notice.Out> getAll() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Notice.Out create(@RequestBody @Valid Notice.In inputDto) {
        return service.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Notice.Out update(@RequestBody @Valid Notice.In inputDto) {
        try {
            return service.update(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public Notice.Out read(@PathVariable long id) {
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
