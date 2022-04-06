package com.nastyHaze.gimboslice.utility;

import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.entity.model.CommandDTO;

public class EntityToDtoConversionUtil {

    public static CommandDTO convertCommand(Command command) {
        CommandDTO convertedCommand = new CommandDTO();

        convertedCommand.setName(command.getName());
        convertedCommand.setDescription(command.getDescription());
        convertedCommand.setCommand(command.getShortcut());

        return convertedCommand;
    }
}
