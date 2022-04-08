package com.nastyHaze.gimboslice.service.web;

import com.nastyHaze.gimboslice.entity.model.CommandDTO;

import java.util.List;

public interface CommandListingService {

    public List<CommandDTO> retrieveAllCommands();
}
