package com.innovation.arduino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

@SpringBootApplication
public class ArduinoApplication {

	public static void main(String[] args) throws IOException {
//		SpringApplication.run(ArduinoApplication.class, args);
		//"/bin/bash","-c","echo \"Fxsxwz1456\" | sudo -S
		String cmd[]={"/bin/bash","-c","echo \" \"| sudo -S ls"};
		Process process=Runtime.getRuntime().exec(cmd);
		Scanner scanner=new Scanner(new InputStreamReader(process.getInputStream()));
		Scanner scanner1=new Scanner(new InputStreamReader(process.getErrorStream()));
		while(scanner.hasNext()){
			System.out.println(scanner.next());
		}
		while (scanner1.hasNext()){
			System.err.println(scanner1.next());
		}




	}
}
