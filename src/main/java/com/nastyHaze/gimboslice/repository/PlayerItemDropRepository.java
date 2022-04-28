package com.nastyHaze.gimboslice.repository;

import com.nastyHaze.gimboslice.entity.data.PlayerItemDrop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerItemDropRepository extends JpaRepository<PlayerItemDrop, Long> {

    public List<PlayerItemDrop> findAllByItemName(String itemName);

    public List<PlayerItemDrop> findAllByPlayerName(String playerName);

    public int countByItemName(String itemName);
}
