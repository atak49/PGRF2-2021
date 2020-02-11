package c05_utery_13_15.renderer;

import c05_utery_13_15.model.Element;
import c05_utery_13_15.model.Vertex;
import c05_utery_13_15.view.Raster;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Mat4PerspRH;
import transforms.Vec3D;

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

