package com.innovation.arduino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.Scanner;

@SpringBootApplication
public class ArduinoApplication {

	public static void main(String[] args) throws IOException {
//		SpringApplication.run(ArduinoApplication.class, args);
		//"/bin/bash","-c","echo \"Fxsxwz1456\" | sudo -S
//		String cmd[]={"/bin/bash","-c","echo \" \"| sudo -S /home/xxz/Documents/ardUART/run.sh /home/xxz/Documents/ardUART/test2.sh"};
//		String cmd[]={"/bin/bash","-c","echo $TERM","ls","echo $TERM"};
//		Process process=Runtime.getRuntime().exec(cmd);
		Process process=Runtime.getRuntime().exec("/bin/bash",null,new File("/bin"));
		Scanner scanner=new Scanner(new InputStreamReader(process.getInputStream()));
		Scanner scanner1=new Scanner(new InputStreamReader(process.getErrorStream()));
		PrintWriter writer=new PrintWriter(new OutputStreamWriter(process.getOutputStream()),true);
		writer.println("export TERM=vt102");
		writer.println("echo $TERM");
		writer.println("/home/xxz/Documents/ardUART/tmp.sh");
		writer.println("exit");
		while(scanner.hasNext()){
			System.out.println(scanner.next());
		}
		while (scanner1.hasNext()){
			System.err.println(scanner1.next());
		}




	}
}
