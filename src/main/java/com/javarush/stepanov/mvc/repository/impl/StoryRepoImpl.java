package com.javarush.stepanov.mvc.repository.impl;

import com.javarush.stepanov.mvc.model.creator.Creator;
import com.javarush.stepanov.mvc.model.story.Story;
import com.javarush.stepanov.mvc.repository.Repo;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
public class StoryRepoImpl implements Repo<Story> {
    Map<Long, Story> memoryDatabase = new ConcurrentHashMap<>();
    @Override
    public Stream<Story> getAll() {
        return memoryDatabase.values().stream();
    }

    @Override
    public Optional<Story> get(Long id) {
        return Optional.ofNullable(memoryDatabase.get(id));
    }

    @Override
    public Optional<Story> create(Story input) {
        Long id = idGenerator.incrementAndGet();
        input.setId(id);
        memoryDatabase.put(id, input);
        return Optional.of(input);
    }

    @Override
    public Optional<Story> update(Story input) {
        memoryDatabase.put(input.getId(), input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(Long id) {
        return memoryDatabase.remove(id) != null;
    }
}
