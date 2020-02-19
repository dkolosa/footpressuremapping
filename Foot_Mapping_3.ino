/* FSR simple testing sketch. 
 
Connect one end of FSR to power, the other end to Analog 0.
Then connect one end of a 10K resistor from Analog 0 to ground 
 
For more information see https://learn.adafruit.com/force-sensitive-resistor-fsr/using-an-fsr */

const int anaPins[3] = {0, 1, 2};
int value;
 
void setup(void) {
  Serial.begin(9600);   
  }
 
void loop(void) {
  for (int analogpin = 0; analogpin < 3; analogpin++)
  {
 value = analogRead(analogpin);  //Read the value of the analog pin
    delay(100);
    value = analogRead(analogpin); //Read twice to establish value
        Serial.print("Pin Number: ");
        Serial.println(analogpin);
        Serial.println(value);
      delay(500);
  }
  delay(500);
}

