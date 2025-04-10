package com.javarush.stepanov.mvc.model.mark;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mark {
    Long id;
    String name;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class In{
        @Positive
        public Long id;
        @Size(min = 2, max = 32)
        public String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Out{
        Long id;
        String name;
    }

}
