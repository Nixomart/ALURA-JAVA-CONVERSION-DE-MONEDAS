package unju.ejercicio2.services;
import com.google.gson.Gson;
import unju.ejercicio2.model.Currency;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExchangeRateService {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/6373d628091f35bbe3addd0f/latest/USD";
    public double convert(String fromCurrency, String toCurrency, double amount, Currency currency) {
        double fromRate = currency.getRates().get(fromCurrency);
        double toRate = currency.getRates().get(toCurrency);
        return amount * (toRate / fromRate);
    }
    public String getExchangeRatesJSON() throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        if (status != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + status);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        con.disconnect();
        return content.toString();
    }
    public Currency getExchangeRates() throws Exception {
        String jsonResponse = getExchangeRatesJSON(); // Llamar al método que obtiene el JSON
        Gson gson = new Gson();
        return gson.fromJson(jsonResponse, Currency.class);
    }
}
