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
  public final static Map<Integer, Color> COLOR_FAMILY = Collections.<Integer, Color>unmodifiableMap(CollectionLiterals.<Integer, Color>newHashMap(Pair.<Integer, Color>of(Integer.valueOf(1), Color.rgb(0, 174, 255)), Pair.<Integer, Color>of(Integer.valueOf(2), Color.rgb(255, 66, 66)), Pair.<Integer, Color>of(Integer.valueOf(3), Color.rgb(0, 217, 112)), Pair.<Integer, Color>of(Integer.valueOf(4), Color.rgb(211, 66, 255)), Pair.<Integer, Color>of(Integer.valueOf(5), Color.rgb(255, 149, 20)), Pair.<Integer, Color>of(Integer.valueOf(6), Color.rgb(252, 61, 240)), Pair.<Integer, Color>of(Integer.valueOf(7), Color.rgb(176, 155, 111)), Pair.<Integer, Color>of(Integer.valueOf(8), Color.rgb(74, 243, 255)), Pair.<Integer, Color>of(Integer.valueOf(9), Color.rgb(41, 204, 182)), Pair.<Integer, Color>of(Integer.valueOf(10), Color.rgb(54, 74, 255))));
}
