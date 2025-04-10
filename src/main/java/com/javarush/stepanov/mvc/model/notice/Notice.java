package com.javarush.stepanov.mvc.model.notice;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    Long id;
    Long storyId;
    String content;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class In{
        @Positive
        Long id;
        @Positive
        Long storyId;
        @Size(min = 4, max = 2048)
        String content;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Out{
        Long id;
        Long storyId;
        String content;
    }

}
