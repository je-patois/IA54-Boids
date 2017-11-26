package fr.utbm.boids.gui.fx;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public abstract class FxApplication extends Application {
  private FXMLLoader loader;
  
  public final void start(final Stage stage) {
    this.loader = this.doApplicationStart(stage);
    stage.show();
  }
  
  public abstract FXMLLoader doApplicationStart(final Stage stage);
  
  public void stop() {
    try {
      super.stop();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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
  
  @SyntheticMember
  public FxApplication() {
    super();
  }
}
