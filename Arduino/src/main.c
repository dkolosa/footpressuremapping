void setup()
{
  //Poll the prot
  Serial.begin(9600); //sertial comm at 9600bps
}

void loop()
{
  char val;

  iif( Serial.available() )       // if data is available to read
  {
    val = Serial.read();         // read it and store it
  }
  if( val == 'H' )               // if 'H' was received
  {
    digitalWrite(ledpin, HIGH);  // turn ON the LED
  } else {
    digitalWrite(ledpin, LOW);   // otherwise turn it OFF
  }
  delay(100);
}
