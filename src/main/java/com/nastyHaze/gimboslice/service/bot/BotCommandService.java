package com.nastyHaze.gimboslice.service.bot;

import com.nastyHaze.gimboslice.constant.Operator;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.service.data.command.CommandSaveService;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.nastyHaze.gimboslice.constant.CommonConstant.INVALID_ARGUMENTS_ERROR_MESSAGE;
import static com.nastyHaze.gimboslice.utility.CommonUtil.processError;
import static com.nastyHaze.gimboslice.utility.CommonUtil.processSuccess;

/**
 *  Abstract parent class for Bot Command Services.
 */
@Service
public abstract class BotCommandService {

    /**
     * Returns the Operator of the parsed Command.
     * @return
     */
    public abstract Operator getOperator();

    /**
     * Logic for processing the Commands for which each service is responsible.
     * @param eventMessage
     * @return
     */
    public abstract Mono<Void> processCommand(Message eventMessage);
}
