package com.nastyHaze.gimboslice.repository;

import com.nastyHaze.gimboslice.entity.data.PlayerItemDrop;
import com.nastyHaze.gimboslice.entity.data.PlayerItemDropCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerItemDropRepository extends JpaRepository<PlayerItemDrop, PlayerItemDropCompositeKey> {

    public List<PlayerItemDrop> findAllByCompositeKeyItemName(String itemName);

    public List<PlayerItemDrop> findAllByCompositeKeyPlayerName(String playerName);

    public int countByCompositeKeyItemName(String itemName);
}
