package com.nastyHaze.gimboslice.service.data.playerItemDrop;

import com.nastyHaze.gimboslice.entity.data.PlayerItemDrop;
import com.nastyHaze.gimboslice.repository.PlayerItemDropRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerItemDropListingService {

    @Autowired
    private PlayerItemDropRepository dropRepository;


    public List<PlayerItemDrop> retrieveAllDropsByPlayerName(String playerName) {
        return dropRepository.findAllByCompositeKeyPlayerName(playerName);
    }

    public List<PlayerItemDrop> retrieveAllDropsByItemName(String itemName) {
        return dropRepository.findAllByCompositeKeyItemName(itemName);
    }

    public int countAllDropsOfItemName(String itemName) {
        return dropRepository.countByCompositeKeyItemName(itemName);
    }
}
