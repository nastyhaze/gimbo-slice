package com.nastyHaze.gimboslice.service.command;

import com.nastyHaze.gimboslice.constant.CommandName;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import com.nastyHaze.gimboslice.utility.ThymeleafContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

import static com.nastyHaze.gimboslice.constant.CommonConstant.LIST_BREAK;
import static com.nastyHaze.gimboslice.constant.TemplateConstant.CLUELESS_TEMPLATE;

@Service
public class CluelessService implements CommandService {

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
        return commandRepository.findByNameAndActiveTrue(CommandName.CLUELESS);
    }

    @Override
    public String retrieveTemplateResponse() {
        Command cluelessCommand = getCommand();
        Map<String, Object> cluelessMap = new HashMap<>();

        String rawResponse = cluelessCommand.getResponse();

        String[] formattedResponse = rawResponse.split(LIST_BREAK);

        Context cluelessContext = thymeleafContextUtil.getContext(cluelessMap);

        cluelessContext.setVariable("CLUELESS_LIST", formattedResponse);

        return thymeleafContextUtil.getParsedTemplate(CLUELESS_TEMPLATE, cluelessContext);
    }
}
