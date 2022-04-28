package com.nastyHaze.gimboslice.service.data.playerItemDrop;

import com.nastyHaze.gimboslice.entity.data.PlayerItemDrop;
import com.nastyHaze.gimboslice.repository.ItemRepository;
import com.nastyHaze.gimboslice.repository.PlayerItemDropRepository;
import com.nastyHaze.gimboslice.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nastyHaze.gimboslice.utility.CommonUtil.getCurrentDateAndTime;

@Service
public class PlayerItemDropSaveService {

    @Autowired
    private PlayerItemDropRepository dropRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ItemRepository itemRepository;

    public boolean save(List<String> dropData) {
        boolean isSuccess = false;
        String playerData = dropData.get(0).trim();
        String itemData = dropData.get(1).trim();

        if(isValidPlayer(playerData) && isValidItem(itemData)) {
            PlayerItemDrop drop = new PlayerItemDrop(playerData, itemData);
            drop.setDateReceived(getCurrentDateAndTime());
            dropRepository.save(drop);
            isSuccess = true;
        }

        return isSuccess;
    }

    private boolean isValidPlayer(String playerName) {
        return playerRepository.existsByName(playerName);
    }

    private boolean isValidItem(String itemName) {
        return itemRepository.existsByName(itemName);
    }
}
