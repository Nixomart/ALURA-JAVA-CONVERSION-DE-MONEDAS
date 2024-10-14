package unju.ejercicio2.model;

public class ApiResponse {
    private String result;
    private String base_code;
    private String target_code;
    private double conversion_rate;

    // Getters
    public String getResult() {
        return result;
    }

    public String getBaseCode() {
        return base_code;
    }

    public String getTargetCode() {
        return target_code;
    }

    public double getConversionRate() {
        return conversion_rate;
    }
}
