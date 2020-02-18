package c04_utery_11_35.renderer;

import c04_utery_11_35.model.Element;
import c04_utery_11_35.model.ElementType;
import c04_utery_11_35.model.Vertex;
import c04_utery_11_35.renderer.Shader;
import transforms.*;
import c04_utery_11_35.view.Raster;

import java.awt.*;
import java.util.List;

public class Renderer3D implements GPURenderer {

    private final Raster raster;
    private Mat4 model, view, projection;

    public Renderer3D(Raster raster) {
        this.raster = raster;

        model = new Mat4Identity();
        view = new Mat4Identity();

        projection = new Mat4PerspRH(Math.PI / 4, Raster.HEIGHT / (float) Raster.WIDTH, 1, 200);
    }

    @Override
    public void draw(List<Element> elements, List<Vertex> vb, List<Integer> ib) {
        for (Element element : elements) {
            final int start = element.getStart();
            final int count = element.getCount();
            if (element.getElementType() == ElementType.TRIANGLE) {
                for (int i = start; i < count + start; i += 3) {
                    final Integer i1 = ib.get(i);
                    final Integer i2 = ib.get(i + 1);
                    final Integer i3 = ib.get(i + 2);
                    final Vertex v1 = vb.get(i1);
                    final Vertex v2 = vb.get(i2);
                    final Vertex v3 = vb.get(i3);
                    prepareTriangle(v1, v2, v3);
                }
            } else if (element.getElementType() == ElementType.LINE) {

            } else {
                // point
            }
        }
    }

    private void prepareTriangle(Vertex a, Vertex b, Vertex c) {
        // 1. transformace vrcholů
        // TODO příští cvičení

        // 2. ořezání
        // rychlé ořezání zobrazovacím objemem
        // vyhodí trojúhelníky, které jsou celé mimo zobrazovací objem
        // TODO příští cvičení

        //3. seřazení trojúhelníků podle souřadnice z
        if (a.z < b.z) {
            Vertex temp = a;
            a = b;
            b = temp;
        }
        if (b.z < c.z) {
            Vertex temp = b;
            b = c;
            c = temp;
        }
        if (a.z < b.z) {
            Vertex temp = a;
            a = b;
            b = temp;
        }
    }

    private Vec3D transformToWindow(Vec3D v) {
        return v.mul(new Vec3D(1, -1, 1)) // Y jde nahoru, chceme dolu
                .add(new Vec3D(1, 1, 0)) // (0,0) je uprostřed, chceme v rohu
                // máme <0, 2> -> vynásobíme polovinou velikosti plátna
                .mul(new Vec3D(Raster.WIDTH / 2f, Raster.HEIGHT / 2f, 1));
    }

    @Override
    public void clear() {

    }

    @Override
    public Mat4 getModel() {
        return model;
    }

    @Override
    public void setModel(Mat4 model) {
        this.model = model;
    }

    @Override
    public Mat4 getView() {
        return view;
    }

    @Override
    public void setView(Mat4 view) {
        this.view = view;
    }

    @Override
    public Mat4 getProjection() {
        return projection;
    }

    @Override
    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }

    @Override
    public void setShader(Shader<Vertex, Color> shader) {

    }

}

