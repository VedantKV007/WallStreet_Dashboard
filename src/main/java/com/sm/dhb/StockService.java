package com.sm.dhb;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class StockService {

    @Value("${alphavantage.api.key}")
    private String apiKey;

    private final String BASE_URL = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE";


    public JSONObject getStockPrice(String symbol) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + apiKey;

        try {

            String result = restTemplate.getForObject(url, String.class);


            System.out.println("API Response: " + result);


            JSONObject jsonObject = new JSONObject(result);


            if (jsonObject.has("Global Quote")) {
                return jsonObject.getJSONObject("Global Quote");
            } else if (jsonObject.has("Note")) {

                throw new RuntimeException("API rate limit exceeded. Please try again later.");
            } else if (jsonObject.has("Error Message")) {

                throw new RuntimeException("Invalid stock symbol. Please check the symbol and try again.");
            } else {

                throw new RuntimeException("Unexpected response from API.");
            }
        } catch (Exception e) {
            // Handle exceptions and print the error
            System.out.println("Error fetching stock data: " + e.getMessage());
            throw new RuntimeException("Error fetching stock data: " + e.getMessage(), e);
        }
    }
}