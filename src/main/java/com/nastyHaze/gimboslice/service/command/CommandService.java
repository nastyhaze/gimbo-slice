package com.nastyHaze.gimboslice.service.command;

import com.nastyHaze.gimboslice.entity.data.Command;
import org.springframework.stereotype.Service;


@Service
public interface CommandService {

    public Command getCommand();

    public String retrieveCommandResponse();

    public String retrieveTemplateResponse();
}
