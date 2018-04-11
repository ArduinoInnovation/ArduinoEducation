package com.innovation.arduino.state;

import com.innovation.arduino.exception.FileIOException;
import com.innovation.arduino.exception.InvalidInputException;
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
        if (command.master != null) {
            if (command.master.size() > 99) {
                throw new InvalidInputException("主机状态超过上限(99个状态)");
            } else {
                uartCommand.insert(0, String.format("%.2d", command.master.size()));
                addCommands(uartCommand, command.master);
            }

        }
        if (command.slave != null) {
            if (command.slave.size() > 99) {
                throw new InvalidInputException("从机状态超过上限（99个状态）");
            } else {
                uartCommand.insert(2, String.format("%.2d", command.slave.size()));
                addCommands(uartCommand, command.slave);
            }
        }
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
        try {
            Process process = Runtime.getRuntime().exec("/bin/bash", ((String[]) System.getenv().values().toArray()), new File("/bin"));
            Scanner scanner = new Scanner(new InputStreamReader(process.getInputStream()));
            Scanner scanner1 = new Scanner(new InputStreamReader(process.getErrorStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(process.getOutputStream()), true);
            writer.println("export TERM=vt102");
            writer.println("echo $TERM");
            writer.println("/home/xxz/Documents/ardUART/tmp.sh"+" "+uartCommands);
            writer.println("exit");
            while(scanner.hasNext()){
                System.out.println(scanner.next());
            }
            while (scanner1.hasNext()){
                System.err.println(scanner1.next());
            }
        } catch (IOException e) {
            throw new FileIOException("系统指令执行错误！");
        }
    }
}
