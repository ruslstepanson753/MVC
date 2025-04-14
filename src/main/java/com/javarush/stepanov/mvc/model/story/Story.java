package com.javarush.stepanov.mvc.model.story;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_story")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long creatorId;
    String title;
    String content;
    LocalDateTime created;
    LocalDateTime modified;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class In{
        @Positive
        Long id;
        @Positive
        Long creatorId;
        @Size(min = 2, max = 64)
        String title;
        @Size(min = 4, max = 2048)
        String content;
        LocalDateTime created;
        LocalDateTime modified;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Out{
        Long id;
        Long creatorId;
        String title;
        String content;
        LocalDateTime created;
        LocalDateTime modified;
    }

}
