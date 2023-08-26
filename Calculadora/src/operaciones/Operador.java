package operaciones;

public class Operador implements Operacion{
    @Override
    public double suma(double a, double b) {
        return a+b;
    }

    @Override
    public double resta(double a, double b) {
        return a-b;
    }

    @Override
    public double multiplicacion(double a, double b) {
        return a*b;
    }

    @Override
    public double division(double a, double b) {
        return a/b;
    }

    @Override
    public double raizCuadrada(double a) {
        return Math.sqrt(a);
    }

    @Override
    public double modulo(double a, double b) {
        return a%b;
    }
}
