package com.nastyHaze.gimboslice.service.web;

import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.entity.model.CommandDTO;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import com.nastyHaze.gimboslice.utility.EntityToDtoConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandListingService {

    @Autowired
    private CommandRepository commandRepository;


    public List<CommandDTO> retrieveAllCommands() {
        return commandRepository.findAll()
                .stream()
                .map(EntityToDtoConversionUtil::convertCommand)
                .collect(Collectors.toList());
    }
}
