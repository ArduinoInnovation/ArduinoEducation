#include <QueueArray.h>

#define MAXSIZE 50
#define MAXNUMBER 5000
String carString[MAXSIZE];
String tempString = "";
int currentCount = 0;
int stateCount = MAXNUMBER;
int controlCount = MAXNUMBER;

String reservedString = "00000000000000000";

char startLabel = 'S';
int startCount = 0;
bool isStart = false;
bool printEN = true;

QueueArray <char> queue;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:

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
    //Serial.print(stateCount);
    //Serial.println(controlCount);
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

