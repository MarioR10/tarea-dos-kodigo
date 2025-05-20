import java.util.List;

/** Clase de utilidad para realizar búsquedas binarias en listas de NumeroMagico.
 * Requiere que la lista esté previamente ordenada por el valor de NumeroMagico.
 */
public class BuscadorMagico {

    /**
     * Realiza una búsqueda binaria para encontrar un NumeroMagico específico en una lista ordenada.
     * @param lista La lista de NumeroMagico donde se realizará la búsqueda.

     * @param valor El valor entero del NumeroMagico que se desea encontrar.
     * @return El objeto NumeroMagico si se encuentra una coincidencia exacta por valor,
     * o null si el valor no está presente en la lista.
     */

    public static NumeroMagico buscarNumeroBinario(List<NumeroMagico> lista, int valor) {

        //Inicializacion de punteros

        //Al principio de la lista
        int izquierda = 0;

        //Al final de la lista
        int derecha = lista.size() - 1;

        //El bucle continua mientras el puntero izquierdo sea menor que el derecho
        while (izquierda <= derecha) {

            // Calcula el índice del medio. Se calcula asi para evitar desbordamientos para numeros muy grandes
            int medio = izquierda + (derecha - izquierda) / 2;

            //Obtenemos el objeto NumeroMagico en la posicion medio
            NumeroMagico actual = lista.get(medio);

            // Compara el valor del NumeroMagico actual con el valor que estamos buscando.
            int comparacion = actual.compareTo(new NumeroMagico(valor));

            //Comparaciones

            if (comparacion == 0) {
                // Si comparacion es 0, significa que los valores son iguales.
                // Hemos encontrado el NumeroMagico con el valor deseado.
                return actual;
            } else if (comparacion < 0) {
                // Si comparacion es menor que 0, significa que el valor del NumeroMagico actual
                // es menor que el valor que buscamos. Por lo tanto, el valor buscado
                // debe estar en la mitad derecha de la lista.

                //Se ajusta para empezar a buscar desde el elemento siguiente al medio
                izquierda = medio + 1;
            } else {
                // Si comparacion es mayor que 0, significa que el valor del NumeroMagico actual
                // es mayor que el valor que buscamos. Por lo tanto, el valor buscado
                // debe estar en la mitad izquierda de la lista.

                //Se ajusta para buscar hasta el elemento anterior al medio
                derecha = medio - 1;
            }
        }
        // Si el bucle termina, significa que el valor no se encontró en la lista.
        return null;
    }
}