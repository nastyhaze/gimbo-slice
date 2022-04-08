package com.nastyHaze.gimboslice.entity.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CommandDTO {

    private String name;

    private String command;

    private String description;

}
