/*
* This file will read multiple analog inputs from an Arduino
* The Inputs will be outputed into the serial monitor
*/

const int anaPins[] = {0, 1};
int value;

void setup()
{
}

void loop(){
  //Print the values of the sensors
  for (int analogpin = 0; analogpin < 2; analogpin++)
  {
    value = analogRead(analogpin);  //Read the value of the analog pin
    delay(100);
    value = analogRead(analogpin); //Read twice to establish value
    Serial.print("Pin Number: %d = %d", pins, value);
    delay(100);
  }
  delay(100);
}
