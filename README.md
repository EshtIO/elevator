# Elevator simulator
Configurable console elevator simulator<br>

## Build the project
From the command line:
* `./gradlew build`

Gradle build tool will create "build" directory with build files

## Run the project

From the command line:
* (Recommended) Execute build project and unarchive `{root}/build/distributions/elevator-{version}.zip`<br>
    Run script with args:  
    * `{unarchived}/bin/elevator.sh {args}` (for Unix)
    * `{unarchived}/bin/elevator.bat {args}` (for Windows)<br>
* (Not recommended)`./gradlew run -Dexec.args="${args}"` where args delimeter - space

### Usage application options (args):
  *  -d, --door - Time between opening and closing doors (ms)<br>
      Default: 1000<br>
  *  -f, --floors - Floors count (>=5 && <= 20)<br>
      Default: 20<br>
  *  -h, --height - Floor height (m)<br>
      Default: 2.8<br>
  *  -s, --speed - Lift speed (m/s)<br>
      Default: 2.0<br>

### Supported elevator console commands after run application:
* Command: `i {floor}`, `inside {floor}`<br>
  Description: Button press with floor inside elevator
* Command: `o {floor}`, `outside {floor}`<br>
  Description: Elevator call from floor
* Command: `n`, `next`, `c`, `continue`<br>
  Description: Continue program
* Command: `q`, `quit`, `exit`, `close`<br>
  Description: Exiting the application