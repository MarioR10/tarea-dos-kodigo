import java.util.HashMap;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        /*
          Esta primera crea el array  de 1000 números aleatorios entre 1000 y 9999, la clave magica y el mensaje secreto
          el numero se almacena en la variable int claveMagica y es usado en el HashMap mensaje donde tenemos nuestro mensaje secreto.

          @author  Juan Manuel Martínez
         * @version 1.0
         * @since   2025-05-15
         */

        //Aca creamos el array de numeros entre 1000 y 9999
        int[] arrayNumeros = new int[1000];
        Random rand = new Random();

        for (int i = 0; i < arrayNumeros.length; i++){
            arrayNumeros[i] = rand.nextInt(9000) + 1000;

        }

        //Aca seleccionamos el numero aleatorio entre el rango de 1000 a 9999
        int indiceClave = rand.nextInt(arrayNumeros.length);
        int claveMagica = arrayNumeros[indiceClave];

        //Aca asociamos el numero aleatorio (claveMagica) al mensaje Secreto
        HashMap<Integer, String> mensaje = new HashMap<>();
        mensaje.put(claveMagica, "El mapa del tesoro está oculto en la última página del códice rojo, sellado por el sabio Eduardo.");

        System.out.println("El número mágico es: " + claveMagica);
        System.out.println("El mensaje es: " + mensaje.get(claveMagica));
    }
}