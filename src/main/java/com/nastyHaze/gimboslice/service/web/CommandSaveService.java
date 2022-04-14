package com.nastyHaze.gimboslice.service.web;

import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandSaveService {

    @Autowired
    private CommandRepository commandRepository;

    // TODO: Make sure we validate the data stored. Right now I am doing it manually, so nothing is sanitized. Do this based on commandType.
    public void save(Command command) {
        commandRepository.save(command);
    }
}
