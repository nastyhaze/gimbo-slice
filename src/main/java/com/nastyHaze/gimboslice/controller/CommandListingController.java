package com.nastyHaze.gimboslice.controller;

import com.nastyHaze.gimboslice.entity.model.CommandDTO;
import com.nastyHaze.gimboslice.service.data.command.CommandListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *  Controller class for Command Listing (retrieval) operations. Returns UI-friendly objects, rather than entities.
 */
@RestController
@RequestMapping("/commands")
public class CommandListingController {

    @Autowired
    private CommandListingService commandListingService;


    @RequestMapping(value = "/listing", method = RequestMethod.GET)
    @ResponseBody
    public List<CommandDTO> getAllCommands() {
        return commandListingService.retrieveAllCommands();
    }

    @RequestMapping(value = "/{commandShortcut}", method = RequestMethod.GET)
    @ResponseBody
    public CommandDTO getCommandByCommandShortcut(@PathVariable(value = "commandShortcut") String commandShortcut) {
        return commandListingService.retrieveCommandByCommandShortcut(commandShortcut);
    }

    @RequestMapping(value = "/{commandShortcut}/response", method = RequestMethod.GET)
    @ResponseBody
    public String getResponseByCommandShortcut(@PathVariable(value = "commandShortcut") String commandShortcut) {
        return commandListingService.retrieveCommandResponseByCommandShortcut(commandShortcut);
    }
}
