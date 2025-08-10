package com.example.conversordeunidades;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    Spinner spinnerEntrada, spinnerSaida;
    EditText editValor;
    Button btnConverter;
    TextView textResultado;

    String[] unidades = {"Centímetros", "Metros", "Quilômetros", "Milhas"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        spinnerEntrada = findViewById(R.id.spinnerFrom);
        spinnerSaida = findViewById(R.id.spinnerTo);
        editValor = findViewById(R.id.editTextValue);
        btnConverter = findViewById(R.id.buttonConvert);
        textResultado = findViewById(R.id.editTextValue);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEntrada.setAdapter(adapter);
        spinnerSaida.setAdapter(adapter);

        btnConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorStr = editValor.getText().toString();

                if (valorStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Digite um valor", Toast.LENGTH_SHORT).show();
                    return;
                }

                double valorOriginal = Double.parseDouble(valorStr);
                String unidadeOrigem = spinnerEntrada.getSelectedItem().toString();
                String unidadeDestino = spinnerSaida.getSelectedItem().toString();

                // Converte para metros (unidade base)
                double valorEmMetros = converterParaMetros(valorOriginal, unidadeOrigem);

                // Converte de metros para a unidade de destino
                double resultado = converterDeMetros(valorEmMetros, unidadeDestino);

                textResultado.setText(String.format("%.2f %s", resultado, unidadeDestino));
            }
        });
    }

    private double converterParaMetros(double valor, String unidade) {
        switch (unidade) {
            case "Centímetros":
                return valor / 100.0;
            case "Metros":
                return valor;
            case "Quilômetros":
                return valor * 1000.0;
            case "Milhas":
                return valor * 1609.34;
            default:
                return valor;
        }
    }

    private double converterDeMetros(double metros, String unidadeDestino) {
        switch (unidadeDestino) {
            case "Centímetros":
                return metros * 100.0;
            case "Metros":
                return metros;
            case "Quilômetros":
                return metros / 1000.0;
            case "Milhas":
                return metros / 1609.34;
            default:
                return metros;
        }
    }
}
