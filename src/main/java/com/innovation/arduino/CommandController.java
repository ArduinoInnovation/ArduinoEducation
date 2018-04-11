package com.innovation.arduino;

import com.innovation.arduino.state.CommandVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author xxz
 * Created on 04/11/2018
 */
@RestController
@RequestMapping("/command")
public class CommandController {

    private CommandService commandService;

    @Autowired
    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @RequestMapping(value = "/task",method = RequestMethod.POST)
    public boolean fire(@RequestBody CommandVO command){
        return commandService.fire(command);
    }
}
