This is a port of shinyquagsire23's Infinite Campus API to Android.  It will (eventually) be a complete, working Android app that adheres to the Material Design guidelines.

See the original API readme below:

**Infinite Campus API**

This is a simple API for the grading system Infinite Campus. It is currently implemented in Java and was created using protocols reversed from Wireshark and the disassemblies of the Infinite Campus App. From there it was just parsing the ungodly XML files the site spit out and turning it into actual, organized data.

**Use Cases**

The project itself was inspired by the absolutely horrendous excuse of an app Infinite Campus has for Android (which fails to comply even to Holo's standards) and the horrible web interface as well. Current plans include a better Android app and perhaps a web interface which can output a similarly organized grade printout using the mobile API.

**Dependencies:**

* Java 1.7

**Features**

At the current moment only student functions have been implemented. It is able to provide a basic printout of an account's grades to stdout and grades.txt provided a username and password (and in the future, a district code). Everything else seems to be properly exposed, provided the district has those features available.
