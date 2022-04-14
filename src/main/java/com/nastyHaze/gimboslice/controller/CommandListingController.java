package com.nastyHaze.gimboslice.controller;

import com.nastyHaze.gimboslice.service.web.CommandListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *  Controller class for Command Listing (retrieval) operations. Returns UI-friendly objects, rather than entities.
 */
@Controller
@RequestMapping("/commands")
public class CommandListingController {

    @Autowired
    private CommandListingService commandListingService;


    /**
     * Returns all Commands from the DB.
     * @param model
     * @return
     */
    @RequestMapping(value = "/listing", method = RequestMethod.GET)
    public String commandsPage(Model model) {
        model.addAttribute("commands", commandListingService.retrieveAllCommands()); // TODO: should only return active commands

        return "commands";
    }
}
