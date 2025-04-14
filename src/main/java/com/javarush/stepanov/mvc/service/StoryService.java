package com.javarush.stepanov.mvc.service;

import com.javarush.stepanov.mvc.mapper.StoryDto;
import com.javarush.stepanov.mvc.model.story.Story;
import com.javarush.stepanov.mvc.repository.impl.CreatorRepo;
import com.javarush.stepanov.mvc.repository.impl.StoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class StoryService {

    private final StoryRepo repo;
    private final StoryDto mapper;
    private final CreatorRepo creatorRepo;

    public List<Story.Out> getAll() {
        return repo
                .findAll()
                .stream()
                .map(mapper::out)
                .toList();
    }

    public List<Story.Out> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return repo.
                findAll(pageable)
                .map(mapper::out)
                .getContent();
    }

    public Story.Out get(Long id) {
        return repo
                .findById(id)
                .map(mapper::out)
                .orElseThrow();
    }

    public Story.Out create(Story.In input) {
        Story creator = mapper.in(input);

        // Проверяем существование записи с таким же title
        repo.findByTitle(creator.getTitle()).ifPresent(existing -> {
            throw new NoSuchElementException();
        });

        creatorRepo.findById(input.getCreatorId())
                .orElseThrow(() -> new NoSuchElementException());

        // Устанавливаем даты создания/изменения
        creator.setCreated(LocalDateTime.now());
        creator.setModified(LocalDateTime.now());

        return mapper.out(repo.save(creator));
    }

    public Story.Out update(Story.In input) {
        Story existing = repo.findById(input.getId())
                .orElseThrow(() -> new NoSuchElementException("Creator not found with id: " + input.getId()));
        Story updated = mapper.in(input); // или частичное обновление:
         existing.setCreatorId(input.getCreatorId());
         existing.setTitle(input.getTitle());
         existing.setContent(input.getContent());
         existing.setCreated(input.getCreated());
         existing.setModified(LocalDateTime.now());
        return mapper.out(repo.save(updated));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

}
