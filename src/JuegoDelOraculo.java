import java.util.*;

public class JuegoDelOraculo {


    private List<NumeroMagico> numeros;

    private int claveMagica;

    private final List<String> mensajesSecretos = Arrays.asList(
            "El tesoro está oculto bajo la montaña del destino.",
            "El portal al reino sumergido se abrirá cuando el sol cruce la cima del monte hueco.",
            "Bajo la tercera luna de piedra, encontrarás la biblioteca olvidada del conocimiento prohibido.",
            "El mapa del tesoro está oculto en la última página del códice rojo, sellado por el sabio Alerion."
    );

    private final Scanner scanner = new Scanner(System.in);


    public void iniciar() {
        generarNumeros(10);
        System.out.println("Numeros generados. Ordenando...");

        long tiempoOrdenamiento = medirTiempo(() -> ordenarNumeros());
        System.out.println("Ordenamiento completado en " + tiempoOrdenamiento + " ms.\n");

        final boolean[] encontrado = {false};
        while (!encontrado[0]) {
            System.out.print("Ingresa un número mágico (entre 1000 y 9999): ");
            int intento = scanner.nextInt();
            long tiempoBusqueda = medirTiempo(() -> {
                encontrado[0] = buscarNumero(intento);
            });
            System.out.println("Búsqueda completada en " + tiempoBusqueda + " ms.");
        }
    }

    private void generarNumeros(int cantidad) {
        Set<Integer> unicos = new HashSet<>();
        Random rand = new Random();

        while (unicos.size() < cantidad) {
            unicos.add(1000 + rand.nextInt(9000));
        }

        List<Integer> listaSimple = new ArrayList<>(unicos);
        claveMagica = listaSimple.get(rand.nextInt(listaSimple.size()));

        System.out.println(claveMagica);

        String mensajeAleatorio = mensajesSecretos.get(rand.nextInt(mensajesSecretos.size()));

        numeros = new ArrayList<>();
        for (Integer num : listaSimple) {
            NumeroMagico nm = new NumeroMagico(num);
            if (num == claveMagica) nm.setMensaje(mensajeAleatorio);
            numeros.add(nm);
        }
    }


    private void ordenarNumeros() {
        mergeSort(numeros);
    }

    private boolean buscarNumero(int valor) {
        int izquierda = 0;
        int derecha = numeros.size() - 1;

        while (izquierda <= derecha) {
            int medio = (izquierda + derecha) / 2;
            int actual = numeros.get(medio).getValor();

            if (actual == valor) {
                if (numeros.get(medio).tieneMensaje()) {
                    System.out.println("¡Correcto! El Oráculo dice: " + numeros.get(medio).getMensaje());
                    return true;
                } else {
                    System.out.println("Ese número no contiene el mensaje del oráculo.");
                    return false;
                }
            } else if (actual < valor) {
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }

        System.out.println("Número no encontrado en el cáliz del oráculo.");
        return false;
    }

    private long medirTiempo(Runnable tarea) {
        long inicio = System.nanoTime();
        tarea.run();
        long fin = System.nanoTime();
        return (fin - inicio) / 1_000_000; // convertir a milisegundos
    }

    // MergeSort recursivo para objetos NumeroMagico
    private void mergeSort(List<NumeroMagico> lista) {
        if (lista.size() <= 1) return;

        int mitad = lista.size() / 2;
        List<NumeroMagico> izquierda = new ArrayList<>(lista.subList(0, mitad));
        List<NumeroMagico> derecha = new ArrayList<>(lista.subList(mitad, lista.size()));

        mergeSort(izquierda);
        mergeSort(derecha);
        mezclar(lista, izquierda, derecha);
    }

    private void mezclar(List<NumeroMagico> lista, List<NumeroMagico> izquierda, List<NumeroMagico> derecha) {
        int i = 0, j = 0, k = 0;

        while (i < izquierda.size() && j < derecha.size()) {
            if (izquierda.get(i).compareTo(derecha.get(j)) <= 0) {
                lista.set(k++, izquierda.get(i++));
            } else {
                lista.set(k++, derecha.get(j++));
            }
        }

        while (i < izquierda.size()) lista.set(k++, izquierda.get(i++));
        while (j < derecha.size()) lista.set(k++, derecha.get(j++));
    }

}
