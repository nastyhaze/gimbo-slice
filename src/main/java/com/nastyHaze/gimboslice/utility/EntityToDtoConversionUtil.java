package com.nastyHaze.gimboslice.utility;

import com.nastyHaze.gimboslice.entity.data.Command;
import com.nastyHaze.gimboslice.entity.model.CommandDTO;

/**
 * Utility class to convert to-and-from UI requests/responses and DB Entities.
 */
public class EntityToDtoConversionUtil {

    public static CommandDTO convertCommand(Command command) {
        CommandDTO convertedCommand = new CommandDTO();

        convertedCommand.setName(command.getName().desc());
        convertedCommand.setDescription(command.getDescription());
        convertedCommand.setCommand(command.getShortcut());

        return convertedCommand;
    }
}
