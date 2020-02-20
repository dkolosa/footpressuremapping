const int ANAPINS[] = {0,1,2};
int incoming;
int npins = 3;
int value;

void setup()
{
  for (int pin = 0; pin < npins; pin++){
    pinMode(ANAPINS[pin], OUTPUT);
  }
  //Poll the prot
  Serial.begin(9600); //sertial comm at 9600bps
}

void loop()
{
  char val;

  iif( Serial.available() > 0)       // if data is available to read
  {
    for (int analogpin = 0; analogpin < npins; analogpin++)
    {
      analogRead(analogpin);
      delay(100);
      value = analogRead(analogpin);
      Serial.print(value);
    }
    Serial.print("\n");
    delay(100);
  }
}
