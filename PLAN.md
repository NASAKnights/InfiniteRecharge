# Programming Plan/Guide to the Infinite Recharge robot subsystems

## Drivetrain
<li>The drivetrain will be a six Falcon 500 (6 TalonFXs) drivetrain. As of now, we have 6 CIMs(2 SRX and 4 SPX) as the Falcons have not arrived.</li>
<li>Two single solenoids for gear shifting (Intermittent Turbo Mode).</li>
<li>A NavX IMU for measuring angles, autonomous control, and outtake alignment.</li>
<li>A PID controller for turning to a certain angle.</li>
<li>Both arcade and curvature drive methods for driving the drivetrain.</li>
<li>Get data from the Vision Client.</li>

## Intake
<li>One or two motors with a constant speed.</li>
<li>One solenoid to deploy the intake.</li>
<li>A distance sensor to count the number of Power Cells in the robots.</li>

## Outtake 
<li>Two NEO motors with a fixed speed. Need to make velocity adjustable in SmartDashboard for calibration. Display real velocity on the SmartDashboard.</li>
<li>Add flywheel speed control if possible.</li>
<li>One solenoid for the two-position hood.</li>
<li>A limit switch to detect the power cell is ready to shoot (or visual sensor).</li>

## Vision Client
<li>One single board computer for vision processing.</li>
<li>Should make the robot turn to a certain angle.</li>
<li>If there is enough time, add variable speed to flywheel.</li>

## Power Cell Articulator
<li>Three BAG motors which will be controlled by VictorSPXs.</li>

## Climber
<li>A double solenoid for deploying the mechanism.</li>
<li>A solenoid lock to lock the mechanism in place.</li>
<li>Two motors for the climbing winch.</li>
<li>A motor for moving the robot on the bar.</li>
<li>Use data from the IMU to center the weight on the bar.</li>