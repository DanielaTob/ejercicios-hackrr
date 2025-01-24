package com.ejercicio;

public class FlipsPractice {

    // Método que calcula el número mínimo de cambios necesarios
    public static int getMinFlips (String pwd){
        int n = pwd.length(); // Longitud de la cadena.
        int flips = 0; // Contador de cambios necesarios.


        // Recorremos la cadena en pares (de dos en dos)
        for ( int i = 0; i < n -1 ; i += 2){ // Nos aseguramos de no salirnos del indice.
            // Si el primer caracter es '0' y el segundo no es '0', incrementamos flips.
            if(pwd.charAt(i) == '0' && pwd.charAt(i + 1) != '0') {
                flips++;
            }
            // Si el primer caracter es '1' y el segundo no es '1', incrementamos flips.
            else if (pwd.charAt(i) == '1' && pwd.charAt(i + 1) != '1'){
                flips++;
            }
        }

        // Retornamos el numero total de flips.
        return flips;
    }

    public static void main(String[] args) {
        // Ejemplo de cadena de entrada
        String input = "0110";

        // Llamamos al método getMinFlips y guardamos el resultado
        int result = getMinFlips(input);

        // Mostramos el resultado en la consola
        System.out.println("Número mínimo de flips necesarios: " + result);
    }
}
