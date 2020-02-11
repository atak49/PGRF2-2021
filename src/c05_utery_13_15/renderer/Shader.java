package c05_utery_13_15.renderer;

@FunctionalInterface
public interface Shader<V, C> {

    C shade(V... v);

}