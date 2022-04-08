package com.nastyHaze.gimboslice.controller;

import com.nastyHaze.gimboslice.service.web.CommandListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/commands")
public class CommandListingController {

    @Autowired
    private CommandListingService commandListingService;


    @RequestMapping(value = "/listing", method = RequestMethod.GET)
    public String commandsPage(Model model) {
        model.addAttribute("commands", commandListingService.retrieveAllCommands());

        return "commands";
    }
}
