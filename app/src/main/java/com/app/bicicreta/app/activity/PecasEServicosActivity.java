package com.app.bicicreta.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.app.bicicreta.R;

public class PecasEServicosActivity extends AppCompatActivity {
    private Button buttonPeca, buttonServico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pecas_eservicos);
    }
}