package fr.utbm.boids.environment;

import fr.utbm.boids.util.Coordinates;
import fr.utbm.boids.util.Edge;
import fr.utbm.boids.util.LineTool;
import fr.utbm.boids.util.Vector;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javafx.scene.shape.Polygon;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.DoubleExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class Obstacle {
  private List<LineTool> lines;
  
  private Polygon shape;
  
  private List<Edge> edges;
  
  public Obstacle() {
  }
  
  public Obstacle(final Obstacle obstacle) {
    this.lines = obstacle.lines;
    this.shape = obstacle.shape;
    ArrayList<Edge> _arrayList = new ArrayList<Edge>();
    this.edges = _arrayList;
    int i = 0;
    for (final Edge e : obstacle.edges) {
      {
        this.edges.add(i, e);
        i++;
      }
    }
  }
  
  public Obstacle(final List<LineTool> lines, final Polygon shape) {
    this.lines = lines;
    this.shape = shape;
    ArrayList<Edge> _arrayList = new ArrayList<Edge>();
    this.edges = _arrayList;
    int nombrePoints = this.nbPoints();
    for (int i = 0; (i < nombrePoints); i = (i + 2)) {
      Double _get = this.shape.getPoints().get((i % nombrePoints));
      Double _get_1 = this.shape.getPoints().get(((i + 1) % nombrePoints));
      Coordinates _coordinates = new Coordinates((_get).doubleValue(), (_get_1).doubleValue());
      Double _get_2 = this.shape.getPoints().get(((i + 2) % nombrePoints));
      Double _get_3 = this.shape.getPoints().get(((i + 3) % nombrePoints));
      Coordinates _coordinates_1 = new Coordinates((_get_2).doubleValue(), (_get_3).doubleValue());
      Edge _edge = new Edge(_coordinates, _coordinates_1);
      this.edges.add((i / 2), _edge);
    }
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
  
  @Pure
  public List<Edge> getEdges() {
    return this.edges;
  }
  
  public void setPolygon(final Polygon polygon) {
    this.setPolygon(polygon);
  }
  
  public void setEdges(final List<Edge> edges) {
    this.edges = edges;
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
    int _divide = (_nbPoints / 2);
    double _divide_1 = (centerX / _divide);
    centerX = _divide_1;
    int _nbPoints_1 = this.nbPoints();
    int _divide_2 = (_nbPoints_1 / 2);
    double _divide_3 = (centerY / _divide_2);
    centerY = _divide_3;
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
  
  @Pure
  public int getWidth() {
    int nbPoints = this.nbPoints();
    int i = 0;
    i = 2;
    Double maxX = this.getPolygon().getPoints().get(0);
    Double minX = this.getPolygon().getPoints().get(0);
    while ((i < nbPoints)) {
      {
        Double _get = this.getPolygon().getPoints().get(i);
        boolean _lessThan = (maxX.compareTo(_get) < 0);
        if (_lessThan) {
          maxX = this.getPolygon().getPoints().get(i);
        }
        Double _get_1 = this.getPolygon().getPoints().get(i);
        boolean _greaterThan = (minX.compareTo(_get_1) > 0);
        if (_greaterThan) {
          minX = this.getPolygon().getPoints().get(i);
        }
        int _i = i;
        i = (_i + 2);
      }
    }
    return Double.valueOf(DoubleExtensions.operator_minus(maxX, minX)).intValue();
  }
  
  @Pure
  public int getMinX() {
    int nbPoints = this.nbPoints();
    int i = 0;
    i = 2;
    Double minX = this.getPolygon().getPoints().get(0);
    while ((i < nbPoints)) {
      {
        Double _get = this.getPolygon().getPoints().get(i);
        boolean _greaterThan = (minX.compareTo(_get) > 0);
        if (_greaterThan) {
          minX = this.getPolygon().getPoints().get(i);
        }
        int _i = i;
        i = (_i + 2);
      }
    }
    return minX.intValue();
  }
  
  @Pure
  public int getMinY() {
    int nbPoints = this.nbPoints();
    int i = 0;
    i = 3;
    Double minY = this.getPolygon().getPoints().get(1);
    while ((i < nbPoints)) {
      {
        Double _get = this.getPolygon().getPoints().get(i);
        boolean _greaterThan = (minY.compareTo(_get) > 0);
        if (_greaterThan) {
          minY = this.getPolygon().getPoints().get(i);
        }
        int _i = i;
        i = (_i + 2);
      }
    }
    return minY.intValue();
  }
  
  @Pure
  public int getHeight() {
    int nbPoints = this.nbPoints();
    int i = 0;
    i = 3;
    Double maxY = this.getPolygon().getPoints().get(1);
    Double minY = this.getPolygon().getPoints().get(1);
    while ((i < nbPoints)) {
      {
        Double _get = this.getPolygon().getPoints().get(i);
        boolean _lessThan = (maxY.compareTo(_get) < 0);
        if (_lessThan) {
          maxY = this.getPolygon().getPoints().get(i);
        }
        Double _get_1 = this.getPolygon().getPoints().get(i);
        boolean _greaterThan = (minY.compareTo(_get_1) > 0);
        if (_greaterThan) {
          minY = this.getPolygon().getPoints().get(i);
        }
        int _i = i;
        i = (_i + 2);
      }
    }
    return Double.valueOf(DoubleExtensions.operator_minus(maxY, minY)).intValue();
  }
  
  @Pure
  public Vector getCentreCarre() {
    Vector centreCarre = null;
    int _minX = this.getMinX();
    int _minY = this.getMinY();
    Vector _vector = new Vector(_minX, _minY);
    centreCarre = _vector;
    int _width = this.getWidth();
    int _divide = (_width / 2);
    int _height = this.getHeight();
    int _divide_1 = (_height / 2);
    Vector _vector_1 = new Vector(_divide, _divide_1);
    centreCarre.plus(_vector_1);
    return centreCarre;
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
