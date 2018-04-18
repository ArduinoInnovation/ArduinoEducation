/***------------Current Form Of 0-1 String---------***/
/*
 0  |  1 |  2-9 |  9-13  |  14  | 15-31
红外 |转发|  马达 |  超声波  | 语音 | Reserved
*/

/***-----------About Serial Decoder----------------***/
#include <QueueArray.h>

#define MAXSIZE 50
#define MAXNUMBER 5000
String carString[MAXSIZE];
String tempString = "";
int currentCount = 0;
int stateCount = MAXNUMBER;
int controlCount = MAXNUMBER;

String reservedString = "00000000000000000";//17 0s

char startLabel = 'S';
int startCount = 0;
bool isStart = false;
bool printEN = true;

QueueArray <char> queue;

/***------------About Car Moving--------------------***/

/*#include<IRremote.h>      //红外部分
int RECV_PIN = 11;
IRrecv ir_recv(RECV_PIN);
decode_results results;
long IR_TARGET = 16724175;//0xFF30CF;
long STOP_TARGET = 16738455;//FF6897;*/

const int trig[3] = {8,4                    ,12}; //超声波部分
const int echo[3] = {7,2,13};
float dis[3];//0 1 2:left center right
//int radius = 20;

int motorR1 = 3;  //马达部分
int motorR2 = 5;  
int motorL1 = 6;
int motorL2 = 9;

/***-------------- Voide Module---------------------- ***/
#include <SoftwareSerial.h>

const int swSerialRx = 10;
const int swSerialTx = 11;
SoftwareSerial swSerial(swSerialRx, swSerialTx);

enum {Hello = 1, Forward, Back, Left, Right, Stop, Exit};
int command = 0;


void setup() {
  //set ports about car moving
  for(int i=0;i<3;i++){
    pinMode(echo[i],INPUT);
    pinMode(trig[i],OUTPUT);
  }
  pinMode(motorL1,OUTPUT);
  pinMode(motorL2,OUTPUT);
  pinMode(motorR1,OUTPUT);
  pinMode(motorR2,OUTPUT);

 
  Serial.begin(9600);
  swSerial.begin(9600);
  swSerial.listen();
  //ir_recv.enableIRIn();
  
}
void loop() {
  if(currentCount >= stateCount + controlCount ){
    carMoving();
   }

}
void carMoving(){
  int ir=0, cmd=1, motor=2, ultra=9,voiceCtrl=14;
  
  for(int i = 0; i< stateCount + controlCount; i=i+1){
     if(carString[i][ir]=='1'){
      //可以接收红外线
     }
     else{
      //不可以接收红外线
     }
     
     if(carString[i][cmd]=='1'){
        //可以转发控制字
     }
     else{
        //不可以转发控制字
     }
     
     if(carString[i][motor]=='1'){      //Motor
        int direct   =  (carString[i][motor+1]-'0')*2+(carString[i][motor+2]-'0');
        int rate     = ((carString[i][motor+3]-'0')*2+(carString[i][motor+4]-'0')) * 50;  
        int duration = ((carString[i][motor+5]-'0')*2+(carString[i][motor+6]-'0')) * 1000;//step: 1 second
        Serial.print("direct:");Serial.println(direct);
        Serial.print("rate:");Serial.println(rate);
        Serial.print("dur:");  Serial.println(duration);
        switch(direct){
          case 0: line(rate,duration);  break; 
          case 1: turnL(rate,duration); break;
          case 2: turnR(rate,duration); break;
          case 3: turnRandom(rate,duration);break;
          default: break;
        }
        continue;
     }
     else{
        still();
     }

     if(carString[i][ultra]=='1'){       //ultrasonic
        int rad      =   ((carString[i][ultra+1]-'0')*2+(carString[i][ultra+2]-'0'))* 10;   //step: 10cm
        int duration =  ((carString[i][ultra+3]-'0')*2+(carString[i][ultra+4]-'0')) * 1000; //step: 1 second
        int slot = 0;
        while(slot < duration){ //Run for a period time of 'duration' with ultrasonic
          range();
          action(rad, HIGH, duration/4);
          slot = slot + duration/4;
        }
        continue;
     }
     else{
       still();
     }

     if(carString[i][voiceCtrl]=='1'){//voice
       voice();
       continue;
     }
  }
}

void action(int radius, int speed,int time){// 无论怎样都延时50ms
  if(dis[0]>radius && dis[1]>radius && dis[2]>radius){
    line(15,time);
    //turnR(15,10); //随机转换方向，防止卡住
  }
  else{
    if(dis[1]<radius ||(dis[0]<radius && dis[2]<radius)){
      back(15,time*0.9);
      turnL(15,time*0.1);
    }
    else if(dis[0]<radius){
      turnR(15,time);
    }
    else if(dis[2]<radius){
      turnL(15,time);
    }
  }
}

void range(){
  long IntervalTime = 0;
  for(int i=0;i<3;i++){
    digitalWrite(trig[i],1);//置高电平
    delayMicroseconds(15);
    digitalWrite(trig[i],0);
    IntervalTime = pulseIn(echo[i], HIGH);
    dis[i] = IntervalTime*0.017;//单位cm
    Serial.print(i);Serial.print(':');Serial.print(dis[i]);Serial.print("cm; ");
    IntervalTime=0;
  }
  Serial.println("");
}



void serialEvent()
{
 
  if(Serial.available() > 0)
  {
    char ch = Serial.read();
    if(ch == startLabel)
    {
      startCount++;
    }
    if(startCount >= 4)
    {
      isStart = true;
      if(validCharJudge(ch))
      {
        queue.push(ch);
      }
    }
    if(isStart == true && queue.count() >= 4 && currentCount < stateCount + controlCount)
    {
      decoder();
    }

    if(currentCount >= stateCount + controlCount && printEN)
    {
      printEN = false;
      for(int i = 0; i < stateCount + controlCount; i++)
      {
        Serial.println(carString[i]);
      }
      delay(5000);  //allowing to unplug the serial
    }
  }
}

bool validCharJudge(char ch)
{
  return ((ch <= '9' && ch >= '0') || (ch <= 'E' && ch >= 'A') || (ch <= 'e' && ch >= 'a'));
}

void decoder()
{
  char opcode = queue.pop();
  if(opcode <= '9' && opcode >= '0')
  {
    char ch0 = opcode;
    char ch1 = queue.pop();
    char ch2 = queue.pop();
    char ch3 = queue.pop();
    stateCount = (ch0 - '0') * 10 + (ch1 - '0');
    controlCount = (ch2 - '0') * 10 + (ch3 - '0');
//    Serial.println(stateCount);
//    Serial.println(controlCount);
    return;
  }
  switch(opcode)
  {
    case 'A':
    {
      tempString += "1";
      for(int i = 0; i < 3; i++)
      {
        char ch = queue.pop();
      }
      break;
    }
    case 'a':
    {
      tempString += "0";
      for(int i = 0; i < 3; i++)
      {
        char ch = queue.pop();
      }
      break;
    }
    case 'B':
    {
      tempString += "1";
      for(int i = 0; i < 3; i++)
      {
        char ch = queue.pop();
      }
      break;
    }
    case 'b':
    {
      tempString += "0";
      for(int i = 0; i < 3; i++)
      {
        char ch = queue.pop();
      }
      break;
    }
    case 'C':
    {
      tempString += "1";
      for(int i = 0; i < 3; i++)
      {
        char ch = queue.pop();
        switch(ch)
        {
          case '0':
          {
            tempString += "00";
            break;
          }
          case '1':
          {
            tempString += "01";
            break;
          }
          case '2':
          {
            tempString += "10";
            break;
          }
          case '3':
          {
            tempString += "11";
            break;
          }
        }
      }
      break;
    }
    case 'c':
    {
      tempString += "0000000";
      for(int i = 0; i < 3; i++)
      {
        char ch = queue.pop();
      }
      break;
    }
    case 'D':
    {
      tempString += "1";
      for(int i = 0; i < 2; i++)
      {
        char ch = queue.pop();
        switch(ch)
        {
          case '0':
          {
            tempString += "00";
            break;
          }
          case '1':
          {
            tempString += "01";
            break;
          }
          case '2':
          {
            tempString += "10";
            break;
          }
          case '3':
          {
            tempString += "11";
            break;
          }
        }
      }
      queue.pop();
      break;
    }
    case 'd':
    {
      tempString += "00000";
      for(int i = 0; i < 3; i++)
      {
        char ch = queue.pop();
      }
      break;
    }
    case 'E':
    {
      tempString += ("1" + reservedString);//保留位
      for(int i = 0; i < 3; i++)
      {
        char ch = queue.pop();
      }
      carString[currentCount] = tempString;
      currentCount++;
      tempString = "";
      break;
    }
    case 'e':
    {
      tempString += ("0" + reservedString);//保留位
      for(int i = 0; i < 3; i++)
      {
        char ch = queue.pop();
      }
      carString[currentCount] = tempString;
      currentCount++;
      tempString = "";
      break;
    }
  }
}

void voice()
{
  bool isExit = false;
  while(isExit != true)
  {
    swSerialEvent();
    if(command == Forward)
    {
      line(HIGH, 3000);
      still();
    }
    else if(command == Back)
    {
      back(HIGH, 3000);
      still();
    }
    else if(command == Left)
    {
      turnL(HIGH, 500);
      still();
    }
    else if(command == Right)
    {
      turnR(HIGH, 500);
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
void line(int v,int duration){
  digitalWrite(motorL1,v); digitalWrite(motorL2,LOW); 
  digitalWrite(motorR1,v); digitalWrite(motorR2,LOW); 
  delay(duration);
}

void back(int v,int duration){
  digitalWrite(motorL1,LOW); digitalWrite(motorL2,v); 
  digitalWrite(motorR1,LOW); digitalWrite(motorR2,v); 
  delay(duration);
}

void turnR(int v,int duration){//ms
  digitalWrite(motorL1,v); digitalWrite(motorL2,LOW); 
  digitalWrite(motorR1,LOW); digitalWrite(motorR2,v); 
  delay(duration);
}

void turnL(int v,int duration){//ms
  digitalWrite(motorL1,LOW); digitalWrite(motorL2,v); 
  digitalWrite(motorR1,v); digitalWrite(motorR2,LOW); 
  delay(duration);
}

void turnRandom(int v,int duration){
  int pastTime=0;
  while(pastTime < duration){
    int slice  = random(duration*0.2, duration*0.4);
    if(slice+pastTime > duration){
      slice = slice + pastTime - duration;
    }
    int direct = random(0,4);
    switch(direct){
      case 0: line(v,slice); break;
      case 1: turnL(v,slice);break;
      case 2: turnR(v,slice);break;
      case 3: back(v,slice);break;
      default:break;
    }
    pastTime = pastTime + slice;
  }
}

