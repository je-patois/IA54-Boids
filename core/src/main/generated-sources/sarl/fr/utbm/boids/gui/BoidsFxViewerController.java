package fr.utbm.boids.gui;

import fr.utbm.boids.events.ConfigureSimulation;
import fr.utbm.boids.gui.fx.FxViewerController;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class BoidsFxViewerController extends FxViewerController {
  private boolean launched = false;
  
  private boolean mapCreated = false;
  
  @FXML
  private ScrollBar boids_quantity_input;
  
  @Pure
  public int getBoidsQuantity() {
    double _value = this.boids_quantity_input.getValue();
    return ((int) _value);
  }
  
  @FXML
  protected Boolean actionSetup() {
    boolean _xblockexpression = false;
    {
      int _boidsQuantity = this.getBoidsQuantity();
      ConfigureSimulation event = new ConfigureSimulation(_boidsQuantity);
      boolean _xifexpression = false;
      if ((!this.launched)) {
        boolean _xblockexpression_1 = false;
        {
          final Procedure0 _function = () -> {
            this.emitToAgents(event);
          };
          this.startAgentApplication(_function);
          this.launched = true;
          _xblockexpression_1 = this.mapCreated = false;
        }
        _xifexpression = _xblockexpression_1;
      } else {
        this.emitToAgents(event);
      }
      _xblockexpression = _xifexpression;
    }
    return Boolean.valueOf(_xblockexpression);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BoidsFxViewerController other = (BoidsFxViewerController) obj;
    if (other.launched != this.launched)
      return false;
    if (other.mapCreated != this.mapCreated)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + (this.launched ? 1231 : 1237);
    result = prime * result + (this.mapCreated ? 1231 : 1237);
    return result;
  }
  
  @SyntheticMember
  public BoidsFxViewerController() {
    super();
  }
}
