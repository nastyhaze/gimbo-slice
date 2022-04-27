package com.nastyHaze.gimboslice.service.command;

import com.nastyHaze.gimboslice.constant.CommandName;
import com.nastyHaze.gimboslice.constant.TemplateConstant;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import com.nastyHaze.gimboslice.service.data.command.CommandListingService;
import com.nastyHaze.gimboslice.utility.ThymeleafContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

import static com.nastyHaze.gimboslice.constant.CommonConstant.COMMAND_LINE_BREAK;

@Service
public class GroupGoalService implements CommandService {

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private CommandListingService commandListingService;

    @Autowired
    private ThymeleafContextUtil thymeleafContextUtil;


    @Override
    public String retrieveCommandResponse() {
        return getCommand().getResponse();
    }

    @Override
    public Command getCommand() {
        return commandRepository.findByNameAndActiveTrue(CommandName.GROUP_GOAL);
    }

    @Override
    public String retrieveTemplateResponse() {
        Command groupGoalCommand = getCommand();
        Map<String, Object> contextMap = new HashMap<>();

        String rawResponse = groupGoalCommand.getResponse();

        String[] responseLines = rawResponse.split(COMMAND_LINE_BREAK);

        contextMap.put("GROUP_GOALS", responseLines);

        Context groupGoalContext = thymeleafContextUtil.getContext(contextMap);

        return thymeleafContextUtil.getParsedTemplate(TemplateConstant.GROUP_GOAL_TEMPLATE, groupGoalContext);
    }
}
