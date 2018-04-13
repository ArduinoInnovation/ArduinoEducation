#include <SoftwareSerial.h>

const int swSerialRx = 10;
const int swSerialTx = 11;
SoftwareSerial swSerial(swSerialRx, swSerialTx);

int motorR1 = 3;    //马达部分
int motorR2 = 5;
int motorL1 = 6;
int motorL2 = 9;


enum {Hello = 1, Forward, Back, Left, Right, Stop, Exit};
int command = 0;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  swSerial.begin(9600);
  swSerial.listen();
  voice();
}

void loop() {
  // put your main code here, to run repeatedly:

}

void voice()
{
  bool isExit = false;
  while(isExit != true)
  {
    swSerialEvent();
    if(command == Forward)
    {
      line(255, 3000);
      still();
    }
    else if(command == Back)
    {
      back(255, 3000);
      still();
    }
    else if(command == Left)
    {
      turnL(255, 500);
      still();
    }
    else if(command == Right)
    {
      turnR(255, 500);
      still();
    }
    else if(command == Stop)
    {
      still();
    }
    else if(command == Exit)
    {
      isExit = true;
    }
    command = 0;
  }
}

void swSerialEvent()
{
  if(swSerial.available() > 0)
  {
    command = swSerial.read();
  }
}

void still(){
  digitalWrite(motorL1,LOW); digitalWrite(motorL2,LOW); 
  digitalWrite(motorR1,LOW); digitalWrite(motorR2,LOW);
}

void line(int speed,int time){
  digitalWrite(motorL1,speed); digitalWrite(motorL2,LOW); 
  digitalWrite(motorR1,speed); digitalWrite(motorR2,LOW); 
  delay(time);
}

void back(int speed,int time){
  digitalWrite(motorL1,LOW); digitalWrite(motorL2,speed); 
  digitalWrite(motorR1,LOW); digitalWrite(motorR2,speed); 
  delay(time);
}

void turnR(int speed,int time){//ms
  digitalWrite(motorL1,speed); digitalWrite(motorL2,LOW); 
  digitalWrite(motorR1,LOW); digitalWrite(motorR2,speed); 
  delay(time);
}

void turnL(int speed,int time){//ms
  digitalWrite(motorL1,LOW); digitalWrite(motorL2,speed); 
  digitalWrite(motorR1,speed); digitalWrite(motorR2,LOW); 
  delay(time);
}

