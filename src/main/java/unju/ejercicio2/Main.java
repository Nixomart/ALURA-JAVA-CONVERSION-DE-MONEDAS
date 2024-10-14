package unju.ejercicio2;

import com.google.gson.Gson;
import unju.ejercicio2.model.ApiResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
    private static final String API_KEY = "6373d628091f35bbe3addd0f";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("\n--- Menú Conversor de Monedas ---");
            System.out.println("1. Convertir USD a ARS");
            System.out.println("2. Convertir entre dos monedas (ejemplos: ARS USD, BGN CAD, EUR AUD)");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    convertirMoneda("USD", "ARS", scanner);
                    break;

                case 2:
                    System.out.print("Ingrese la moneda base (ejemplo: ARS): ");
                    String baseCurrency = scanner.nextLine().toUpperCase();
                    System.out.print("Ingrese la moneda destino (ejemplo: USD): ");
                    String targetCurrency = scanner.nextLine().toUpperCase();
                    convertirMoneda(baseCurrency, targetCurrency, scanner);
                    break;

                case 3:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (option != 3);

        scanner.close();
    }
    public static void convertirMoneda(String baseCurrency, String targetCurrency, Scanner scanner) {
        System.out.print("Ingrese la cantidad a convertir: ");
        double amount = scanner.nextDouble();

        try {
            String urlString = API_URL + baseCurrency + "/" + targetCurrency;
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                Gson gson = new Gson();
                ApiResponse currencyResponse = gson.fromJson(content.toString(), ApiResponse.class);
                if ("success".equals(currencyResponse.getResult())) {
                    double conversionRate = currencyResponse.getConversionRate();
                    double convertedAmount = amount * conversionRate;
                    System.out.printf("%.2f %s equivale a %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);
                } else {
                    System.out.println("Error en la conversión.");
                }
            } else {
                System.out.println("Error en la solicitud HTTP. Código de estado: " + status);
            }

            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}