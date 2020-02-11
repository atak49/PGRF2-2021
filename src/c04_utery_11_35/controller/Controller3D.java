package c04_utery_11_35.controller;

import c04_utery_11_35.renderer.GPURenderer;
import c04_utery_11_35.renderer.Renderer3D;
import c04_utery_11_35.view.Raster;
import transforms.Camera;
import transforms.Vec3D;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller3D {

    private GPURenderer renderer3D;
    private Camera camera;

    private int mx, my;

    public Controller3D(Raster raster) {
        initObjects(raster);
        initListeners(raster);
    }

    private void display() {

    }

    private void initObjects(Raster raster) {
        renderer3D = new Renderer3D(raster);
        resetCamera();
    }

    private void resetCamera() {
        camera = new Camera(
                new Vec3D(0, -5, 3),
                Math.toRadians(90),
                Math.toRadians(-40),
                1, true
        );
        renderer3D.setView(camera.getViewMatrix());
    }

    private void initListeners(Raster raster) {
        raster.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mx = e.getX();
                my = e.getY();
            }
        });

        raster.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    double diff = (mx - e.getX()) / 100.0;
                    double azimuth = camera.getAzimuth() + diff;
                    camera = camera.withAzimuth(azimuth);
                    renderer3D.setView(camera.getViewMatrix());

                    // dodělat zenit, ořezat <-PI/2,PI/2>
                }

                // dodělat transformace
                mx = e.getX();
                my = e.getY();
            }
        });
        raster.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        camera = camera.forward(1);
                        renderer3D.setView(camera.getViewMatrix());
                        break;
                    // dodělat ovládání
                }
            }
        });
    }

}
