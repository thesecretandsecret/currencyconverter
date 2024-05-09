package com.example.currencyconverter; // Replace with your package name

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editTextFromAmount;
    private Spinner spinnerFromCurrency, spinnerToCurrency = findViewById(R.id.spinner_to_currency);
    private TextView textViewToAmount;
    private Button buttonConvert;

    // Map to store exchange rates (replace with actual API or data source)
    private final Map<String, Double> exchangeRates = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        editTextFromAmount = findViewById(R.id.edit_text_from_amount);
        spinnerFromCurrency = findViewById(R.id.spinner_from_currency);
        textViewToAmount = findViewById(R.id.text_view_to_amount);
        buttonConvert = findViewById(R.id.button_convert);

        // Set up currency options (replace with actual currencies)
        String[] currencies = new String[]{"USD", "EUR", "JPY", "GBP", "AUD"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        spinnerFromCurrency.setAdapter(adapter);
        spinnerToCurrency.setAdapter(adapter);

        // Initialize exchange rates (replace with actual values or API calls)
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.92);
        exchangeRates.put("JPY", 114.10);
        exchangeRates.put("GBP", 0.82);
        exchangeRates.put("AUD", 1.41);

        // Handle user input in "From Amount" EditText
        editTextFromAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                convertCurrency(); // Convert on any change in input
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed for this implementation
            }
        });

        // Handle selection changes in spinners
        spinnerFromCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertCurrency();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection case (optional)
            }
        });

        spinnerToCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertCurrency();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection case (optional)
            }
        });

        // Handle button click for conversion
        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency();
            }
        });
    }



    private void convertCurrency () {
        String fromCurrency = spinnerFromCurrency.getSelectedItem().toString();
        String toCurrency = spinnerToCurrency.getSelectedItem().toString();
        String fromAmountString = editTextFromAmount.getText().toString();

        // Input validation
        if (fromAmountString.isEmpty()) {
            Toast.makeText(this, "Please enter an amount to convert.", Toast.LENGTH_SHORT).show();
            return;
        }

        double fromAmount;
        try {
            fromAmount = Double.parseDouble(fromAmountString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input. Please enter a valid number.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get exchange rates
        double fromCurrencyRate = exchangeRates.get(fromCurrency);
        double toCurrencyRate = exchangeRates.get(toCurrency);

        // Convert currency
        double convertedAmount = (fromAmount / fromCurrencyRate) * toCurrencyRate;

        // Update TextView with converted amount
        textViewToAmount.setText(String.format("%.2f", convertedAmount) + " " + toCurrency);
    }
}
