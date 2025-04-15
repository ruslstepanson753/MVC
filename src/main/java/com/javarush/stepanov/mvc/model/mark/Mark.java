package com.javarush.stepanov.mvc.model.mark;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javarush.stepanov.mvc.model.story.Story;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_mark")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "marks")
    @Builder.Default
    @JsonIgnore
    private Set<Story> stories = new HashSet<>();

    // DTO классы
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class In {
        private Long id;
        private String name;
        private Set<Long> storyIds; // ID связанных историй
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Out {
        private Long id;
        private String name;
        private Set<String> storyTitles; // Названия связанных историй
    }
}