package com.javarush.stepanov.mvc.repository.impl;

import com.javarush.stepanov.mvc.model.notice.Notice;
import com.javarush.stepanov.mvc.repository.Repo;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
public class NoticeRepo implements Repo<Notice> {
    Map<Long, Notice> memoryDatabase = new ConcurrentHashMap<>();
    @Override
    public Stream<Notice> getAll() {
        return memoryDatabase.values().stream();
    }

    @Override
    public Optional<Notice> get(Long id) {
        return Optional.ofNullable(memoryDatabase.get(id));
    }

    @Override
    public Optional<Notice> create(Notice input) {
        Long id = idGenerator.incrementAndGet();
        input.setId(id);
        memoryDatabase.put(id, input);
        return Optional.of(input);
    }

    @Override
    public Optional<Notice> update(Notice input) {
        memoryDatabase.put(input.getId(), input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(Long id) {
        return memoryDatabase.remove(id) != null;
    }
}
