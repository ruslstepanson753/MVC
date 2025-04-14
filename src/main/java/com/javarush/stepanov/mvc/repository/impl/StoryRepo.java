package com.javarush.stepanov.mvc.repository.impl;

import com.javarush.stepanov.mvc.model.story.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoryRepo extends JpaRepository<Story,Long> {

    Optional<Object> findByTitle(String title);
}
