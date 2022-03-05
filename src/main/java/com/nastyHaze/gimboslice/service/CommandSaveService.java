package com.nastyHaze.gimboslice.service;

import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandSaveService {

    @Autowired
    private CommandRepository commandRepository;

    // TODO: Get deep copying working and use soft deletes for updating
    public void updateCommand(Command command, String newResponse) {
        Command target = commandRepository.findByShortcutAndActiveTrue(command.getShortcut().replace('+', '?'));

        target.setResponse(newResponse);
        commandRepository.save(target);
    }
}
