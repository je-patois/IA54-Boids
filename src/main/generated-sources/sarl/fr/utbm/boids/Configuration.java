package fr.utbm.boids;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import java.util.Collections;
import java.util.Map;
import javafx.scene.paint.Color;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Pair;

@SarlSpecification("0.6")
@SarlElementType(10)
@SuppressWarnings("all")
public interface Configuration {
  public final static Map<Integer, Color> COLOR_FAMILY = Collections.<Integer, Color>unmodifiableMap(CollectionLiterals.<Integer, Color>newHashMap(Pair.<Integer, Color>of(Integer.valueOf(1), Color.RED), Pair.<Integer, Color>of(Integer.valueOf(2), Color.BLUE), Pair.<Integer, Color>of(Integer.valueOf(3), Color.GREEN), Pair.<Integer, Color>of(Integer.valueOf(4), Color.YELLOW), Pair.<Integer, Color>of(Integer.valueOf(5), Color.PURPLE), Pair.<Integer, Color>of(Integer.valueOf(6), Color.ORANGE), Pair.<Integer, Color>of(Integer.valueOf(7), Color.BROWN), Pair.<Integer, Color>of(Integer.valueOf(8), Color.PINK), Pair.<Integer, Color>of(Integer.valueOf(9), Color.BEIGE), Pair.<Integer, Color>of(Integer.valueOf(10), Color.CYAN)));
}
