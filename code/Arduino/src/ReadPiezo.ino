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
  for (int pin = 0; pin < 2; analogpin++)
  {
    value = analogRead(anaPins[pin]);  //Read the value of the analog pin
    delay(100);
    value = analogRead(anaPins[pin]); //Read twice to establish value
    Serial.print("Pin Number: %d = %d", anaPins[pin], value);
    delay(100);
  }
  delay(100);
}
