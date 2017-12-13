package fr.utbm.boids.gui.fx;

import fr.utbm.boids.gui.fx.AppStart;
import fr.utbm.boids.gui.fx.EndSimulation;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventListener;
import io.sarl.util.OpenEventSpace;
import java.util.UUID;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public abstract class FxViewerController implements EventListener {
  private final UUID id = UUID.randomUUID();
  
  /**
   * Event space for interacting with the agents.
   */
  private OpenEventSpace space;
  
  /**
   * Emit a kill signal when the app is exited.
   */
  public void safeExit() {
    EndSimulation _endSimulation = new EndSimulation();
    this.emitToAgents(_endSimulation);
  }
  
  /**
   * Method invoked by the SARL agent to register the object on a space.
   */
  public Address setUISpace(final OpenEventSpace space) {
    Address _xblockexpression = null;
    {
      OpenEventSpace _space = this.space;
      if (_space!=null) {
        _space.unregister(this);
      }
      this.space = space;
      OpenEventSpace _space_1 = this.space;
      Address _register = null;
      if (_space_1!=null) {
        _register=_space_1.register(this);
      }
      _xblockexpression = _register;
    }
    return _xblockexpression;
  }
  
  /**
   * Replies the space for interaction between SARL agents and UI.
   */
  @Pure
  public OpenEventSpace getUISpace() {
    return this.space;
  }
  
  /**
   * Emit an event to the agents.
   */
  public void emitToAgents(final Event event) {
    if ((this.space != null)) {
      this.space.emit(this.getID(), event, null);
    }
  }
  
  public void startAgentApplication(final Procedure0 feedback) {
    AppStart _appStart = new AppStart(this, feedback);
    this.emitToAgents(_appStart);
  }
  
  /**
   * Catch exit event.
   */
  @FXML
  public void exitApplication(final ActionEvent event) {
    this.safeExit();
    Platform.exit();
  }
  
  /**
   * Get ID of the object on the space.
   */
  @Pure
  public UUID getID() {
    return this.id;
  }
  
  /**
   * Needed for receiving events from the agents.
   */
  public void receiveEvent(final Event event) {
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe return type is incompatible with equals(Object). Current method has the return type: void. The super method has the return type: boolean."
      + "\nThe return type is incompatible with equals(Object). Current method has the return type: void. The super method has the return type: boolean.");
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    throw new Error("Unresolved compilation problems:"
      + "\nThe return type is incompatible with equals(Object). Current method has the return type: void. The super method has the return type: boolean.");
  }
  
  @SyntheticMember
  public FxViewerController() {
    super();
  }
}
