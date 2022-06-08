package android.coderrrk.screwpilecalculator;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SupportViewFrostHeavingActivity extends AppCompatActivity {

    TextView textViewLiterature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_view);

        textViewLiterature = findViewById(R.id.supportTextView);

        textViewLiterature.setText("\n" + "1. Частичная или полная (на глубину промерзания) замена грунта на песок средней крупности (не является пучинистым) не менее чем на 0,2м вокруг сваи с его уплотнением" + "\n"+ "\n"+
                "2. Обмазка сваи на глубину промерзания составами КО-1112/КО-174/эпоксидными или аналогичными составами (для того чтобы свая не смерзалась с грунтом)" + "\n"+ "\n"+
                "3. Покрытие сваи на глубину промерзания составом БАМ-3/БАМ-4 и оборачивание в пленку ПВХ/ПЭ, закрепляемую вверху и внизу проволокой (пленка будет двигаться с грунтом по смазанному покрытию)" + "\n"+ "\n"+
                "4. Уплотнение грунта вблизи расположения сваи и устройство скрытой отмостки из эструдированного пенополистерола (целесообразно при отсуствии грунтовых вод)"+ "\n"+ "\n" +
                "5. В случае наличия слоев торфа на участке, не учитывая их как несущий слой (слабонесущая осадочная рыхлая порода), увеличивайте длину сваи");

    }
}