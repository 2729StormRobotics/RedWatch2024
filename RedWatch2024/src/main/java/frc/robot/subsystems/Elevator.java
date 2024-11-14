package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator {

    private enum State {

        STOP, UP, RELEASE, DOWN
    }

    public final CANSparkMax m_IndexerMotor;
    private DoubleSolenoid stopper;
    private State state;
    private long start = 0;
    private Encoder place;
    private double speed;
    private DigitalInput bottom;
    private boolean safetyScale;

    private static double MAX = 47.0;

    public Elevator {
        bottom = new DigitalInput(Ports.Digital.LIFT_INDEX);
        elevator = new m_IndexerMotor(Ports.PWM.ELEVATOR);
        stopper = new DoubleSolenoid(Ports.PCM.STOPPER_1, Ports.PCM.STOPPER_2);
        place = new Encoder(Ports.Digital.ENCODER_LIFT_1, Ports.DIGITAL.ENCODER)

        place.setDistancePerPulse(2.93*Math.PI / 360) //Value can be changed
        place.reset();

        state = State.STOP;

    }

public void setSpeed (double s) {
    if (s==0) {
        state = State.STOP;
    }
    else if (s > 0) {
        state = State.UP;
    }
    else {
        if (state != State.DOWN && state != State.RELEASE) {
            state = State.RELEASE;
            start = System.curentTimeMillis();
        }

        speed = Math.min(Math.abs(s), .3);
        return;

    }

    speed = Math.min(s, 0.95);

}

public void tick() {
    switch (state) {
        case UP:
            if (place.getDistance() >= MAX) {
                state = State.STOP;
                speed = 0;
            }
            else if (place.getDistance() >= MAX - 12) {
                if (safetyScale) { // Value to ensure that the elevator does not overreach 
                    speed = Math.min(0.4, speed);
                }
                else {
                    speed *= 0.7; //This value can be changed
                }
            }
            break;
        case RELEASE: 
            stopper.set(Value.kReverse);
            if (System.currentTimeMillis() - start > Ports.DELAY) {
                state = state.DOWN;
            }
            break;
        case STOP: 
            elevator.set(0);
            stopper.set(Value.kForward); // set the constant value for kForward
            break;
        case DOWN:
            if (!bottom.get()) {
                elevator.set(speed);  
            break;          
            }
    }
}

    public double getSpeed() {
        return elevator.get();
    }

    public double getGoalSpeed() {
        return speed;
    }

    public boolean isStopped() {
        return stopper.get() == Value.kForward;
    }

    public double getHeight() {
        return place.getDistance();
    }

    public void Stop() {
        state = state.STOP;
    }

    public boolean isBottom() {
        return bottom.get();
    }

    public void debug() {
		SmartDashboard.putNumber("Elevator Place", getHeight());
		SmartDashboard.putBoolean("Elevator Bottom", isBottom());
	}

	public void setSafetyScale(boolean b) {
		safetyScale = b;
	}


    

}
