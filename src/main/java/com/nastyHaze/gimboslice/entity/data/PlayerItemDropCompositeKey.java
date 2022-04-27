package com.nastyHaze.gimboslice.entity.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PlayerItemDropCompositeKey implements Serializable {

    private String playerName;

    private String itemName;

    public PlayerItemDropCompositeKey(String playerName, String itemName) {
        this.playerName = playerName;
        this.itemName = itemName;
    }
}
