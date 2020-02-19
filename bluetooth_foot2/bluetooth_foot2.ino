/* Turn an LED on/off based on a command send via BlueTooth
** Rui Santos: http://randomnerdtutorials.wordpress.com
** https://github.com/42Bots/ArduinoBluetooth/blob/master/BluetoothBlink
** http://42bots.com/tutorials/how-to-connect-arduino-uno-to-android-phone-via-bluetooth/

FSR simple testing sketch.
Connect one end of FSR to power, the other end to Analog 0.
Then connect one end of a 10K resistor from Analog 0 to ground
For more information see https://learn.adafruit.com/force-sensitive-resistor-fsr/using-an-fsr */

const int ANAPINS[] = {0};                                // pins for pressure sensors
int incoming;
float value;
//float kgs = 1;                                             // Default leaves weight in lbs
//String units = " lbs";                                     // Default units as lbs

void setup() {
    for (int pin = 0; pin < 1; pin++) {
        pinMode(ANAPINS[pin], OUTPUT);
    }
    Serial.begin(9600);                                      // Default connection rate for this BT module
}

void loop() {
    // if (Serial.available() > 0) {                          // see if there's incoming serial data:
    //   incoming = Serial.read();                            // read the oldest byte in the serial buffer
    //   if (incoming == 'k') {                               // Converting to KGs
    //       Serial.println("Now Showing Weight in Kilograms.");
    //       units.replace(" lbs", " kg");                        // Correcting units
    //       kgs = .45;
    //   }                                          // .4536 kgs equal 1 lb
    //   else if (incoming == 'l') {                          // Going back to pounds
    //       Serial.println("Now Showing Weight in Pounds.");
    //       kgs = 1;
    //       units.replace(" kg", " lbs");}
    // }                                           // Correcting units
    for (int analogpin = 0; analogpin < 1; analogpin++)
    {
        analogRead(analogpin);                            //Read the value of the analog pin
        delay(100);
        value = analogRead(analogpin);                         //Read twice to establish value
//        Serial.print("Pin Number: ");
//        Serial.println(analogpin);
        Serial.print(value);
//        Serial.print(value*kgs);
//        Serial.println(units);
        delay(300);
    }
    Serial.print("\n");
    delay(300);
}

