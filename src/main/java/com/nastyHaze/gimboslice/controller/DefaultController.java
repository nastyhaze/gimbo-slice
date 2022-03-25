package com.nastyHaze.gimboslice.controller;

import com.nastyHaze.gimboslice.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/")
public class DefaultController {

    @Autowired
    private CommandRepository commandRepository;

    /*@RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }*/

    @RequestMapping(value = "/commands", method = RequestMethod.GET)
    public String commandListing(Model model) {
        model.addAttribute("commands", commandRepository.findAll());
        return "commands";
    }
}
