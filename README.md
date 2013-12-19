SmartGlove
==========

An Android application for controlling input/output for interfacing with an Arduino with bluetooth!

This application subscribes to the incoming SMS feed of Android phones by using the content://sms provider, sends an update to a connected Arduino using the [Amarino Toolkit](http://www.amarino-toolkit.net/) (not included in this project, download the jar and link it in the build path) using bluetooth.  The code also accepts messages triggered by the arduino, and is currently set up to send a response text when a button is pressed.

Our Smart Glove ([Glove as an Interface](https://blogs.discovery.wisc.edu/wearablecomputing-13/category/projects/glove-as-an-interface/)) project was constructed as the term project for DS 501-Wearable Computing in the Design Studies department at the University of Wisconsin-Madison for Fall 2013.  More information and a diary of our progress can be found on the [class website](https://blogs.discovery.wisc.edu/wearablecomputing-13/).


License
==========
This work is licensed under the MIT license.  Please see the LICENSE file for details.  If you use any part of this code, please attribute (required) and notify (optional, but we'd like to know!) us.
