package com.javarush.stepanov.mvc.repository.impl;

import com.javarush.stepanov.mvc.model.mark.Mark;
import com.javarush.stepanov.mvc.repository.Repo;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
public class MarkRepo implements Repo<Mark> {

    Map<Long, Mark> memoryDatabase = new ConcurrentHashMap<>();

    @Override
    public Stream<Mark> getAll() {
        return memoryDatabase.values().stream();
    }

    @Override
    public Optional<Mark> get(Long id) {
        return Optional.ofNullable(memoryDatabase.get(id));
    }

    @Override
    public Optional<Mark> create(Mark input) {
        Long id = idGenerator.incrementAndGet();
        input.setId(id);
        memoryDatabase.put(id,input);
        return Optional.of(input);
    }

    @Override
    public Optional<Mark> update(Mark input) {
        memoryDatabase.put(input.getId(),input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(Long id) {
        return memoryDatabase.remove(id) != null;
    }
}
