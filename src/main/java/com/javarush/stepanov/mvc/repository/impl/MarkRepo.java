package com.javarush.stepanov.mvc.repository.impl;

import com.javarush.stepanov.mvc.model.mark.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MarkRepo extends JpaRepository<Mark,Long> {

    Optional<Mark> findByName(String markName);
}
