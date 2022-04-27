package com.nastyHaze.gimboslice.service.data.command;

import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.entity.model.CommandDTO;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import com.nastyHaze.gimboslice.service.command.CommandService;
import com.nastyHaze.gimboslice.utility.EntityToDtoConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.nastyHaze.gimboslice.utility.EntityToDtoConversionUtil.convertCommand;

@Service
public class CommandListingService {

    @Autowired
    private CommandRepository commandRepository;

    private Map<Command, CommandService> commandServiceMap;


    @Bean
    private Map<Command, CommandService> setCommandServiceMap(List<CommandService> commandServiceList) {
        commandServiceMap = commandServiceList.stream().collect(Collectors.toMap(CommandService::getCommand, Function.identity()));
        return commandServiceMap;
    }

    public Map<Command, CommandService> getCommandServiceMap() {
        return commandServiceMap;
    }

    /**
     * Returns every active Command from the DB, each converted to a UI-friendly object.
     * @return
     */
    public List<CommandDTO> retrieveAllCommands() {
        return commandRepository.findAll()
                .stream()
                .map(EntityToDtoConversionUtil::convertCommand)
                .collect(Collectors.toList());
    }

    public CommandDTO retrieveCommandByCommandShortcut(String shortcut) {
        Command command = commandRepository.findByShortcutAndActiveTrue(shortcut);

        return convertCommand(command);
    }

    public String retrieveCommandResponseByCommandShortcut(String shortcut) {
        Command command = commandRepository.findByShortcutAndActiveTrue(shortcut);

        return commandServiceMap.get(command).retrieveCommandResponse();
    }
}
