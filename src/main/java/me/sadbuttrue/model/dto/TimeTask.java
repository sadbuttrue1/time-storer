package me.sadbuttrue.model.dto;

import lombok.Builder;
import lombok.Data;
import me.sadbuttrue.model.Time;

@Data
@Builder
public class TimeTask {
    private Iterable<Time> times;
}
