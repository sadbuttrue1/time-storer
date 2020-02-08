package me.sadbuttrue.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
public class Time {
    @Id
    private String id;

    private Date time;
}
