package com.nastyHaze.gimboslice.service.base;

import com.nastyHaze.gimboslice.constant.Operator;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import com.nastyHaze.gimboslice.service.web.CommandSaveService;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.nastyHaze.gimboslice.constant.CommonConstant.INVALID_ARGUMENTS_ERROR_MESSAGE;
import static com.nastyHaze.gimboslice.utility.CommonUtility.processError;
import static com.nastyHaze.gimboslice.utility.CommonUtility.processSuccess;

/**
 *  Abstract parent class for Bot Command Services.
 */
@Service
public abstract class CommandService {

    @Autowired
    private CommandSaveService commandSaveService;


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

    /**
     * Saves altered Commands & provides success/error message.
     * @param eventMessage
     * @param command
     * @return
     */
    public Mono<Void> save(Message eventMessage, Command command) {
        Mono<Void> stream;

        if(Objects.isNull(command)) {
            stream = processError(eventMessage, INVALID_ARGUMENTS_ERROR_MESSAGE);
        } else {
            commandSaveService.save(command);

            stream = processSuccess(eventMessage, this.getOperator());
        }

        return stream;
    }
}
