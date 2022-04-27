package com.nastyHaze.gimboslice.entity.data;

import com.nastyHaze.gimboslice.constant.CommandName;
import com.nastyHaze.gimboslice.constant.ResponseType;
import com.nastyHaze.gimboslice.entity.AbstractDomainEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import java.util.List;


/**
 * Entity class for Bot Command(s).
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Command extends AbstractDomainEntity {

    @Enumerated(EnumType.STRING)
    private CommandName name;

    private String description;

    private String shortcut;

    @EqualsAndHashCode.Exclude
    private String response;

    @Getter
    @Enumerated(EnumType.STRING)
    private ResponseType responseType;

    private boolean active;
}
