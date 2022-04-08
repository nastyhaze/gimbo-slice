package com.nastyHaze.gimboslice.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CommandDTO {

    private String name;

    private String command;

    private String description;

}
