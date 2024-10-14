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
        System.out.println("Ingrese la moneda origen (ejemplo: EUR): ");
        String baseCurrency = scanner.nextLine().toUpperCase();
        System.out.println("Ingrese la moneda a la que quiere cambiar (ejemplo: GBP): ");
        String targetCurrency = scanner.nextLine().toUpperCase();
        System.out.println("Ingrese la cantidad a cambiar: ");
        double amount = scanner.nextDouble();
        try {
            String urlString = API_URL + baseCurrency + "/" + targetCurrency;
            URL url = new URL(urlString);
            HttpURLConnection cnn = (HttpURLConnection) url.openConnection();
            cnn.setRequestMethod("GET");

            int status = cnn.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(cnn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();
                /*
                *
                * ACA MAPEO EL RESULTADO DE LA API EN JSON
                * */
                ApiResponse currencyResponse = gson.fromJson(content.toString(), ApiResponse.class);

                if ("success".equals(currencyResponse.getResult())) {
                    double conversionRate = currencyResponse.getConversionRate();
                    double convertedAmount = amount * conversionRate;
                    System.out.printf("%.2f %s equivale a %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);
                } else {
                    System.out.println("Error en la conversion.");
                }

            } else {
                System.out.println("Error estado de la http: " + status);
            }

            cnn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}