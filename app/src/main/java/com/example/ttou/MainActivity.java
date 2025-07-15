package com.example.ttou;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    EditText edOne;
    EditText edTwo;
    Button btOne;
    Button btTwo;
    TextView tvOne;
    private ActivityResultLauncher launcher;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edOne = findViewById(R.id.edOne);
        edTwo = findViewById(R.id.edTwo);
        btOne = findViewById(R.id.btOne);
        btTwo = findViewById(R.id.btTwo);
        tvOne = findViewById(R.id.tvOne);
        //
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedRate = sharedPref.getString("savedRate", "");
        edOne.setText(savedRate);
        tvOne.setVisibility(View.INVISIBLE);



        btOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rateStr = edOne.getText().toString();
                String inputStr = edTwo.getText().toString();
                if (!inputStr.isEmpty() && !rateStr.isEmpty()) {
                    try {
                        double amount = Double.parseDouble(inputStr);
                        double rate = Double.parseDouble(rateStr);
                        //
                        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("savedRate", rateStr);
                        Set<String> historySet = sharedPref.getStringSet("history", new HashSet<>());
                        historySet.add("金額: " + inputStr + "，匯率: " + rateStr);
                        editor.putStringSet("history", historySet);
                        editor.commit();
                        //
                        double value = amount / rate;
                        //value = (double) Math.round(value * 100) / 100;
                        String result = String.format("%.2f",value);

                        tvOne.setVisibility(View.VISIBLE);
                        tvOne.setText("您可兌換 " + result + "美金");
                    } catch (NumberFormatException e) {
                        tvOne.setText("請輸入有效的數字");
                    }
                } else {
                    tvOne.setText("請輸入金額與匯率");
                }
            }
        });
        btTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                launcher.launch(intent);

            }
        });

    }
}