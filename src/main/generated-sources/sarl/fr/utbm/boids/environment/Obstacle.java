package fr.utbm.boids.environment;

import fr.utbm.boids.Vector;
import fr.utbm.boids.util.LineTool;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javafx.scene.shape.Polygon;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class Obstacle {
  private List<LineTool> lines;
  
  private Polygon shape;
  
  public Obstacle() {
  }
  
  public Obstacle(final Obstacle obstacle) {
    this.lines = obstacle.lines;
    this.shape = obstacle.shape;
  }
  
  public Obstacle(final List<LineTool> lines, final Polygon shape) {
    this.lines = lines;
    this.shape = shape;
  }
  
  /**
   * def getInfos() : EnvironmentInfos {
   * return this.infos
   * }
   * 
   * def setInfos(infos : EnvironmentInfos) : void {
   * this.infos = infos
   * }
   */
  @Pure
  public List<LineTool> getLines() {
    return this.lines;
  }
  
  public void setLines(final List<LineTool> lines) {
    this.lines = lines;
  }
  
  @Pure
  public Polygon getPolygon() {
    return this.shape;
  }
  
  public void setPolygon(final Polygon polygon) {
    this.setPolygon(polygon);
  }
  
  @Pure
  public int nbPoints() {
    return this.getPolygon().getPoints().size();
  }
  
  @Pure
  public String toString() {
    String _string = this.lines.toString();
    return ("Obstacle: LineTool: " + _string);
  }
  
  @Pure
  public Vector getCenter() {
    double centerX = 0;
    double centerY = 0;
    for (int i = 0; (i < (this.nbPoints() - 1)); i = (i + 2)) {
      {
        double _centerX = centerX;
        Double _get = this.getPolygon().getPoints().get(i);
        centerX = (_centerX + (_get).doubleValue());
        double _centerY = centerY;
        Double _get_1 = this.getPolygon().getPoints().get((i + 1));
        centerY = (_centerY + (_get_1).doubleValue());
      }
    }
    int _nbPoints = this.nbPoints();
    double _divide = (centerX / _nbPoints);
    centerX = _divide;
    int _nbPoints_1 = this.nbPoints();
    double _divide_1 = (centerY / _nbPoints_1);
    centerY = _divide_1;
    return new Vector(centerX, centerY);
  }
  
  @Pure
  public Polygon polygonArea(final double facteur) {
    int nbPoints = this.nbPoints();
    double centreX = 0;
    double centreY = 0;
    List<Double> nouveauxX = new ArrayList<Double>();
    List<Double> nouveauxY = new ArrayList<Double>();
    for (int i = 0; (i < (nbPoints - 1)); i = (i + 2)) {
      {
        double _centreX = centreX;
        Double _get = this.getPolygon().getPoints().get(i);
        centreX = (_centreX + (_get).doubleValue());
        double _centreY = centreY;
        Double _get_1 = this.getPolygon().getPoints().get((i + 1));
        centreY = (_centreY + (_get_1).doubleValue());
      }
    }
    double _centreX = centreX;
    int _nbPoints = this.nbPoints();
    centreX = (_centreX / _nbPoints);
    double _centreY = centreY;
    int _nbPoints_1 = this.nbPoints();
    centreY = (_centreY / _nbPoints_1);
    for (int i = 0; (i < (nbPoints - 1)); i = (i + 2)) {
      {
        Double _get = this.getPolygon().getPoints().get(i);
        double _minus = ((_get).doubleValue() - (centreX * facteur));
        nouveauxX.add(Double.valueOf(_minus));
        Double _get_1 = this.getPolygon().getPoints().get((i + 1));
        double _minus_1 = ((_get_1).doubleValue() - (centreY * facteur));
        nouveauxY.add(Double.valueOf(_minus_1));
      }
    }
    List<Double> fullList = new ArrayList<Double>();
    final Consumer<Double> _function = (Double point) -> {
      fullList.add(point);
    };
    nouveauxX.forEach(_function);
    final Consumer<Double> _function_1 = (Double point) -> {
      fullList.add(point);
    };
    nouveauxY.forEach(_function_1);
    final List<Double> _converted_fullList = (List<Double>)fullList;
    return new Polygon(((double[])Conversions.unwrapArray(_converted_fullList, double.class)));
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
}
