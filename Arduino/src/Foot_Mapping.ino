/* FSR simple testing sketch.

Connect one end of FSR to power, the other end to Analog 0.
Then connect one end of a 10K resistor from Analog 0 to ground

For more information see https://learn.adafruit.com/force-sensitive-resistor-fsr/using-an-fsr */

int fsrPin = 0;     // the FSR and 10K pulldown are connected to a0
int fsrPin2 = 1;
int fsrPin3 = 2;

int fsrReading;     // the analog reading from the FSR resistor divider

int fsrReading2;


void setup(void) {
  // We'll send debugging information via the Serial monitor
  Serial.begin(9600);   //Read at 9.6kbps
}

void loop(void) {
  fsrReading = analogRead(fsrPin);

  fsrReading2 = analogRead(fsrPin2);


  Serial.print("Analog reading = ");

//Show which sensor is printed
  Serial.print("Sensor Number: %d", fsrPin)

  Serial.print(fsrReading);     // the raw analog reading



  // We'll have a few threshholds, qualitatively determined
  if (fsrReading < 10) {
    Serial.println(" - No pressure");
  } else if (fsrReading < 200) {
    Serial.println(" - Light touch");
  } else if (fsrReading < 500) {
    Serial.println(" - Light squeeze");
  } else if (fsrReading < 800) {
    Serial.println(" - Medium squeeze");
  } else {
    Serial.println(" - Big squeeze");
  }
  delay(1000);
}
