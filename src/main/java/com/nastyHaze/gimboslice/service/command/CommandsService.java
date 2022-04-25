package com.nastyHaze.gimboslice.service.command;

import com.nastyHaze.gimboslice.constant.CommandName;
import com.nastyHaze.gimboslice.constant.TemplateConstant;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.entity.model.CommandDTO;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import com.nastyHaze.gimboslice.service.data.CommandListingService;
import com.nastyHaze.gimboslice.service.data.CommandSaveService;
import com.nastyHaze.gimboslice.utility.ThymeleafContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nastyHaze.gimboslice.utility.CommonUtil.convertCommandDtoListToStringList;


@Service
public class CommandsService implements CommandService {

    @Autowired
    private CommandListingService commandListingService;

    @Autowired
    private CommandSaveService commandSaveService;

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private ThymeleafContextUtil thymeleafContextUtil;


    @Override
    public String retrieveCommandResponse() {
        refresh();
        return getCommand().getResponse();
    }

    @Override
    public Command getCommand() {
        return commandRepository.findByNameAndActiveTrue(CommandName.COMMANDS);
    }

    @Override
    public String retrieveTemplateResponse() {
        List<CommandDTO> commandList = commandListingService.retrieveAllCommands();

        List<String> formattedCommandList = commandList.stream()
                .map(cmd -> String.format("%-12s | %-32s", cmd.getCommand(), cmd.getDescription()))
                .collect(Collectors.toList());

        Map<String, Object> contextMap = new HashMap<>();

        contextMap.put("COMMAND_LIST", formattedCommandList);

        Context infoContext = thymeleafContextUtil.getContext(contextMap);

        return thymeleafContextUtil.getParsedTemplate(TemplateConstant.COMMANDS_TEMPLATE, infoContext);
    }

    private void refresh() {
        Command refreshed = getCommand();
        List<String> commandsList = convertCommandDtoListToStringList(commandListingService.retrieveAllCommands());

        refreshed.setResponse(commandsList.toString());

        commandSaveService.save(refreshed);
    }
}
