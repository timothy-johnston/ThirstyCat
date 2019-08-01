Hello! This guide will teach you how to hook a force sensing resistor up to a Raspberry Pi.

Before getting in to the details, I want to give a major shoutout to Andrew Caird. His guide on how to [use a force sensitive resistor with a Raspberry Pi][fsr-guide] was tremendously helpful to me.

## Materials
* Raspberry Pi B+
* Force-sensitive resistor
* Breadboard
* Pi Cobbler
* MCP3008 analog to digital converter
* Ribbon cable
* 10k Ohm resistor
* Male/male jumper wires 

## Lets Build It
The process is pretty straight-forward. I'll try to make it more straight-forward with lots of pictures.

Plug the analog to digital converter and Pi Cobbler into the breadboard.
<p align="center">
  <img src="/webApp/thirstyCat/src/main/resources/static/media/build TC 1.JPG" height="300">
</p>

Refer to the wiring diagrams provided by Adafruit ([more info here)][adafruit-page]. Don't worry that the cobbler and converter are in different locations than the picture of my own setup - what's important here is to connect the MCP3008's pins to the correct Cobbler pins according to the following:
* MCP3008 Pin -> Cobbler Pin
* MCP3008 VDD -> 3.3V 
* MCP3008 VREF -> 3.3V 
* MCP3008 AGND -> GND 
* MCP3008 CLK -> #18 
* MCP3008 DOUT -> #23 
* MCP3008 DIN -> #24 
* MCP3008 CS -> #25 
* MCP3008 DGND -> GND 

<p align="center">
  <img src="/webApp/thirstyCat/src/main/resources/static/media/build TC 8.gif" height="300">
</p>
<p align="center">
  Pin Diagram
</p>

<br>

<p align="center">
  <img src="/webApp/thirstyCat/src/main/resources/static/media/build TC 5.png" height="300">
</p>
<p align="center">
  Wiring Diagram: Zoomed Out
</p>

<br>

<p align="center">
  <img src="/webApp/thirstyCat/src/main/resources/static/media/build TC 4.png" height="300">
</p>
<p align="center">
  Wiring Diagram: Zoomed In
</p>

<br>

<p align="center">
  <img src="/webApp/thirstyCat/src/main/resources/static/media/build TC 2.JPG" height="300">
</p>
<p align="center">
  Connected pins
</p>

<br>

Here is the only tricky part. Connect the FSR and the 10k Ohm resistor to the breadboard. See the picture below:  The FSR goes in a free location on the breadboard. Use a jumper wire to connect one of the FSR leads to Channel 0 on the MCP3008. Then use the 10k Ohm resistor to connect that same location to the ground on the opposite side of the breadboard. Finally, use a jumper wire to connect the 2nd FSR lead to the power rail on the FSR's side of the breadboard.

<p align="center">
  <img src="/webApp/thirstyCat/src/main/resources/static/media/build TC 7.png" height="300">
</p>

Smooth sailing from here on out. Connect the breadboard to the Pi using the ribbon cable. 

<p align="center">
  <img src="/webApp/thirstyCat/src/main/resources/static/media/build TC 3.JPG" height="300">
</p>

Finally, connect the Pi Cam to the Pi. [Here is a nice guide][pi-guide] on how to set up and use your PiCam.


## Lets Use It
At this point, if you've been building your own, you've got a working force sensor. What you do with it now is up to you! 
Want to see what I did? I build a force-sensitive platform, placed it in front of my cat's water bowl, and wrote a Python script to capture a cat pic each time she steps on the platform to take a drink. Head to the [project on GitHub][github-page] to check it out. 

<p align="center">
  Have fun, and thanks for reading!
</p>
<p align="center">
  <img src="/webApp/thirstyCat/src/main/resources/static/media/build TC 0.JPG" height="300">
</p>

[thirsty-cat]: https://twitter.com/TheThirstyCat
[fsr-guide]: https://acaird.github.io/computers/2015/01/07/raspberry-pi-fsr
[adafruit-page]: https://learn.adafruit.com/reading-a-analog-in-and-controlling-audio-volume-with-the-raspberry-pi/connecting-the-cobbler-to-a-mcp3008
[github-page]: https://github.com/timothy-johnston/ThirstyCat
[pi-guide]: https://thepihut.com/blogs/raspberry-pi-tutorials/16021420-how-to-install-use-the-raspberry-pi-camera
