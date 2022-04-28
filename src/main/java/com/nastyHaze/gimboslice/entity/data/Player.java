package com.nastyHaze.gimboslice.entity.data;

import com.nastyHaze.gimboslice.entity.AbstractDomainEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Player extends AbstractDomainEntity {

    private String name;

    private String slayerTask;
}
