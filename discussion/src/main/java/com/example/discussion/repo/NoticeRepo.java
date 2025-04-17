package com.example.discussion.repo;

import com.example.discussion.model.Notice;
import com.example.discussion.model.NoticeKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepo extends CassandraRepository<Notice, NoticeKey> {
    // Здесь можно добавить кастомные запросы
}