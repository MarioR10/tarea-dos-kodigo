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
        System.out.println("Selecciona el algoritmo de ordenamiento:");
        System.out.println("1. MergeSort");
        System.out.println("2. QuickSort");
        System.out.println("3. Ambos (comparar tiempos)");
        System.out.print("Opción: ");
        int opcion = scanner.nextInt();
        generarNumeros(1000);
        if (opcion == 3) {
            List<NumeroMagico> copiaMerge = new ArrayList<>();
            List<NumeroMagico> copiaQuick = new ArrayList<>();
            for (NumeroMagico nm : numeros) copiaMerge.add(new NumeroMagico(nm.getValor()));
            for (NumeroMagico nm : numeros) copiaQuick.add(new NumeroMagico(nm.getValor()));

            System.out.println("Ejecutando MergeSort...");
            long tiempoMerge = medirTiempo(() -> mergeSort(copiaMerge));
            System.out.println("MergeSort finalizado en " + tiempoMerge + " ms.");

            System.out.println("Ejecutando QuickSort...");
            long tiempoQuick = medirTiempo(() -> quickSort(copiaQuick, 0, copiaQuick.size() - 1));
            System.out.println("QuickSort finalizado en " + tiempoQuick + " ms.");

            System.out.println("Usando lista ordenada con QuickSort para el juego.");
            numeros = copiaQuick;
        } else {
            System.out.println("Numeros generados. Ordenando usando " + (opcion == 1 ? "MergeSort" : "QuickSort") + "...");
            long tiempoOrdenamiento = medirTiempo(() -> ordenarNumeros(opcion));
            System.out.println("Ordenamiento completado en " + tiempoOrdenamiento + " ms.");
        }

        long tiempoOrdenamiento = medirTiempo(() -> ordenarNumeros(opcion));
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


    private void ordenarNumeros(int opcion) {
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

    private void quickSort(List<NumeroMagico> lista, int inicio, int fin) {
        if (inicio >= fin) return;
        int pivoteIndex = particionar(lista, inicio, fin);
        quickSort(lista, inicio, pivoteIndex - 1);
        quickSort(lista, pivoteIndex + 1, fin);
    }

    private int particionar(List<NumeroMagico> lista, int inicio, int fin) {
        NumeroMagico pivote = lista.get(fin);
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {
            if (lista.get(j).compareTo(pivote) <= 0) {
                i++;
                Collections.swap(lista, i, j);
            }
        }
        Collections.swap(lista, i + 1, fin);
        return i + 1;
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
