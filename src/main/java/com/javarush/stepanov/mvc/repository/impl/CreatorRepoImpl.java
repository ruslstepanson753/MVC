package com.javarush.stepanov.mvc.repository.impl;

import com.javarush.stepanov.mvc.model.creator.Creator;
import com.javarush.stepanov.mvc.repository.Repo;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
public interface CreatorRepoImpl extends JpaRepository<Creator,Long> {
}
