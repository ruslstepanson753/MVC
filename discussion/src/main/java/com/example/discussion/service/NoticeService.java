package com.example.discussion.service;



import com.example.discussion.mapper.NoticeDto;
import com.example.discussion.model.Notice;
import com.example.discussion.model.NoticeKey;
import com.example.discussion.repo.NoticeRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoticeService {

    private final IdGeneratorService idGeneratorService;
    private final NoticeRepo repo;
    private final NoticeDto mapper;

    public List<Notice.Out> getAll() {
        return repo
                .findAll()
                .stream()
                .map(mapper::out)
                .toList();
    }

    public List<Notice.Out> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return repo.
                findAll(pageable)
                .map(mapper::out)
                .getContent();
    }

    public Notice.Out get(Long id) {
        Optional<Notice> notice = repo.findOneByKeyId(id);
        return mapper.out(notice.orElseThrow(() -> new NoSuchElementException()));
    }

    public Notice.Out create(Notice.In input) {
        String country = "RU";
        Long id = idGeneratorService.getNextId();
        Long storyId = input.getStoryId();
        NoticeKey noticeKey = new NoticeKey(country,id,storyId);
        Notice notice = mapper.in(input);
        notice.setKey(noticeKey);
        return mapper.out(
                repo.save(notice));
    }

    public Notice.Out update(Notice.In input) {
        Notice notice = mapper.in(input);
        return mapper.out(
                repo.save(notice));
    }

    public void delete(Long id) {

    }

}
