package sample.phisic;

import javafx.geometry.Point3D;
import javafx.scene.shape.Sphere;

public class Body {
    public double x;
    public double y;
    public double z;
    public double vx;
    public double vy;
    public double vz;
    public double mass;

    public String color = "#ffffff";
    public double radius;
    public Sphere sphere = new Sphere();
}
