package com.sm.dhb;


import com.sm.dhb.StockService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StockController {

    @Autowired
    private StockService stockService;


    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Fetch market indices (SPY, QQQ, DIA) here
        return "dashboard";
    }

    @GetMapping("/searchStock")
    public String searchStock(@RequestParam("symbol") String symbol, Model model) {
        try {

            JSONObject stockData = stockService.getStockPrice(symbol);

            if (stockData != null) {
                // Extract data and add to the model
                model.addAttribute("symbol", stockData.getString("01. symbol"));
                model.addAttribute("price", stockData.getString("05. price"));
            } else {
                model.addAttribute("error", "No data found for the given stock symbol.");
            }
        } catch (Exception e) {
            // Display the error message to the user
            model.addAttribute("error", e.getMessage());
        }

        return "dashboard";
    }
}