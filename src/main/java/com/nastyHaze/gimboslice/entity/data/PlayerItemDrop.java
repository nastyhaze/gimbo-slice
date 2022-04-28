package com.nastyHaze.gimboslice.entity.data;

import com.nastyHaze.gimboslice.entity.AbstractDomainEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

import static com.nastyHaze.gimboslice.utility.CommonUtil.getCurrentDateAndTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PlayerItemDrop extends AbstractDomainEntity {

    private String playerName;

    private String itemName;

    private Date dateReceived;

    public PlayerItemDrop(String playerName, String itemName) {
        this.playerName = playerName;
        this.itemName = itemName;
        this.dateReceived = getCurrentDateAndTime();
    }
}
