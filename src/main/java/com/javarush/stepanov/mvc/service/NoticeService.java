package com.javarush.stepanov.mvc.service;

import com.javarush.stepanov.mvc.mapper.NoticeDto;
import com.javarush.stepanov.mvc.mapper.StoryDto;
import com.javarush.stepanov.mvc.model.notice.Notice;
import com.javarush.stepanov.mvc.repository.impl.NoticeRepoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoticeService {
    private final NoticeRepoImpl repoImpl;
    private final NoticeDto mapper;

    public List<Notice.Out> getAll() {
        return repoImpl
                .getAll()
                .map(mapper::out)
                .toList();
    }

    public Notice.Out get(Long id) {
        return repoImpl
                .get(id)
                .map(mapper::out)
                .orElseThrow();
    }

    public Notice.Out create(Notice.In input) {
        return repoImpl
                .create(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public Notice.Out update(Notice.In input) {
        return repoImpl
                .update(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public boolean delete(Long id) {
        return repoImpl.delete(id);
    }

}
