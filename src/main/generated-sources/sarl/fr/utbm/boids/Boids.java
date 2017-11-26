package fr.utbm.boids;

import fr.utbm.boids.agents.BootAgent;
import fr.utbm.boids.gui.BoidsFxApplication;
import fr.utbm.boids.gui.fx.FxApplication;
import fr.utbm.boids.gui.fx.FxBootAgent;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import java.util.UUID;
import javax.inject.Inject;

@SarlSpecification("0.6")
@SarlElementType(17)
@SuppressWarnings("all")
public class Boids extends FxBootAgent {
  protected Class<? extends FxApplication> getFxApplicationType() {
    return BoidsFxApplication.class;
  }
  
  protected Class<? extends Agent> getApplicationBootAgentType() {
    return BootAgent.class;
  }
  
  @SyntheticMember
  public Boids(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  @Deprecated
  public Boids(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public Boids(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
