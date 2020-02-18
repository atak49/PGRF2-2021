package c05_utery_13_15.controller;

import c05_utery_13_15.model.Element;
import c05_utery_13_15.model.ElementType;
import c05_utery_13_15.model.Vertex;
import c05_utery_13_15.renderer.GPURenderer;
import c05_utery_13_15.renderer.Renderer3D;
import c05_utery_13_15.view.Raster;
import transforms.Camera;
import transforms.Mat4Identity;
import transforms.Mat4Transl;
import transforms.Vec3D;

import javax.swing.*;
import javax.swing.event.MenuKeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Controller3D {

    private GPURenderer renderer3D;
    private Camera camera;

    private int mx, my;

    private List<Element> elements;
    private List<Vertex> vb;
    private List<Integer> ib;

    public Controller3D(Raster raster) {
        initObjects(raster);
        initListeners(raster);
    }

    private void display() {
        renderer3D.clear();

        renderer3D.setModel(new Mat4Identity());
        renderer3D.setView(camera.getViewMatrix());
//        renderer3D.setProjection();

        renderer3D.draw(elements, vb, ib);

        renderer3D.setModel(new Mat4Transl(5, 0, 0));
//        renderer3D.draw();
    }

    private void initObjects(Raster raster) {
        renderer3D = new Renderer3D(raster);
        resetCamera();

        // předpoklad naplněného vertex bufferu

        ib.add(0);
        ib.add(1);
        ib.add(2);
        ib.add(2);
        ib.add(2);
        ib.add(1);
        ib.add(3);
        ib.add(4);
        ib.add(5);
        ib.add(2);
        ib.add(4);

        elements.add(new Element(ElementType.TRIANGLE, 6, 0));
        elements.add(new Element(ElementType.LINE, 2, 6));
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
