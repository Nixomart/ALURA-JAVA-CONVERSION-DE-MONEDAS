package unju.ejercicio2;

import unju.ejercicio2.model.Currency;
import unju.ejercicio2.services.ExchangeRateService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExchangeRateService service = new ExchangeRateService();

        try {
            Currency currency = service.getExchangeRates();

            System.out.println("Ingrese la moneda de origen (por ejemplo, USD): ");
            String fromCurrency = scanner.nextLine().toUpperCase();

            System.out.println("Ingrese la moneda de destino (por ejemplo, EUR): ");
            String toCurrency = scanner.nextLine().toUpperCase();

            System.out.println("Ingrese la cantidad: ");
            double amount = scanner.nextDouble();

            double result = service.convert(fromCurrency, toCurrency, amount, currency);
            System.out.println(amount + " " + fromCurrency + " equivale a " + result + " " + toCurrency);

        } catch (Exception e) {
            System.out.println("Error al realizar la conversión: " + e.getMessage());
        }

        scanner.close();
    }
}