package com.nastyHaze.gimboslice.service.command;

import com.nastyHaze.gimboslice.constant.CommandName;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.entity.data.Player;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import com.nastyHaze.gimboslice.repository.PlayerRepository;
import com.nastyHaze.gimboslice.utility.ThymeleafContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.*;
import java.util.stream.Collectors;

import static com.nastyHaze.gimboslice.constant.CommonConstant.LIST_BREAK;
import static com.nastyHaze.gimboslice.constant.CommonConstant.expressiveNullResponses;
import static com.nastyHaze.gimboslice.constant.TemplateConstant.CLUELESS_TEMPLATE;
import static com.nastyHaze.gimboslice.constant.TemplateConstant.SLAYER_TASK_TEMPLATE;

@Service
public class SlayerTaskService implements CommandService {

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ThymeleafContextUtil thymeleafContextUtil;


    @Override
    public String retrieveCommandResponse() {
        return getCommand().getResponse();
    }

    @Override
    public Command getCommand() {
        return commandRepository.findByNameAndActiveTrue(CommandName.SLAYER_TASK);
    }

    @Override
    public String retrieveTemplateResponse() {
        Map<String, Object> slayerTaskMap = new HashMap<>();

        List<Player> players = playerRepository.findAll();
        List<String> formattedResponse = players.stream()
                .map(player -> String.format("%-16s | %-16s", player.getName(), Optional.ofNullable(player.getSlayerTask()).orElse(getNullTaskMessage())))
                .collect(Collectors.toList());

        Context slayerTaskContext = thymeleafContextUtil.getContext(slayerTaskMap);

        slayerTaskContext.setVariable("PLAYER_TASK_LIST", formattedResponse);

        return thymeleafContextUtil.getParsedTemplate(SLAYER_TASK_TEMPLATE, slayerTaskContext);
    }

    private String getNullTaskMessage() {
        Random myRandom = new Random();
        int messageIndex = myRandom.nextInt(expressiveNullResponses.length - 1);
        return expressiveNullResponses[messageIndex];
    }
}
