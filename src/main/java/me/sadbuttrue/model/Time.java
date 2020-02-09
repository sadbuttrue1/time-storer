package me.sadbuttrue.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
public class Time {
    @Id
    private String id;

    private LocalDateTime time;
}
