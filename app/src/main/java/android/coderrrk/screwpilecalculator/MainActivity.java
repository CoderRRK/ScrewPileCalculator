package android.coderrrk.screwpilecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button FrostHeavingButton;
    Intent intentFrostHeaving;

    Button powerPileButton;

    Button usedLiteratureButton;
    Intent intentUsedLiterature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrostHeavingButton = findViewById(R.id.FrostHeavingButton);
        FrostHeavingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentFrostHeaving = new Intent(MainActivity.this, FrostHeavingActivity.class);
                startActivity(intentFrostHeaving);
            }
        });


        powerPileButton = findViewById(R.id.powerPileButton);
        powerPileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Извините, расчет находится в разработке", Toast.LENGTH_LONG).show();
            }
        });


        usedLiteratureButton = findViewById(R.id.usedLiteratureButton);
        usedLiteratureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentUsedLiterature = new Intent(MainActivity.this, UsedLiteratureActivity.class);
                startActivity(intentUsedLiterature);
            }
        });


    }
}