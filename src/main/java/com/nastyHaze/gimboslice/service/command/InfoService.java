package com.nastyHaze.gimboslice.service.command;

import com.nastyHaze.gimboslice.constant.CommandName;
import com.nastyHaze.gimboslice.constant.TemplateConstant;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import com.nastyHaze.gimboslice.utility.ThymeleafContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nastyHaze.gimboslice.constant.CommonConstant.COMMAND_LINE_BREAK;


@Service
public class InfoService implements CommandService {

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private ThymeleafContextUtil thymeleafContextUtil;


    @Override
    public String retrieveCommandResponse() {
        return getCommand().getResponse();
    }

    @Override
    public Command getCommand() {
        return commandRepository.findByNameAndActiveTrue(CommandName.INFO);
    }

    @Override
    public String retrieveTemplateResponse() {
        Command infoCommand = getCommand();
        Map<String, Object> contextMap = new HashMap<>();

        String rawResponse = infoCommand.getResponse();

        String[] responseLines = rawResponse.split(COMMAND_LINE_BREAK);

        contextMap.put("INFO_BLURB", responseLines);

        Context infoContext = thymeleafContextUtil.getContext(contextMap);

        return thymeleafContextUtil.getParsedTemplate(TemplateConstant.INFO_TEMPLATE, infoContext);
    }
}
