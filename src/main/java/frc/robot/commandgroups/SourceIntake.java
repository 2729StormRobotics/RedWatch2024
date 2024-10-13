// package frc.robot.commandgroups;

// import edu.wpi.first.wpilibj2.command.InstantCommand;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.commands.Pivot.AutoPivot;
// import frc.robot.subsystems.indexer.Indexer;
// import frc.robot.subsystems.pivotArm.PivotArm;
// import frc.robot.commands.Indexer.SourceFeed;
// import frc.robot.commands.Shooter.SetPower;
// import frc.robot.commands.Shooter.StopShooter;
// import frc.robot.Constants.PivotConstants;
// import frc.robot.Constants.ShooterConstants;

// public class SourceIntake extends SequentialCommandGroup {
//   // Intialize subsystems
//   private final Indexer m_indexer;
//   private final Pivot m_pivot;

//   /** Creates a new SourceIntake. */
//   public SourceIntake() {
//     m_indexer = Indexer.getInstance();
//     m_pivot = Pivot.getInstance();
    
//     // Add your commands in the addCommands() call, e.g.
//     // addCommands(new FooCommand(), new BarCommand());
//     addCommands(
//       // Shooter turns to source angle
//       new AutoPivot(PivotConstants.kSourcePivotAngle, m_pivot, false),

//       // Turn on shooter motor, motor values are placeholders
//       new SetPower(-ShooterConstants.kBottomFlywheelID, -ShooterConstants.kTopFlywheelID),

//       new SourceFeed( /*,true */),
//       // new SourceFeed(m_indexer /*,false */),
//       // new SourceFeed(m_indexer /*,true */),

//       // Stop shooter and indexer once note is intaken
//       new StopShooter(),
//       new InstantCommand(() -> {m_indexer.stop();})
      
    
//     );

//   }
// }