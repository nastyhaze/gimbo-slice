package com.nastyHaze.gimboslice.service.event;

import com.nastyHaze.gimboslice.constant.Operator;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class MessageCreateListener implements Listener<MessageCreateEvent> {



    private Map<Operator, CommandService> commandServiceMap;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    private Map<Operator, CommandService> setCommandServiceMap(List<CommandService> commandServiceList) {
        commandServiceMap = commandServiceList.stream().collect(Collectors.toMap(CommandService::getOperator, Function.identity()));
        return commandServiceMap;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        Message eventMessage = event.getMessage();
        CommandService commandService = getCommandServiceFromMessage(eventMessage);

        return Objects.nonNull(commandService)
                ? commandService.processCommand(eventMessage)
                : Mono.empty();
    }

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    private CommandService getCommandServiceFromMessage(Message eventMessage) {
        return commandServiceMap.get(getOperatorFromMessage(eventMessage));
    }

    private Operator getOperatorFromMessage(Message message) {
        return Arrays.stream(Operator.values())
                .filter(val -> val.opCode().equals(message.getContent().substring(0, 1)))
                .findFirst()
                .orElse(null);
    }
}
