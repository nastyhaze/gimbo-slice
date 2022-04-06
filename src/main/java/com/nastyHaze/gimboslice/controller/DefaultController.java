package com.nastyHaze.gimboslice.controller;

import com.nastyHaze.gimboslice.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")
public class DefaultController {

    @Autowired
    private CommandRepository commandRepository;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value="/about", method = RequestMethod.GET)
    public String about() { return "about";}

    // TEST SAMPLE CODE
    public class Command {
        int id;
        String shortcut;
        String response;

        Command(int id, String shortcut, String response) {
            this.id = id;
            this.shortcut = shortcut;
            this.response = response;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShortcut() {
            return shortcut;
        }

        public void setShortcut(String shortcut) {
            this.shortcut = shortcut;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }

    @RequestMapping(value = "commands", method = RequestMethod.GET)
    public String commandListing(Model model) {

        List<Command> commands = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Command temp = new Command(i, "shortcut " + i, "response " + i);
            commands.add(temp);
        }

        model.addAttribute("commands", commands);
        return "commands";
    }
}
