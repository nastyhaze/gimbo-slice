package com.nastyHaze.gimboslice.service.command;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nastyHaze.gimboslice.constant.CommonConstant.TEAM_SIZE;

@Service
public class ChambersReqService implements CommandService{

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
        return commandRepository.findByNameAndActiveTrue(CommandName.CHAMBERS_REQ);
    }

    @Override
    public String retrieveCommandResponse() {
        return getCommand().getResponse();
    }

    @Override
    public String retrieveTemplateResponse() {
        List<Item> chambersReqList = itemRepository.findAllByTagsContaining(ItemTag.CHAMBERS_REQ.name());

        List<String> formattedChambersReqList = chambersReqList.stream()
                .map(item -> String.format("%-25s | %s / %s obtained", item.getName(), dropRepository.countByItemName(item.getName()), TEAM_SIZE))
                .collect(Collectors.toList());

        Map<String, Object> contextMap = new HashMap<>();

        contextMap.put("CHAMBERS_REQ_LIST", formattedChambersReqList);

        Context chambersReqContext = thymeleafContextUtil.getContext(contextMap);

        return thymeleafContextUtil.getParsedTemplate(TemplateConstant.CHAMBERS_REQ_TEMPLATE, chambersReqContext);
    }
}
