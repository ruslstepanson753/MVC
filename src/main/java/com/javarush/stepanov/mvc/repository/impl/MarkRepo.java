package com.javarush.stepanov.mvc.repository.impl;

import com.javarush.stepanov.mvc.model.mark.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepo extends JpaRepository<Mark,Long> {
}
