package fr.utbm.boids.gui.fx;

import fr.utbm.boids.gui.fx.FxViewerController;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class AppStart extends Event {
  public final FxViewerController fxController;
  
  public final Procedure0 startFeedback;
  
  public AppStart(final FxViewerController fxController, final Procedure0 feedback) {
    this.fxController = fxController;
    this.startFeedback = feedback;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    return result;
  }
  
  /**
   * Returns a String representation of the AppStart event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("fxController  = ").append(this.fxController);
    result.append("startFeedback  = ").append(this.startFeedback);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 4892590752L;
}
