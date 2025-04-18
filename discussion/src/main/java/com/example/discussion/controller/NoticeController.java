package com.example.discussion.controller;


import com.example.discussion.model.Notice;
import com.example.discussion.service.NoticeService;
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
@RequestMapping("/api/v1.0/notices")
public class NoticeController {

    private final NoticeService service;

    @GetMapping("/{id}")
    public Notice.Out getNoticeById(@PathVariable Long id) {
        return service.get(id);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Notice.Out> getAllNotices2(
    )   {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Notice.Out createNotice(@RequestBody @Valid Notice.In input) {
        System.out.println();
        try {
            return service.create(input);
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Notice.Out  updateNotice(@RequestBody @Valid Notice.In input) {
        try {
            return service.update(input);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteNotice(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
