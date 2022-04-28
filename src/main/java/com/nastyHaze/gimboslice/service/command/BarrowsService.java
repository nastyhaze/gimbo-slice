package com.nastyHaze.gimboslice.service.command;

import com.nastyHaze.gimboslice.constant.BarrowsBrothers;
import com.nastyHaze.gimboslice.constant.CommandName;
import com.nastyHaze.gimboslice.constant.ItemTag;
import com.nastyHaze.gimboslice.constant.TemplateConstant;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.entity.data.Item;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import com.nastyHaze.gimboslice.repository.ItemRepository;
import com.nastyHaze.gimboslice.repository.PlayerItemDropRepository;
import com.nastyHaze.gimboslice.utility.ThymeleafContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BarrowsService implements CommandService {

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PlayerItemDropRepository dropRepository;

    @Autowired
    private ThymeleafContextUtil thymeleafContextUtil;


    @Override
    public Command getCommand() {
        return commandRepository.findByNameAndActiveTrue(CommandName.BARROWS);
    }

    @Override
    public String retrieveCommandResponse() {
        return getCommand().getResponse();
    }

    @Override
    public String retrieveTemplateResponse() {
        List<String> barrowsItemNames = itemRepository.findAllByTagsContaining(ItemTag.BARROWS.name())
                .stream()
                .map(Item::getName)
                .collect(Collectors.toList());

        Map<String, Integer> barrowsLog = new HashMap<>();

        barrowsItemNames.forEach(itemName -> barrowsLog.put(itemName, dropRepository.countByItemName(itemName)));

        List<List<String>> barrowsTableEntries = formatBarrowsLog(barrowsLog);

        Map<String, Object> contextMap = new HashMap<>();

        contextMap.put("BARROWS_TABLE", barrowsTableEntries);

        Context barrowsContext = thymeleafContextUtil.getContext(contextMap);

        return thymeleafContextUtil.getParsedTemplate(TemplateConstant.BARROWS_TEMPLATE, barrowsContext);
    }

    private List<List<String>> formatBarrowsLog(Map<String, Integer> barrowsLog) {
        return Arrays.stream(BarrowsBrothers.values())
                .map(brother -> barrowsLog.keySet()
                        .stream()
                        .filter(key -> key.contains(brother.name()))
                        .map(key -> String.format("%-25s | %-4d", key, barrowsLog.get(key)))
                        .map(str -> str = str.replaceAll(",", ""))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
