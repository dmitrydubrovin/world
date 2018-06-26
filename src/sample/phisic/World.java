package sample.phisic;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

import java.util.List;

public class World {
    public double G;
    public int warp;
    public List<Body> bodies;

    public void calc() {
        for (int i = 0; i < bodies.size(); i++) {
            for (int j = i + 1; j < bodies.size(); j++) {
                Body b1 = bodies.get(i);
                Body b2 = bodies.get(j);
                Point3D p1 = new Point3D(b1.x, b1.y, b1.z);
                Point3D p2 = new Point3D(b2.x, b2.y, b2.z);
                double forse = p1.distance(p2) * G / b1.mass / b2.mass;
                Point3D sub = p2.subtract(p1).multiply(1 / p1.distance(p2)).multiply(forse);
                b1.vx += sub.getX();
                b1.vy += sub.getY();
                b1.vz += sub.getZ();
                b2.vx -= sub.getX();
                b2.vy -= sub.getY();
                b2.vz -= sub.getZ();
            }
        }
    }

    public void move() {
        for (Body body : bodies) {
            body.x += body.vx;
            body.y += body.vy;
            body.z += body.vz;
        }
    }

    public void init() {
        for (Body body : bodies) {
            body.sphere.setRadius(body.radius);
            PhongMaterial ph = new PhongMaterial();
            ph.setDiffuseColor(Color.valueOf(body.color));
            body.sphere.setMaterial(ph);
        }
    }

    public void relax() {
        for (Body body : bodies) {
            body.sphere.setTranslateX(body.x);
            body.sphere.setTranslateY(body.y);
            body.sphere.setTranslateZ(body.z);
        }
    }

    public Thread phisics = new Thread(() -> {
        init();
        int cnt = 0;
        while (true) {
            cnt++;
            calc();
            move();
            if (cnt == warp) {
                cnt = 0;
                relax();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
}
