import java.util.*;

public class NumeroMagico implements Comparable<NumeroMagico>{

    private final int valor;
    private String mensaje;

    public NumeroMagico(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public boolean tieneMensaje() {
        return mensaje != null;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public int compareTo(NumeroMagico otro) {
        return Integer.compare(this.valor, otro.valor);
    }

}
