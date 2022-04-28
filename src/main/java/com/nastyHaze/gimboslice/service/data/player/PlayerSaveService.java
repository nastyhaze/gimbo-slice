package com.nastyHaze.gimboslice.service.data.player;

import com.nastyHaze.gimboslice.entity.data.Player;
import com.nastyHaze.gimboslice.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PlayerSaveService {

    @Autowired
    private PlayerRepository playerRepository;

    public boolean updateTask(List<String> playerTaskData) {
        if (playerTaskData.size() != 2) {
            return false;
        }

        String playerName = playerTaskData.get(0).trim();
        String task = playerTaskData.get(1).trim();

        Player targetPlayer = playerRepository.findOneByName(playerName);

        if (Objects.isNull(targetPlayer)) {
            return false;
        }

        if(task.equalsIgnoreCase("done")) {
            targetPlayer.setSlayerTask(null);
        } else {
            targetPlayer.setSlayerTask(task);
        }

        playerRepository.save(targetPlayer);

        return true;
    }
}
