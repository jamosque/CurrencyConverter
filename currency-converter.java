import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONObject;

class CurrencyConverterApp {
    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();
        converter.start();
    }
}

class CurrencyConverter {
    private static final String API_KEY = "1640b72d9e5ae06c83c1d295";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private Scanner scanner;
    private ExchangeRateService exchangeRateService;
    private TransactionHistory transactionHistory;

    public CurrencyConverter() {
        this.scanner = new Scanner(System.in);
        this.exchangeRateService = new ExchangeRateService();
        this.transactionHistory = new TransactionHistory();
    }

    public void start() {
        while (true) {
            showMenu();
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    performConversion();
                    break;
                case 2:
                    showHistory();
                    break;
                case 3:
                    System.out.println("¡Gracias por usar nuestro convertidor!");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor intente de nuevo.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n=== CONVERTIDOR DE DIVISAS ===");
        System.out.println("1. Realizar conversión");
        System.out.println("2. Ver historial de conversiones");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void performConversion() {
        System.out.println("\n=== NUEVA CONVERSIÓN ===");
        System.out.print("Ingrese la cantidad en COP: ");
        double amount = scanner.nextDouble();

        System.out.println("\nMonedas disponibles:");
        System.out.println("1. USD - Dólar estadounidense");
        System.out.println("2. EUR - Euro");
        System.out.println("3. GBP - Libra esterlina");
        System.out.print("Seleccione la moneda destino (1-3): ");

        String targetCurrency = "";
        int currencyOption = scanner.nextInt();
        switch (currencyOption) {
            case 1: targetCurrency = "USD"; break;
            case 2: targetCurrency = "EUR"; break;
            case 3: targetCurrency = "GBP"; break;
            default:
                System.out.println("Opción no válida");
                return;
        }

        try {
            double rate = exchangeRateService.getExchangeRate("COP", targetCurrency);
            double result = amount / rate;

            Transaction transaction = new Transaction(
                    amount,
                    "COP",
                    targetCurrency,
                    result,
                    rate
            );

            transactionHistory.addTransaction(transaction);
            printTransactionDetails(transaction);

        } catch (Exception e) {
            System.out.println("Error al realizar la conversión: " + e.getMessage());
        }
    }

    private void showHistory() {
        System.out.println("\n=== HISTORIAL DE CONVERSIONES ===");
        transactionHistory.showTransactions();
    }

    private void printTransactionDetails(Transaction transaction) {
        System.out.println("\n=== DETALLES DE LA TRANSACCIÓN ===");
        System.out.println(transaction.toString());
    }
}

class ExchangeRateService {
    private static final String API_KEY = "1640b72d9e5ae06c83c1d295";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public double getExchangeRate(String baseCurrency, String targetCurrency) throws Exception {
        String urlStr = BASE_URL + API_KEY + "/latest/" + baseCurrency;

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONObject rates = jsonResponse.getJSONObject("conversion_rates");

        return rates.getDouble(targetCurrency);
    }
}

class Transaction {
    private double originalAmount;
    private String originalCurrency;
    private String targetCurrency;
    private double convertedAmount;
    private double exchangeRate;
    private LocalDateTime timestamp;

    public Transaction(double originalAmount, String originalCurrency,
                       String targetCurrency, double convertedAmount,
                       double exchangeRate) {
        this.originalAmount = originalAmount;
        this.originalCurrency = originalCurrency;
        this.targetCurrency = targetCurrency;
        this.convertedAmount = convertedAmount;
        this.exchangeRate = exchangeRate;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("""
            Fecha: %s
            Cantidad original: %.2f %s
            Cantidad convertida: %.2f %s
            Tasa de cambio: 1 %s = %.6f %s
            """,
                timestamp.format(formatter),
                originalAmount, originalCurrency,
                convertedAmount, targetCurrency,
                targetCurrency, exchangeRate, originalCurrency
        );
    }
}

class TransactionHistory {
    private List<Transaction> transactions;

    public TransactionHistory() {
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void showTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No hay transacciones registradas.");
            return;
        }

        for (int i = 0; i < transactions.size(); i++) {
            System.out.println("\nTransacción #" + (i + 1));
            System.out.println(transactions.get(i));
        }
    }
}