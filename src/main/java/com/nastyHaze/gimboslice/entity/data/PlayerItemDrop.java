package com.nastyHaze.gimboslice.entity.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;

import static com.nastyHaze.gimboslice.utility.CommonUtil.getCurrentDateAndTime;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PlayerItemDrop {

    @EmbeddedId
    private PlayerItemDropCompositeKey compositeKey;

    private Date date_received;

    public PlayerItemDrop(PlayerItemDropCompositeKey compositeKey) {
        this.compositeKey = compositeKey;
        this.date_received = getCurrentDateAndTime();
    }
}
