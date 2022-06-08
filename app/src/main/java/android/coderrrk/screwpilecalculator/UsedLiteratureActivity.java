package android.coderrrk.screwpilecalculator;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UsedLiteratureActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_used_literature);

        textView = findViewById(R.id.usedLiteratureTextView);

        textView.setText("\n" + "По расчету морозного пучения: " + "\n"+
                "1. СП 22.13330.2016 - Основания зданий и сооружений, п.5.5, п.6.8.6, прил.А." + "\n"+
                "2. СП 131.13330.2020 - Строительная климатология" + "\n"+
                "3. СП 24.13330.2021 - Свайные фундаменты, п.7.2.15" + "\n"+
                "3. СП 50-102-2003 - Проектирование и устройство свайных фундаментов, п.7.2.10" + "\n"+
                "4. Рекомендации по снижению касательных сил морозного выпучивания фундаментов с применением пластических смазок и кремнийорганических эмалей - 1980г.");

    }
}


