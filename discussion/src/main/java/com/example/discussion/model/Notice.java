package com.example.discussion.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tbl_notice")
public class Notice {

    @PrimaryKeyColumn(name = "country", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String country;

    @PrimaryKeyColumn(name = "id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private Long id;

    @PrimaryKeyColumn(name = "story_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private Long storyId;

    private String content;
}
