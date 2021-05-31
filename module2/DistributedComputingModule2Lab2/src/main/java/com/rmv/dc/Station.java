package com.rmv.dc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Station {
    private Integer id;
    private Integer wayId;
    private String name;
    private Integer duration;
}
