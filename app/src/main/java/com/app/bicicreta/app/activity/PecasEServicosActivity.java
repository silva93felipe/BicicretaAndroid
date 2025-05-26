package com.app.bicicreta.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.app.bicicreta.R;
import com.app.bicicreta.app.fragment.PecaFragment;
import com.app.bicicreta.app.fragment.ServicoFragment;
import com.google.android.material.tabs.TabLayout;

public class PecasEServicosActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pecas_eservicos);
        iniciarComponentes();
    }

    private void createFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutPecasEServico,  fragment).commit();
    }

    private void iniciarComponentes(){
        frameLayout =  findViewById(R.id.frameLayoutPecasEServico);
        tabLayout = findViewById(R.id.tabLayout);
        createFragment(new PecaFragment(getApplicationContext()));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment fragment = null;
                switch (position){
                    case 0:
                        fragment = new PecaFragment(getApplicationContext());
                        break;
                    case 1:
                        fragment = new ServicoFragment(getApplicationContext());
                        break;
                    default:
                        break;
                }

                createFragment(fragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}