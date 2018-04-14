package com.innovation.arduino;

import com.innovation.arduino.exception.FileIOException;
import com.innovation.arduino.exception.InvalidInputException;
import com.innovation.arduino.state.CommandVO;
import com.innovation.arduino.state.State;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * Description:
 * 业务逻辑对象
 *
 * @author xxz
 * Created on 04/11/2018
 */
@Component
public class CommandService {
    public boolean fire(CommandVO command) {
        if (command == null) {
            return false;
        }
        StringBuilder uartCommand = new StringBuilder();
        if (command.getMaster() != null) {
            if (command.getMaster().size() > 99) {
                throw new InvalidInputException("主机状态超过上限(99个状态)");
            } else {
                uartCommand.insert(0, String.format("%02d", command.getMaster().size()));
                addCommands(uartCommand, command.getMaster());
            }

        }
        if (command.getSlave() != null) {
            if (command.getSlave().size() > 99) {
                throw new InvalidInputException("从机状态超过上限（99个状态）");
            } else {
                uartCommand.insert(2, String.format("%02d", command.getSlave().size()));
                addCommands(uartCommand, command.getSlave());
            }
        }
        uartCommand.insert(0,"SSSS");
        sendCommands(new String(uartCommand));
        return true;

    }


    private void addCommands(StringBuilder builder, List<State> states) {
        if (states != null && builder != null) {
            for (State s : states) {
                builder.append(s.encoding());
            }
        }
    }

    private void sendCommands(String uartCommands) {
        System.err.println(uartCommands);

        try {
            Process process = Runtime.getRuntime().exec("/bin/bash", null, new File("/bin"));
            Scanner scanner = new Scanner(new InputStreamReader(process.getInputStream()));
            Scanner scanner1 = new Scanner(new InputStreamReader(process.getErrorStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(process.getOutputStream()), true);
            writer.println("export TERM=vt102");
            writer.println("echo $TERM");
            writer.println("/home/xxz/Documents/ardUART/tmp.sh"+" "+uartCommands+" "+uartCommands.length());
            writer.println("exit");
            while(scanner.hasNext()){
                System.out.println(scanner.nextLine());
            }
            while (scanner1.hasNext()){
                System.err.println(scanner1.nextLine());
            }
        } catch (IOException e) {
            throw new FileIOException("系统指令执行错误！");
        }

    }
}
