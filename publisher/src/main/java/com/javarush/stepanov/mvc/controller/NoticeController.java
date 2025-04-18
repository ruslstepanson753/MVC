package com.javarush.stepanov.mvc.controller;


import com.javarush.stepanov.mvc.model.creator.Creator;
import com.javarush.stepanov.mvc.model.notice.Notice;
import com.javarush.stepanov.mvc.service.NoticeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1.0/notices")
public class NoticeController {

    private final NoticeService service;
    private final DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;

    @GetMapping("/{id}")
    public Notice.Out getNoticeById(@PathVariable Long id) {
        return service.get(id);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<Notice.Out>> getAllNotices() {
        System.out.println();
        WebClient webClient = WebClient.create("http://localhost:24130");

        return webClient.get()
                .uri("/api/notices")
                .retrieve()
                .onStatus(
                        status -> status == HttpStatus.NOT_FOUND || status == HttpStatus.BAD_REQUEST,
                        response -> Mono.error(new RuntimeException("Error: " + response.statusCode()))
                )
                .bodyToMono(new ParameterizedTypeReference<List<Notice.Out>>() {});
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Notice.Out createNotice(@RequestBody @Valid Notice.In input) {
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
