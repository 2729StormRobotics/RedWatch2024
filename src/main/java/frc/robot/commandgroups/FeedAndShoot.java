package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.indexer.Indexer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class FeedAndShoot extends SequentialCommandGroup {
  private final Indexer m_indexer;
  private final Shooter m_shooter;

  /** Creates a new Shoot. */
  public FeedAndShoot(double leftSpeed, double rightSpeed, double indexerSpeed, Indexer indexer) {
    m_indexer = indexer;

    addCommands(
      // new WaitCommand(2),
      new InstantCommand(() -> {m_indexer.runIndexer(indexerSpeed);}),
      new WaitCommand(1.5),
      new InstantCommand(()  -> {m_shooter.manualCommand(0);})   //missing??

    );
  }
}