package com.nastyHaze.gimboslice.service.data;

import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandSaveService {

    @Autowired
    private CommandRepository commandRepository;


    public void save(Command command) {
        commandRepository.save(command);
    }
}
