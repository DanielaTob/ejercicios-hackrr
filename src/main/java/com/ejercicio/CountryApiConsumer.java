package com.ejercicio;

import org.json.JSONArray; // Importa la clase para manejar arreglos en JSON.

import org.json.JSONObject; // Importa la clase para manejar objetos en JSON.

import java.io.BufferedReader; // Permite leer texto linea por linea de una entrada (como una conexión HTTP)

import java.io.InputStreamReader; // Convierte los bytes de entrada en caracteres legibles

import java.net.HttpURLConnection; // Clase para manejar conexiones HTTP (GET, POST, etc)

import java.net.URL; // Clase para manejar URLs.

import java.util.ArrayList; // Clase para trabajar con listas dinámicas.

import java.util.Collections; // Clase para utilidades como ordenar listas.

import java.util.Comparator; // Clase para definir criterios de orden personalizado.

import java.util.List; // Interfaz para trabajar con listas en Java.

public class CountryApiConsumer {

  // Metodo principal para encontrar paises segun región y palabra clase
  public static List<String> findCountrys (String region, String keyword) {

      List<String> result = new ArrayList<>(); // Lista para almacenar los resultados (nombre y población).
      int page = 1; // Inicia una busqueda desde la primera pagina de la API.
      boolean hasMorePages = true; // Controla si hay más paginas por procesar.


      try {

          // Ciclo para procesar todas las paginas de la API.
          while (hasMorePages) {

              // Construir la URL con parametros de busqueda.
              String apiUrl = String.format(
                      "https://jsonmock.hackerrank.com/api/countries/search?region=%s&name=%s&page=%d",
                        region, keyword, page
                      );

              // Crear una conexión HTTP hacia la API
              HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
              // Define que va a ser una solicitud GET.
              connection.setRequestMethod("GET");


              // Leer la respuesta de la API linea por linea.
              BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
              // Almacena toda la respuesta como un texto.
              StringBuilder response = new StringBuilder();

              String line;
              // Leer cada linea de la respuesta
              while((line = reader.readLine()) != null){
                  // Añade cada linea al StringBuilder.
                  response.append(line);
              }

              // Cierra el lector para liberar recursos.
              reader.close();
              // Cierra la conexión HTTP.
              connection.disconnect();


              // Convertir la respuesta en un objeto JSON.
              JSONObject jsonResponse = new JSONObject(response.toString());
              // Extraer el arreglo "data" de la respuesta.
              JSONArray data = jsonResponse.getJSONArray("data");

              //Procesar cada pais dentro del arreglo data.
              for(int i = 0; i < data.length(); i++ ){
                  JSONObject country = data.getJSONObject(i); // Obtener un pais del arreglo.
                  String name = country.getString("name"); // Extraer el nombre del pais.
                  int population = country.getInt("population"); // Extraer la poblacion del pais.
                  result.add(name + "," + population); // Añadir el pais y su poblacion a la lista.
              }

              // Verificar si hay más paginas por procesar.
              int totalPages = jsonResponse.getInt("total_pages"); // Numero total de paginas.
              hasMorePages = page < totalPages; // Verifica si la pagina actual es menor al total.
              page++; // Avanza a la siguiente pagina.


              // Ordenar la lista por su población (ASCENDENTE) y luego por nombre (ALFABETICO)
              Collections.sort(result, new Comparator<String>() {
                  @Override
                  public int compare(String o1, String o2) {
                      // Separar el nombre y la poblacion de cada entrada.
                      String[] parts1 = o1.split(","); // Divide la cadena en nombre y población.
                      String[] parts2 = o2.split(","); // Divide la cadena en nombre y poblacion.


                      //CConvertir la poblacion (parte 2) a enteros.
                      int population1 = Integer.parseInt(parts1[1]);
                      int population2 = Integer.parseInt(parts2[1]);

                      // Comparar por poblacion primero
                      if (population1 != population2) {
                          return  Integer.compare(population1, population2); // Menor a mayor.
                      }
                      // Si las poblaciones son iguales, comparar por nombre.
                      return parts1[0].compareTo(parts2[0]); // Orden alfabetico.
                  }
              });

          }

      } catch (Exception e) { // Captura la excepcion durante el proceso.
          e.printStackTrace(); // Imprime la pila de errores para depuración.
      }

      return result; // Retorna la lista de paises ordenada.
  }

    // Método principal para ejecutar el programa
    public static void main(String[] args) {
        // Llamar al método para buscar países en la región "Asia" con la letra "a" en su nombre
        List<String> countries = findCountrys("Asia", "a");

        // Imprimir los países encontrados
        System.out.println("Países ordenados por población y nombre:");
        for (String country : countries) { // Recorre la lista y muestra cada país.
            System.out.println(country);// Imprime cada país en la consola.
        }
    }



}
