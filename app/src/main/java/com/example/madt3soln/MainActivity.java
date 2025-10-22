package com.example.madt3soln;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private TextView screen;
    private double mem = 0.0;
    private String op = null;
    private boolean done = false;
    private final DecimalFormat df = new DecimalFormat("0.##########");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = findViewById(R.id.calculatorScreen);

        int[] nums = {R.id.n0,R.id.n1,R.id.n2,R.id.n3,R.id.n4,R.id.n5,R.id.n6,R.id.n7,R.id.n8,R.id.n9};
        View.OnClickListener onNum = v -> push(((Button)v).getText().toString());
        for (int id : nums) findViewById(id).setOnClickListener(onNum);

        findViewById(R.id.dot).setOnClickListener(v -> dot());
        findViewById(R.id.clearEverything).setOnClickListener(v -> clearAll());
        findViewById(R.id.backOne).setOnClickListener(v -> back());
        findViewById(R.id.signChange).setOnClickListener(v -> flip());
        findViewById(R.id.squareRoot).setOnClickListener(v -> root());

        findViewById(R.id.addition).setOnClickListener(v -> op("+"));
        findViewById(R.id.subtraction).setOnClickListener(v -> op("-"));
        findViewById(R.id.multiplication).setOnClickListener(v -> op("×"));
        findViewById(R.id.division).setOnClickListener(v -> op("÷"));
        findViewById(R.id.nr).setOnClickListener(v -> eq());
    }

    @SuppressLint("SetTextI18n")
    private void push(String d) {
        String cur = screen.getText().toString();
        if (done || cur.equals("0")) {
            screen.setText(d);
            done = false;
        } else {
            screen.setText(cur + d);
        }
    }

    @SuppressLint("SetTextI18n")
    private void dot() {
        String cur = screen.getText().toString();
        if (done) {
            screen.setText("0.");
            done = false;
            return;
        }
        if (!cur.contains(".")) {
            screen.setText(cur + ".");
        }
    }

    private void clearAll() {
        mem = 0.0;
        op = null;
        screen.setText("0");
        done = false;
    }

    private void back() {
        String cur = screen.getText().toString();
        if (done) {
            clearAll();
            return;
        }
        if (cur.length() > 1) screen.setText(cur.substring(0, cur.length() - 1));
        else screen.setText("0");
    }

    @SuppressLint("SetTextI18n")
    private void flip() {
        String cur = screen.getText().toString();
        if (cur.equals("0")) return;
        if (cur.startsWith("-")) screen.setText(cur.substring(1));
        else {
            screen.setText("-" + cur);
        }
    }

    private void root() {
        double v = parse();
        if (v < 0) {
            clearAll();
            return;
        }
        screen.setText(df.format(Math.sqrt(v)));
        done = true;
    }

    private void op(String o) {
        if (op != null && !done) eq();
        mem = parse();
        op = o;
        done = true;
    }

    private void eq() {
        if (op == null) {
            done = true;
            return;
        }
        double cur = parse();
        double res;
        switch (op) {
            case "+": res = mem + cur; break;
            case "-": res = mem - cur; break;
            case "×": res = mem * cur; break;
            case "÷":
                if (cur == 0.0) { clearAll(); return; }
                res = mem / cur; break;
            default: res = cur;
        }
        screen.setText(df.format(res));
        mem = res;
        done = true;
    }

    private double parse() {
        try { return Double.parseDouble(screen.getText().toString()); }
        catch (NumberFormatException e) { return 0.0; }
    }
}
