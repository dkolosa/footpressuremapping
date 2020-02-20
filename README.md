# Foot Pressure Mapping

The key to success in sports is often proper technique. Technique is often hard to master, and practicing with incorrect technique can potentially do more harm than good. An important technique that is often overlooked in football is not to be caught flat footed in open space. Once flat footed, it is too hard to change direction at a rate as fast as the imposing player. This can often lead to being put on the wrong end of a highlight reel. This is one of numerous examples where self-analyzing shoes could come into play. Piezoelectric sensors can be placed into shoes to read the pressure distribution being applied to different parts of the foot. The signals generated from the piezoelectric sensors will be conditioned and converted to a digital signal via microcontroller. The microcontroller will transmit the data wirelessly to either a computer or mobile device via a Bluetooth module. The information can be stored and analyzed or broadcast in real-time allowing the user to correct improper footwork.

## Usage
- Install the apk file onto an Android >= 5.0
- Load the Arduino with the included program in the Arduino directory.
- Wire the piezoelectrics and bluetooth module according to the specifications in the attached pdf.
- Pair the android app and the bluetooth module
- Apply pressure to the sensors to see the pressure applied on the app.

## Arduino
The source for the Arudino is in the Arduino directory. Arduino Studio was used for programming the microcontroller.

## Android app
The android app is located in the Footmapping directory.

The app has been tested on Android 5.0.

An apk is avaliable in releases.

## Remort
The included pdf details the project, setup, preliminary results, meterials used, and results.
