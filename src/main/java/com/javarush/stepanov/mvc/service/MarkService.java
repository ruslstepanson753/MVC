package com.javarush.stepanov.mvc.service;

import com.javarush.stepanov.mvc.mapper.MarkDto;
import com.javarush.stepanov.mvc.model.mark.Mark;
import com.javarush.stepanov.mvc.repository.impl.MarkRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MarkService {
    private final MarkRepo repoImpl;
    private final MarkDto mapper;

    public List<Mark.Out> getAll() {
        return repoImpl
                .getAll()
                .map(mapper::out)
                .toList();
    }

    public Mark.Out get(Long id) {
        return repoImpl
                .get(id)
                .map(mapper::out)
                .orElseThrow();
    }

    public Mark.Out create(Mark.In input) {
        return repoImpl
                .create(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public Mark.Out update(Mark.In input) {
        return repoImpl
                .update(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public boolean delete(Long id) {
        return repoImpl.delete(id);
    }

}
