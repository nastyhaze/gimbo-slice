package com.nastyHaze.gimboslice.service.base;

import com.nastyHaze.gimboslice.constant.Operator;
import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.repository.CommandRepository;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.nastyHaze.gimboslice.constant.CommonConstant.INVALID_ARGUMENTS_ERROR_MESSAGE;
import static com.nastyHaze.gimboslice.utility.CommonUtility.processError;
import static com.nastyHaze.gimboslice.utility.CommonUtility.processSuccess;


@Service
public abstract class CommandService {

    @Autowired
    private CommandRepository commandRepository;


    public abstract Operator getOperator();

    public abstract Mono<Void> processCommand(Message eventMessage);

    public Mono<Void> save(Message eventMessage, Command command) {
        Mono<Void> stream;

        if(Objects.isNull(command)) {
            stream = processError(eventMessage, INVALID_ARGUMENTS_ERROR_MESSAGE);
        } else {
            commandRepository.save(command);

            stream = processSuccess(eventMessage, this.getOperator());
        }

        return stream;
    }
}
