package com.example.discussion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyClass
public class NoticeKey implements Serializable {
    @PrimaryKeyColumn(name = "country", type = PrimaryKeyType.PARTITIONED)
    private String country;

    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.CLUSTERED)
    private Long id;

    @PrimaryKeyColumn(name = "story_id", type = PrimaryKeyType.CLUSTERED)
    private Long storyId;
}