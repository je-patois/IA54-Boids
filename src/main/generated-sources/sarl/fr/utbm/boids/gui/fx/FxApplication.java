package fr.utbm.boids.gui.fx;

import fr.utbm.boids.gui.fx.FxViewerController;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.util.OpenEventSpace;
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
    OpenEventSpace candidate = FxApplication.consumeEventSpaceCandidate();
    this.loader = this.doApplicationStart(stage);
    if (((this.loader != null) && (this.loader.<Object>getController() instanceof FxViewerController))) {
      Object _controller = this.loader.<Object>getController();
      ((FxViewerController) _controller).setUISpace(candidate);
    }
    stage.show();
  }
  
  public abstract FXMLLoader doApplicationStart(final Stage stage);
  
  public void stop() {
    try {
      super.stop();
      if (((this.loader != null) && (this.loader.<Object>getController() instanceof FxViewerController))) {
        Object _controller = this.loader.<Object>getController();
        ((FxViewerController) _controller).safeExit();
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private static OpenEventSpace eventSpaceCandidate;
  
  static OpenEventSpace setEventSpaceCandidate(final OpenEventSpace event) {
    OpenEventSpace _xsynchronizedexpression = null;
    synchronized (FxApplication.class) {
      _xsynchronizedexpression = FxApplication.eventSpaceCandidate = event;
    }
    return _xsynchronizedexpression;
  }
  
  private static OpenEventSpace consumeEventSpaceCandidate() {
    synchronized (FxApplication.class) {
      OpenEventSpace candidate = FxApplication.eventSpaceCandidate;
      FxApplication.eventSpaceCandidate = null;
      return candidate;
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
