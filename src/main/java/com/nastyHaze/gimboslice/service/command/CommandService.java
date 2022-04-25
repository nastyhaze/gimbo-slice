package com.nastyHaze.gimboslice.service.command;

import com.nastyHaze.gimboslice.entity.data.Command;
import org.springframework.stereotype.Service;

@Service
public interface CommandService {

    public String retrieveCommandResponse();

    public String retrieveTemplateResponse();

    public Command getCommand();
}
