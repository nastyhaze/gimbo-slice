package com.nastyHaze.gimboslice.entity.data;

import com.nastyHaze.gimboslice.constant.CommandName;
import com.nastyHaze.gimboslice.constant.ResponseType;
import com.nastyHaze.gimboslice.entity.AbstractDomainEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


/**
 * Entity class for Bot Command(s).
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Command extends AbstractDomainEntity {

    @Enumerated(EnumType.STRING)
    private CommandName name;

    private String description;

    private String shortcut;

    private String response;

    @Enumerated(EnumType.STRING)
    private ResponseType responseType;

    private boolean active;
}
