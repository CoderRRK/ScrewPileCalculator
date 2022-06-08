package android.coderrrk.screwpilecalculator;

import static java.lang.Double.parseDouble;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;



public class FrostHeavingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    CheckBox checkBoxSoil1, checkBoxSoil2, checkBoxSoil3;
    EditText pileOuterDiameter;
    EditText pileDepth;
    EditText bladeDiameter;
    EditText loadOnPile;
    TextView result;
    TextView resultCalc;
    Spinner spinnerSoils, spinnerRegions;
    String spinnerSoilsData, spinnerRegionsData;
    double spinnerSoilsDataDouble, spinnerRegionsDataDouble;
    ArrayList<String> spinnerArrayListSoils;
    ArrayList<String> spinnerArrayListRegions;
    ArrayAdapter<String> spinnerAdapterSoils;
    ArrayAdapter<String> spinnerAdapterRegions;
    String checkBoxSoilData1, checkBoxSoilData2, checkBoxSoilData3;
    double checkBoxSoilDataDouble;
    double pileOuterDiameterDouble;
    double bladeDiameterDouble;
    double loadOnPileDouble;
    double pileDepthDouble;
    double PI = 3.14159265358979323;
    double frostHeaving;
    double frostHeavingBreaker;
    HashMap<String, Double> soilMap1;
    HashMap<String, Double> soilMap2;
    HashMap<String, Double> soilMap3;
    Button resultButton;
    HashMap<String, Double> RegionMap;
    HashMap<String, Double> SoilsMap;
    double Yс;
    double a1;
    double a2;
    double powerSoil;
    double clutch;
    double specificGravity;
    Button supportButton;
    Intent intentSupportView;
    TextView freezingDepth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frost_heaving);
        pileOuterDiameter = findViewById(R.id.pileOuterDiameter);
        pileDepth = findViewById(R.id.pileDepth);
        bladeDiameter = findViewById(R.id.bladeDiameter);
        loadOnPile = findViewById(R.id.loadOnPile);
        supportButton = findViewById(R.id.supportButton);
        SoilsMap1();
        SoilsMap2();
        SoilsMap3();
        SoilsHashMap();
        RegionsMap();
        createSpinnerRegions();
        createSpinnerSoils();
        checkBoxSoils();
        pileSurfaceArea();
        bladeDiameter();
        loadOnPile();
        resultCalculate();

        supportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentSupportView = new Intent(FrostHeavingActivity.this, SupportViewFrostHeavingActivity.class);
                startActivity(intentSupportView);
            }
        });
    }
//В новуй экран с пояснениями

    public void pileSurfaceArea() {

        pileOuterDiameter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        pileDepth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void bladeDiameter() {
        bladeDiameter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void loadOnPile() {

        loadOnPile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    public void resultCalculate(){
        resultButton = findViewById(R.id.resultButton);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinnerSoilsData = spinnerSoils.getSelectedItem().toString();
                spinnerSoilsDataDouble = SoilsMap.get(spinnerSoilsData);

                spinnerRegionsData = spinnerRegions.getSelectedItem().toString();
                spinnerRegionsDataDouble = (double) RegionMap.get(spinnerRegionsData);

                double soilCalc = spinnerSoilsDataDouble * spinnerRegionsDataDouble * 1.1;
                //Узнаем глубину промерзания


                double scale = Math.pow(10, 2);
                double soilCalcForTextView = Math.ceil(soilCalc * scale) / scale;
                freezingDepth = findViewById(R.id.freezingDepth);
                freezingDepth.setText("Расчетная глубина промерзания грунта: " + soilCalcForTextView + " м");
                //Отбрасываем лишние нули, сокращение до сотых

                String errorPileOuterDiameter = pileOuterDiameter.getText().toString();
                String errorPileDepth = pileDepth.getText().toString();
                String errorBladeDiameter = bladeDiameter.getText().toString();
                String errorLoadOnPile = loadOnPile.getText().toString();
                if (TextUtils.isEmpty(errorPileOuterDiameter)){
                    pileOuterDiameter.setError("Поле не может быть пустым!");
                    return;
                }else if (TextUtils.isEmpty(errorPileDepth)){
                    pileDepth.setError("Поле не может быть пустым!");
                    return;
                }else if (TextUtils.isEmpty(errorBladeDiameter)){
                    bladeDiameter.setError("Поле не может быть пустым!");
                    return;
                }else if(TextUtils.isEmpty(errorLoadOnPile)){
                    loadOnPile.setError("Поле не может быть пустым!");
                    return;
                }

                    //Проверка заполнения полей

                pileOuterDiameterDouble = parseDouble(pileOuterDiameter.getText().toString());
                pileDepthDouble = parseDouble(pileDepth.getText().toString());
                bladeDiameterDouble = parseDouble(bladeDiameter.getText().toString());
                loadOnPileDouble = parseDouble(loadOnPile.getText().toString());


                if (soilCalc <= 1.5 && checkBoxSoil1.isChecked()) {
                    checkBoxSoilDataDouble = soilMap1.get(checkBoxSoilData1);
                    Yс = 0.5;
                } else if (soilCalc > 1.5 && soilCalc < 2.51 && checkBoxSoil1.isChecked()) {
                    checkBoxSoilDataDouble = soilMap2.get(checkBoxSoilData1);
                    Yс = 0.5;
                    checkBoxSoilDataDouble = 90 + (soilCalc - 2.5) * ((110 - 90) / (1.5 - 2.5));
                } else if (soilCalc >= 2.51 && soilCalc < 3 && checkBoxSoil1.isChecked()) {
                    checkBoxSoilDataDouble = soilMap2.get(checkBoxSoilData1);
                    Yс = 0.5;
                    checkBoxSoilDataDouble = 70 + (soilCalc - 3) * ((90 - 70) / (2.51 - 3));
                } else if (3.0 <= soilCalc && checkBoxSoil1.isChecked()) {
                    checkBoxSoilDataDouble = soilMap3.get(checkBoxSoilData1);
                    Yс = 0.5;
                }


                if (soilCalc <= 1.5 && checkBoxSoil2.isChecked()) {
                    checkBoxSoilDataDouble = soilMap1.get(checkBoxSoilData2);
                    Yс = 0.6;
                } else if (soilCalc > 1.5 && soilCalc < 2.51 && checkBoxSoil2.isChecked()) {
                    checkBoxSoilDataDouble = soilMap2.get(checkBoxSoilData2);
                    Yс = 0.6;
                    checkBoxSoilDataDouble = 70 + (soilCalc - 2.5) * ((90 - 70) / (1.5 - 2.5));
                } else if (soilCalc >= 2.51 && soilCalc < 3 && checkBoxSoil2.isChecked()) {
                    checkBoxSoilDataDouble = soilMap2.get(checkBoxSoilData2);
                    Yс = 0.6;
                    checkBoxSoilDataDouble = 55 + (soilCalc - 3) * ((70 - 55) / (2.51 - 3));
                } else if (3.0 <= soilCalc && checkBoxSoil2.isChecked()) {
                    checkBoxSoilDataDouble = soilMap3.get(checkBoxSoilData2);
                    Yс = 0.6;
                }


                if (soilCalc <= 1.5 && checkBoxSoil3.isChecked()) {
                    checkBoxSoilDataDouble = soilMap1.get(checkBoxSoilData3);
                    Yс = 0.7;
                } else if (soilCalc > 1.5 && soilCalc < 2.51 && checkBoxSoil3.isChecked()) {
                    checkBoxSoilDataDouble = soilMap2.get(checkBoxSoilData3);
                    Yс = 0.7;
                    checkBoxSoilDataDouble = 55 + (soilCalc - 2.5) * ((70 - 55) / (1.5 - 2.5));
                } else if (soilCalc >= 2.51 && soilCalc < 3 && checkBoxSoil3.isChecked()) {
                    checkBoxSoilDataDouble = soilMap2.get(checkBoxSoilData3);
                    Yс = 0.7;
                    checkBoxSoilDataDouble = 40 + (soilCalc - 3) * ((55 - 40) / (2.51 - 3));
                } else if (3.0 <= soilCalc && checkBoxSoil3.isChecked()) {
                    checkBoxSoilDataDouble = soilMap3.get(checkBoxSoilData3);
                    Yс = 0.7;
                }

                //проверка выбранного чекбокса, интерполяция глубины промерзания, установка понимжающего коэффициента

                if (checkBoxSoil1.isChecked()) {

                    if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.45") && checkBoxSoil1.isChecked()) {
                        a1 = 29.5;
                        a2 = 16.5;
                        clutch = 19;
                        specificGravity = ((26.6-10)/(1+0.45));
                    } else if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.55") && checkBoxSoil1.isChecked()) {
                        a1 = 23.1;
                        a2 = 12.3;
                        clutch = 15;
                        specificGravity = ((26.6-10)/(1+0.55));
                    } else if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.65") && checkBoxSoil1.isChecked()) {
                        a1 = 18.0;
                        a2 = 9.2;
                        clutch = 13;
                        specificGravity = ((26.6-10)/(1+0.65));
                    } else if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.75") && checkBoxSoil1.isChecked()) {
                        a1 = 13.5;
                        a2 = 6.2;
                        clutch = 11;
                        specificGravity = ((26.6-10)/(1+0.75));
                    } else if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.85") && checkBoxSoil1.isChecked()) {
                        a1 = 10.1;
                        a2 = 4.5;
                        clutch = 9;
                        specificGravity = ((26.6-10)/(1+0.85));
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.45") && checkBoxSoil1.isChecked()) {
                        a1 = 0;
                        a2 = 0;
                        clutch = 0;
                        specificGravity = 0;
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.55") && checkBoxSoil1.isChecked()) {
                        a1 = 0;
                        a2 = 0;
                        clutch = 0;
                        specificGravity = 0;
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.65") && checkBoxSoil1.isChecked()) {
                        a1 = 11.1;
                        a2 = 5.0;
                        clutch = 25;
                        specificGravity = ((27-10)/(1+0.65));
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.75") && checkBoxSoil1.isChecked()) {
                        a1 = 10.1;
                        a2 = 4.5;
                        clutch = 20;
                        specificGravity = ((27-10)/(1+0.75));
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.85") && checkBoxSoil1.isChecked()) {
                        a1 = 9.4;
                        a2 = 3.8;
                        clutch = 16;
                        specificGravity = ((27-10)/(1+0.85));
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.95") && checkBoxSoil1.isChecked()) {
                        a1 = 8.1;
                        a2 = 3.1;
                        clutch = 14;
                        specificGravity = ((27-10)/(1+0.95));
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=0.55") && checkBoxSoil1.isChecked()) {
                        a1 = 0;
                        a2 = 0;
                        clutch = 0;
                        specificGravity = 0;
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=0.65") && checkBoxSoil1.isChecked()) {
                        a1 = 8.4;
                        a2 = 3.3;
                        clutch = 45;
                        specificGravity = ((27-10)/(1+0.65));
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=0.75") && checkBoxSoil1.isChecked()) {
                        a1 = 8.1;
                        a2 = 3.1;
                        clutch = 41;
                        specificGravity = ((27-10)/(1+0.75));
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=0.85") && checkBoxSoil1.isChecked()) {
                        a1 = 7.8;
                        a2 = 2.8;
                        clutch = 36;
                        specificGravity = ((27-10)/(1+0.85));
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=0.95") && checkBoxSoil1.isChecked()) {
                        a1 = 7.2;
                        a2 = 2.2;
                        clutch = 33;
                        specificGravity = ((27-10)/(1+0.95));
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=1.05") && checkBoxSoil1.isChecked()) {
                        a1 = 6.8;
                        a2 = 1.4;
                        clutch = 29;
                        specificGravity = ((27-10)/(1+1.05));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.45") && checkBoxSoil1.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 6;
                        specificGravity = ((26-10)/(1+0.45));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.55") && checkBoxSoil1.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 4;
                        specificGravity = ((26-10)/(1+0.55));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.65") && checkBoxSoil1.isChecked()) {
                        a1 = 48.4;
                        a2 = 31.0;
                        clutch = 2;
                        specificGravity = ((26-10)/(1+0.65));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.75") && checkBoxSoil1.isChecked()) {
                        a1 = 29.5;
                        a2 = 16.5;
                        clutch = 1;
                        specificGravity = ((26-10)/(1+0.75));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.45") && checkBoxSoil1.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 8;
                        specificGravity = ((26-10)/(1+0.45));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.55") && checkBoxSoil1.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 6;
                        specificGravity = ((26-10)/(1+0.55));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.65") && checkBoxSoil1.isChecked()) {
                        a1 = 38.0;
                        a2 = 22.5;
                        clutch = 4;
                        specificGravity = ((26-10)/(1+0.65));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.75") && checkBoxSoil1.isChecked()) {
                        a1 = 23.1;
                        a2 = 12.3;
                        clutch = 2;
                        specificGravity = ((26-10)/(1+0.75));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.45") && checkBoxSoil1.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 2;
                        specificGravity = ((26-10)/(1+0.45));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.55") && checkBoxSoil1.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 1;
                        specificGravity = ((26-10)/(1+0.55));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.65") && checkBoxSoil1.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 1;
                        specificGravity = ((26-10)/(1+0.65));
                    }

                    if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.45") ||
                            spinnerSoils.getSelectedItem().equals("Супеси, е=0.55") ||
                            spinnerSoils.getSelectedItem().equals("Супеси, е=0.65") ||
                            spinnerSoils.getSelectedItem().equals("Супеси, е=0.75") ||
                            spinnerSoils.getSelectedItem().equals("Супеси, е=0.85") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.65") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.75") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.85") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.95") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=0.65") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=0.75") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=0.85") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=0.95") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=1.05")) {

                        if (pileDepthDouble / 1000 <= 1) {
                            powerSoil = 12.0;
                        } else if (1 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 2) {
                            powerSoil = 17.0;
                        } else if (2 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 3) {
                            powerSoil = 20.0;
                        } else if (3 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 4) {
                            powerSoil = 22.0;
                        } else if (4 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 5) {
                            powerSoil = 24.0;
                        } else if (5 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 6) {
                            powerSoil = 25.0;
                        } else if (6 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 8) {
                            powerSoil = 26.0;
                        } else if (8 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 10) {
                            powerSoil = 27.0;
                        } else if (10 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 15) {
                            powerSoil = 28.0;
                        } else if (15 < pileDepthDouble / 1000) {
                            powerSoil = 30.0;
                        }
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.45") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=0.55") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.55")) {

                        if (pileDepthDouble / 1000 <= 1) {
                            powerSoil = 0.0;
                        } else if (1 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 2) {
                            powerSoil = 0.0;
                        } else if (2 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 3) {
                            powerSoil = 0.0;
                        } else if (3 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 4) {
                            powerSoil = 0.0;
                        } else if (4 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 5) {
                            powerSoil = 0.0;
                        } else if (5 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 6) {
                            powerSoil = 0.0;
                        } else if (6 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 8) {
                            powerSoil = 0.0;
                        } else if (8 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 10) {
                            powerSoil = 0.0;
                        } else if (10 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 15) {
                            powerSoil = 0.0;
                        } else if (15 < pileDepthDouble / 1000) {
                            powerSoil = 0.0;
                        }
                    } else if (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.45") ||
                            spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.55") ||
                            spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.65") ||
                            spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.75")) {
                        if (pileDepthDouble / 1000 <= 1) {
                            powerSoil = 23.0;
                        } else if (1 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 2) {
                            powerSoil = 30.0;
                        } else if (2 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 3) {
                            powerSoil = 35.0;
                        } else if (3 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 4) {
                            powerSoil = 38.0;
                        } else if (4 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 5) {
                            powerSoil = 40.0;
                        } else if (5 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 6) {
                            powerSoil = 42.0;
                        } else if (6 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 8) {
                            powerSoil = 42.0;
                        } else if (8 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 10) {
                            powerSoil = 44.0;
                        } else if (10 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 15) {
                            powerSoil = 46.0;
                        } else if (15 < pileDepthDouble / 1000) {
                            powerSoil = 51.0;
                        }
                    } else if (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.45") ||
                            spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.55") ||
                            spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.65") ||
                            spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.75")) {
                        if (pileDepthDouble / 1000 <= 1) {
                            powerSoil = 15.0;
                        } else if (1 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 2) {
                            powerSoil = 21.0;
                        } else if (2 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 3) {
                            powerSoil = 25.0;
                        } else if (3 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 4) {
                            powerSoil = 27.0;
                        } else if (4 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 5) {
                            powerSoil = 29.0;
                        } else if (5 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 6) {
                            powerSoil = 31.0;
                        } else if (6 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 8) {
                            powerSoil = 33.0;
                        } else if (8 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 10) {
                            powerSoil = 34.0;
                        } else if (10 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 15) {
                            powerSoil = 38.0;
                        } else if (15 < pileDepthDouble / 1000) {
                            powerSoil = 41.0;
                        }
                    } else if (spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.45") ||
                            spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.55") ||
                            spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.65")) {

                        if (pileDepthDouble / 1000 <= 1) {
                            powerSoil = 35.0;
                        } else if (1 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 2) {
                            powerSoil = 42.0;
                        } else if (2 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 3) {
                            powerSoil = 48.0;
                        } else if (3 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 4) {
                            powerSoil = 53.0;
                        } else if (4 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 5) {
                            powerSoil = 56.0;
                        } else if (5 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 6) {
                            powerSoil = 58.0;
                        } else if (6 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 8) {
                            powerSoil = 62.0;
                        } else if (8 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 10) {
                            powerSoil = 65.0;
                        } else if (10 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 15) {
                            powerSoil = 72.0;
                        } else if (15 < pileDepthDouble / 1000) {
                            powerSoil = 79.0;
                        }
                    }
                }
                    //Первый чекбокс


                 else if (checkBoxSoil2.isChecked()) {

                    if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.45") && checkBoxSoil2.isChecked()) {
                        a1 = 29.5;
                        a2 = 16.5;
                        clutch = 19;
                        specificGravity = ((26.6-10)/(1+0.45));
                    } else if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.55") && checkBoxSoil2.isChecked()) {
                        a1 = 23.1;
                        a2 = 12.3;
                        clutch = 15;
                        specificGravity = ((26.6-10)/(1+0.55));
                    } else if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.65") && checkBoxSoil2.isChecked()) {
                        a1 = 18.0;
                        a2 = 9.2;
                        clutch = 13;
                        specificGravity = ((26.6-10)/(1+0.65));
                    } else if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.75") && checkBoxSoil2.isChecked()) {
                        a1 = 13.5;
                        a2 = 6.2;
                        clutch = 11;
                        specificGravity = ((26.6-10)/(1+0.75));
                    } else if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.85") && checkBoxSoil2.isChecked()) {
                        a1 = 10.1;
                        a2 = 4.5;
                        clutch = 9;
                        specificGravity = ((26.6-10)/(1+0.85));
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.45") && checkBoxSoil2.isChecked()) {
                        a1 = 18.0;
                        a2 = 9.2;
                        clutch = 39;
                        specificGravity = ((27-10)/(1+0.45));
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.55") && checkBoxSoil2.isChecked()) {
                        a1 = 16.5;
                        a2 = 8.1;
                        clutch = 34;
                        specificGravity = ((27-10)/(1+0.55));
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.65") && checkBoxSoil2.isChecked()) {
                        a1 = 15.0;
                        a2 = 7.0;
                        clutch = 28;
                        specificGravity = ((27-10)/(1+0.65));
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.75") && checkBoxSoil2.isChecked()) {
                        a1 = 13.5;
                        a2 = 6.2;
                        clutch = 23;
                        specificGravity = ((27-10)/(1+0.75));
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.85") && checkBoxSoil2.isChecked()) {
                        a1 = 11.1;
                        a2 = 5.0;
                        clutch = 18;
                        specificGravity = ((27-10)/(1+0.85));
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.95") && checkBoxSoil2.isChecked()) {
                        a1 = 9.7;
                        a2 = 4.1;
                        clutch = 15;
                        specificGravity = ((27-10)/(1+0.95));
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=0.55") && checkBoxSoil2.isChecked()) {
                        a1 = 0;
                        a2 = 0;
                        clutch = 0;
                        specificGravity = 0;
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=0.65") && checkBoxSoil2.isChecked()) {
                        a1 = 10.1;
                        a2 = 4.5;
                        clutch = 57;
                        specificGravity = ((27-10)/(1+0.65));
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=0.75") && checkBoxSoil2.isChecked()) {
                        a1 = 9.7;
                        a2 = 4.1;
                        clutch = 50;
                        specificGravity = ((27-10)/(1+0.75));
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=0.85") && checkBoxSoil2.isChecked()) {
                        a1 = 9.4;
                        a2 = 3.8;
                        clutch = 43;
                        specificGravity = ((27-10)/(1+0.85));
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=0.95") && checkBoxSoil2.isChecked()) {
                        a1 = 8.1;
                        a2 = 3.1;
                        clutch = 37;
                        specificGravity = ((27-10)/(1+0.95));
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=1.05") && checkBoxSoil2.isChecked()) {
                        a1 = 6.6;
                        a2 = 1.8;
                        clutch = 32;
                        specificGravity = ((27-10)/(1+1.05));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.45") && checkBoxSoil2.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 6;
                        specificGravity = ((26-10)/(1+0.45));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.55") && checkBoxSoil2.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 4;
                        specificGravity = ((26-10)/(1+0.55));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.65") && checkBoxSoil2.isChecked()) {
                        a1 = 48.4;
                        a2 = 31.0;
                        clutch = 2;
                        specificGravity = ((26-10)/(1+0.65));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.75") && checkBoxSoil2.isChecked()) {
                        a1 = 29.5;
                        a2 = 16.5;
                        clutch = 1;
                        specificGravity = ((26-10)/(1+0.75));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.45") && checkBoxSoil2.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 8;
                        specificGravity = ((26-10)/(1+0.45));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.55") && checkBoxSoil2.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 6;
                        specificGravity = ((26-10)/(1+0.55));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.65") && checkBoxSoil2.isChecked()) {
                        a1 = 38.0;
                        a2 = 22.5;
                        clutch = 4;
                        specificGravity = ((26-10)/(1+0.65));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.75") && checkBoxSoil2.isChecked()) {
                        a1 = 23.1;
                        a2 = 12.3;
                        clutch = 2;
                        specificGravity = ((26-10)/(1+0.75));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.45") && checkBoxSoil2.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 2;
                        specificGravity = ((26-10)/(1+0.45));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.55") && checkBoxSoil2.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 1;
                        specificGravity = ((26-10)/(1+0.55));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.65") && checkBoxSoil2.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 1;
                        specificGravity = ((26-10)/(1+0.65));
                    }
                    if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.45") ||
                            spinnerSoils.getSelectedItem().equals("Супеси, е=0.55") ||
                            spinnerSoils.getSelectedItem().equals("Супеси, е=0.65") ||
                            spinnerSoils.getSelectedItem().equals("Супеси, е=0.75") ||
                            spinnerSoils.getSelectedItem().equals("Супеси, е=0.85") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.45") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.55") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.65") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.75") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.85") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.95") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=0.65") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=0.75") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=0.85") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=0.95") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=1.05")) {

                        if (pileDepthDouble / 1000 <= 1) {
                            powerSoil = 19.0;
                        } else if (1 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 2) {
                            powerSoil = 26.0;
                        } else if (2 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 3) {
                            powerSoil = 30.0;
                        } else if (3 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 4) {
                            powerSoil = 33.0;
                        } else if (4 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 5) {
                            powerSoil = 35.0;
                        } else if (5 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 6) {
                            powerSoil = 37.0;
                        } else if (6 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 8) {
                            powerSoil = 38.0;
                        } else if (8 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 10) {
                            powerSoil = 40.0;
                        } else if (10 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 15) {
                            powerSoil = 45.0;
                        } else if (15 < pileDepthDouble / 1000) {
                            powerSoil = 49.0;
                        }
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=0.55")) {

                        if (pileDepthDouble / 1000 <= 1) {
                            powerSoil = 0.0;
                        } else if (1 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 2) {
                            powerSoil = 0.0;
                        } else if (2 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 3) {
                            powerSoil = 0.0;
                        } else if (3 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 4) {
                            powerSoil = 0.0;
                        } else if (4 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 5) {
                            powerSoil = 0.0;
                        } else if (5 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 6) {
                            powerSoil = 0.0;
                        } else if (6 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 8) {
                            powerSoil = 0.0;
                        } else if (8 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 10) {
                            powerSoil = 0.0;
                        } else if (10 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 15) {
                            powerSoil = 0.0;
                        } else if (15 < pileDepthDouble / 1000) {
                            powerSoil = 0.0;
                        }

                    } else if (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.45") ||
                            spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.55") ||
                            spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.65") ||
                            spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.75")) {

                        if (pileDepthDouble / 1000 <= 1) {
                            powerSoil = 23.0;
                        } else if (1 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 2) {
                            powerSoil = 30.0;
                        } else if (2 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 3) {
                            powerSoil = 35.0;
                        } else if (3 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 4) {
                            powerSoil = 38.0;
                        } else if (4 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 5) {
                            powerSoil = 40.0;
                        } else if (5 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 6) {
                            powerSoil = 42.0;
                        } else if (6 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 8) {
                            powerSoil = 42.0;
                        } else if (8 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 10) {
                            powerSoil = 44.0;
                        } else if (10 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 15) {
                            powerSoil = 46.0;
                        } else if (15 < pileDepthDouble / 1000) {
                            powerSoil = 51.0;
                        }
                    } else if (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.45") ||
                            spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.55") ||
                            spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.65") ||
                            spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.75")) {

                        if (pileDepthDouble / 1000 <= 1) {
                            powerSoil = 15.0;
                        } else if (1 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 2) {
                            powerSoil = 21.0;
                        } else if (2 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 3) {
                            powerSoil = 25.0;
                        } else if (3 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 4) {
                            powerSoil = 27.0;
                        } else if (4 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 5) {
                            powerSoil = 29.0;
                        } else if (5 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 6) {
                            powerSoil = 31.0;
                        } else if (6 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 8) {
                            powerSoil = 33.0;
                        } else if (8 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 10) {
                            powerSoil = 34.0;
                        } else if (10 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 15) {
                            powerSoil = 38.0;
                        } else if (15 < pileDepthDouble / 1000) {
                            powerSoil = 41.0;
                        }
                    } else if (spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.45") ||
                            spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.55") ||
                            spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.65")) {

                        if (pileDepthDouble / 1000 <= 1) {
                            powerSoil = 35.0;
                        } else if (1 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 2) {
                            powerSoil = 42.0;
                        } else if (2 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 3) {
                            powerSoil = 48.0;
                        } else if (3 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 4) {
                            powerSoil = 53.0;
                        } else if (4 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 5) {
                            powerSoil = 56.0;
                        } else if (5 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 6) {
                            powerSoil = 58.0;
                        } else if (6 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 8) {
                            powerSoil = 62.0;
                        } else if (8 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 10) {
                            powerSoil = 65.0;
                        } else if (10 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 15) {
                            powerSoil = 72.0;
                        } else if (15 < pileDepthDouble / 1000) {
                            powerSoil = 79.0;
                        }
                    }
                }
                //Второй чекбокс

                else if (checkBoxSoil3.isChecked()) {

                    if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.45") && checkBoxSoil3.isChecked()) {
                        a1 = 38.0;
                        a2 = 22.5;
                        clutch = 21;
                        specificGravity = ((26.6-10)/(1+0.45));
                    } else if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.55") && checkBoxSoil3.isChecked()) {
                        a1 = 33.7;
                        a2 = 19.5;
                        clutch = 17;
                        specificGravity = ((26.6-10)/(1+0.55));
                    } else if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.65") && checkBoxSoil3.isChecked()) {
                        a1 = 26.3;
                        a2 = 14.4;
                        clutch = 15;
                        specificGravity = ((26.6-10)/(1+0.65));
                    } else if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.75") && checkBoxSoil3.isChecked()) {
                        a1 = 18.0;
                        a2 = 9.2;
                        clutch = 13;
                        specificGravity = ((26.6-10)/(1+0.75));
                    } else if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.85") && checkBoxSoil3.isChecked()) {
                        a1 = 0;
                        a2 = 0;
                        clutch = 0;
                        specificGravity = 0;
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.45") && checkBoxSoil3.isChecked()) {
                        a1 = 23.1;
                        a2 = 12.3;
                        clutch = 47;
                        specificGravity = ((27-10)/(1+0.45));
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.55") && checkBoxSoil3.isChecked()) {
                        a1 = 20.5;
                        a2 = 10.7;
                        clutch = 37;
                        specificGravity = ((27-10)/(1+0.55));
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.65") && checkBoxSoil3.isChecked()) {
                        a1 = 18.0;
                        a2 = 9.2;
                        clutch = 31;
                        specificGravity = ((27-10)/(1+0.65));
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.75") && checkBoxSoil3.isChecked()) {
                        a1 = 16.5;
                        a2 = 8.1;
                        clutch = 25;
                        specificGravity = ((27-10)/(1+0.75));
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.85") && checkBoxSoil3.isChecked()) {
                        a1 = 15.0;
                        a2 = 7.0;
                        clutch = 22;
                        specificGravity = ((27-10)/(1+0.85));
                    } else if (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.95") && checkBoxSoil3.isChecked()) {
                        a1 = 12.1;
                        a2 = 5.5;
                        clutch = 19;
                        specificGravity = ((27-10)/(1+0.95));
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=0.55") && checkBoxSoil3.isChecked()) {
                        a1 = 13.5;
                        a2 = 6.2;
                        clutch = 81;
                        specificGravity = ((27-10)/(1+0.55));
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=0.65") && checkBoxSoil3.isChecked()) {
                        a1 = 12.1;
                        a2 = 5.5;
                        clutch = 68;
                        specificGravity = ((27-10)/(1+0.65));
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=0.75") && checkBoxSoil3.isChecked()) {
                        a1 = 11.1;
                        a2 = 5.0;
                        clutch = 54;
                        specificGravity = ((27-10)/(1+0.75));
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=0.85") && checkBoxSoil3.isChecked()) {
                        a1 = 10.1;
                        a2 = 4.5;
                        clutch = 47;
                        specificGravity = ((27-10)/(1+0.85));
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=0.95") && checkBoxSoil3.isChecked()) {
                        a1 = 9.4;
                        a2 = 3.8;
                        clutch = 41;
                        specificGravity = ((27-10)/(1+0.95));
                    } else if (spinnerSoils.getSelectedItem().equals("Глины, е=1.05") && checkBoxSoil3.isChecked()) {
                        a1 = 8.1;
                        a2 = 3.1;
                        clutch = 36;
                        specificGravity = ((27-10)/(1+1.05));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.45") && checkBoxSoil3.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 6;
                        specificGravity = ((26-10)/(1+0.45));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.55") && checkBoxSoil3.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 4;
                        specificGravity = ((26-10)/(1+0.55));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.65") && checkBoxSoil3.isChecked()) {
                        a1 = 48.4;
                        a2 = 31.0;
                        clutch = 2;
                        specificGravity = ((26-10)/(1+0.65));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.75") && checkBoxSoil3.isChecked()) {
                        a1 = 29.5;
                        a2 = 16.5;
                        clutch = 1;
                        specificGravity = ((26-10)/(1+0.75));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.45") && checkBoxSoil3.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 8;
                        specificGravity = ((26-10)/(1+0.45));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.55") && checkBoxSoil3.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 6;
                        specificGravity = ((26-10)/(1+0.55));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.65") && checkBoxSoil3.isChecked()) {
                        a1 = 38.0;
                        a2 = 22.5;
                        clutch = 4;
                        specificGravity = ((26-10)/(1+0.65));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.75") && checkBoxSoil3.isChecked()) {
                        a1 = 23.1;
                        a2 = 12.3;
                        clutch = 2;
                        specificGravity = ((26-10)/(1+0.75));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.45") && checkBoxSoil3.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 2;
                        specificGravity = ((26-10)/(1+0.45));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.55") && checkBoxSoil3.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 1;
                        specificGravity = ((26-10)/(1+0.55));
                    } else if (spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.65") && checkBoxSoil3.isChecked()) {
                        a1 = 64.9;
                        a2 = 44.4;
                        clutch = 1;
                        specificGravity = ((26-10)/(1+0.65));
                    }

                    if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.45") ||
                            spinnerSoils.getSelectedItem().equals("Супеси, е=0.55") ||
                            spinnerSoils.getSelectedItem().equals("Супеси, е=0.65") ||
                            spinnerSoils.getSelectedItem().equals("Супеси, е=0.75") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.45") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.55") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.65") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.75") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.85") ||
                            spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.95") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=0.55") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=0.65") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=0.75") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=0.85") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=0.95") ||
                            spinnerSoils.getSelectedItem().equals("Глины, е=1.05")) {

                        if (pileDepthDouble / 1000 <= 1) {
                            powerSoil = 35.0;
                        } else if (1 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 2) {
                            powerSoil = 42.0;
                        } else if (2 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 3) {
                            powerSoil = 48.0;
                        } else if (3 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 4) {
                            powerSoil = 53.0;
                        } else if (4 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 5) {
                            powerSoil = 56.0;
                        } else if (5 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 6) {
                            powerSoil = 58.0;
                        } else if (6 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 8) {
                            powerSoil = 62.0;
                        } else if (8 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 10) {
                            powerSoil = 65.0;
                        } else if (10 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 15) {
                            powerSoil = 72.0;
                        } else if (15 < pileDepthDouble / 1000) {
                            powerSoil = 79.0;
                        }
                    } else if (spinnerSoils.getSelectedItem().equals("Супеси, е=0.85")) {

                        if (pileDepthDouble / 1000 <= 1) {
                            powerSoil = 0.0;
                        } else if (1 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 2) {
                            powerSoil = 0.0;
                        } else if (2 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 3) {
                            powerSoil = 0.0;
                        } else if (3 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 4) {
                            powerSoil = 0.0;
                        } else if (4 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 5) {
                            powerSoil = 0.0;
                        } else if (5 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 6) {
                            powerSoil = 0.0;
                        } else if (6 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 8) {
                            powerSoil = 0.0;
                        } else if (8 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 10) {
                            powerSoil = 0.0;
                        } else if (10 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 15) {
                            powerSoil = 0.0;
                        } else if (15 < pileDepthDouble / 1000) {
                            powerSoil = 0.0;
                        }

                    } else if (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.45") ||
                            spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.55") ||
                            spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.65") ||
                            spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.75")) {

                        if (pileDepthDouble / 1000 <= 1) {
                            powerSoil = 23.0;
                        } else if (1 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 2) {
                            powerSoil = 30.0;
                        } else if (2 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 3) {
                            powerSoil = 35.0;
                        } else if (3 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 4) {
                            powerSoil = 38.0;
                        } else if (4 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 5) {
                            powerSoil = 40.0;
                        } else if (5 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 6) {
                            powerSoil = 42.0;
                        } else if (6 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 8) {
                            powerSoil = 42.0;
                        } else if (8 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 10) {
                            powerSoil = 44.0;
                        } else if (10 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 15) {
                            powerSoil = 46.0;
                        } else if (15 < pileDepthDouble / 1000) {
                            powerSoil = 51.0;
                        }
                    } else if (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.45") ||
                            spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.55") ||
                            spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.65") ||
                            spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.75")) {

                        if (pileDepthDouble / 1000 <= 1) {
                            powerSoil = 15.0;
                        } else if (1 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 2) {
                            powerSoil = 21.0;
                        } else if (2 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 3) {
                            powerSoil = 25.0;
                        } else if (3 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 4) {
                            powerSoil = 27.0;
                        } else if (4 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 5) {
                            powerSoil = 29.0;
                        } else if (5 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 6) {
                            powerSoil = 31.0;
                        } else if (6 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 8) {
                            powerSoil = 33.0;
                        } else if (8 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 10) {
                            powerSoil = 34.0;
                        } else if (10 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 15) {
                            powerSoil = 38.0;
                        } else if (15 < pileDepthDouble / 1000) {
                            powerSoil = 41.0;
                        }
                    } else if (spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.45") ||
                            spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.55") ||
                            spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.65")) {

                        if (pileDepthDouble / 1000 <= 1) {
                            powerSoil = 35.0;
                        } else if (1 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 2) {
                            powerSoil = 42.0;
                        } else if (2 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 3) {
                            powerSoil = 48.0;
                        } else if (3 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 4) {
                            powerSoil = 53.0;
                        } else if (4 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 5) {
                            powerSoil = 56.0;
                        } else if (5 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 6) {
                            powerSoil = 58.0;
                        } else if (6 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 8) {
                            powerSoil = 62.0;
                        } else if (8 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 10) {
                            powerSoil = 65.0;
                        } else if (10 < pileDepthDouble / 1000 && pileDepthDouble / 1000 <= 15) {
                            powerSoil = 72.0;
                        } else if (15 < pileDepthDouble / 1000) {
                            powerSoil = 79.0;
                        }
                    }
                }

                //Третий чекбокс

                //Проверка соответствия выборанного региона с составом грунта, чекбоксами и их переменными


                if ((pileDepthDouble/1000 < bladeDiameterDouble/1000*6) &&
                                ((spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.45") || (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.55"))
                                || (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.65")) || (spinnerSoils.getSelectedItem().equals("Пески мелкие, е=0.75"))
                                || (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.45")) || (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.55"))
                                || (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.65")) || (spinnerSoils.getSelectedItem().equals("Пески пылеватые, е=0.75"))
                                || (spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.45")) || (spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.55"))
                                || (spinnerSoils.getSelectedItem().equals("Пески гравелистые, крупные, е=0.65"))))) {
                    Toast.makeText(FrostHeavingActivity.this, "Залегание лопасти от уровня планировки должно быть не менее 6 её диаметров, увеличте длину сваи", Toast.LENGTH_LONG).show();
                }else if ((pileDepthDouble/1000 < bladeDiameterDouble/1000*5) && (
                             (spinnerSoils.getSelectedItem().equals("Супеси, е=0.45")) || (spinnerSoils.getSelectedItem().equals("Супеси, е=0.55")) ||
                             (spinnerSoils.getSelectedItem().equals("Супеси, е=0.65") || (spinnerSoils.getSelectedItem().equals("Супеси, е=0.75")) ||
                             (spinnerSoils.getSelectedItem().equals("Супеси, е=0.85") || (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.45") ||
                             (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.55") || (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.65")) ||
                             (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.75") || (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.85") ||
                             (spinnerSoils.getSelectedItem().equals("Cуглинки, е=0.95") || (spinnerSoils.getSelectedItem().equals("Глины, е=0.55") ||
                             (spinnerSoils.getSelectedItem().equals("Глины, е=0.65") || (spinnerSoils.getSelectedItem().equals("Глины, е=0.75")) ||
                             (spinnerSoils.getSelectedItem().equals("Глины, е=0.85") || (spinnerSoils.getSelectedItem().equals("Глины, е=0.95") ||
                             (spinnerSoils.getSelectedItem().equals("Глины, е=1.05")
                                     )))))))))))))){
                    Toast.makeText(FrostHeavingActivity.this, "Залегание лопасти от уровня планировки должно быть не менее 5 её диаметров, увеличте длину сваи", Toast.LENGTH_LONG).show();
                }

                //Проверка соответствия залегиния лопасти от уровня планировки

                frostHeaving = checkBoxSoilDataDouble * 0.8 * pileOuterDiameterDouble / 1000 *
                        soilCalc * PI - (loadOnPileDouble / 100 * 0.9);
                //Расчет силы морозного пучения


                if (soilCalc + 0.15 < pileDepthDouble / 1000){
                frostHeavingBreaker = (Yс * (((a1 * clutch + a2 * specificGravity * (pileDepthDouble / 1000 - 0.15)) *
                        (bladeDiameterDouble / 1000 / 2 * bladeDiameterDouble / 1000 / 2 * PI -
                                pileOuterDiameterDouble / 1000 / 2 * pileOuterDiameterDouble / 1000 / 2 * PI))
                        + pileOuterDiameterDouble / 1000 * PI * powerSoil * (pileDepthDouble / 1000 - soilCalc - 0.14)))/1.1;
                } else{
                    Toast.makeText(FrostHeavingActivity.this, "Лопасть сваи должна быть ниже глубины промерзания!", Toast.LENGTH_LONG).show();
                }


                Log.d("Yс", "" + Yс);
                Log.d("a1", "" + a1);
                Log.d("clutch", "" + clutch);
                Log.d("a2", "" + a2);
                Log.d("a2", "" + specificGravity);
                Log.d("pileDepthDouble", "" + pileDepthDouble);
                Log.d("bladeDiameterDouble", "" + bladeDiameterDouble);
                Log.d("pileOuterDiameterDouble", "" + pileOuterDiameterDouble);
                Log.d("powerSoil", "" + powerSoil);
                Log.d("soilCalc", "" + soilCalc);


                //Расчет противидействующей морозному пучению силы

                if (frostHeaving <=0.0 || frostHeavingBreaker <=0.0){
                    Toast.makeText(FrostHeavingActivity.this, "Пожалуйста выберете одну из характеристик грунта!", Toast.LENGTH_LONG).show();

                }

                scale = Math.pow(10,2);
                double frostHeavingBreakerDouble = Math.ceil(frostHeavingBreaker*scale)/scale;
                double frostHeavingDouble = Math.ceil(frostHeaving*scale)/scale;
                //Обрезка лишних нулей, сокращение до сотых долей
                result = findViewById(R.id.result);
                resultCalc = findViewById(R.id.resultCalc);
            if (frostHeavingBreakerDouble > frostHeavingDouble) {
                resultCalc.setText("" + frostHeavingBreakerDouble + " кПа" + "  >  " + frostHeavingDouble + " кПа");
                result.setText("Проверка выполнена, свая подходит!");
            } else {
                resultCalc.setText("" + frostHeavingBreakerDouble + " кПа" + "  <  " + frostHeavingDouble + " кПа");
                result.setText("Cвая не подходит!");
                Toast errorToast = Toast.makeText(FrostHeavingActivity.this, "Пожалуйста увеличьте длину сваи!", Toast.LENGTH_LONG);
                errorToast.show();
            }}}); }

// Расчет результата по нажатию кнопки

    public void checkBoxSoils() {
        checkBoxSoil1 = findViewById(R.id.checkBox1);
        checkBoxSoil2 = findViewById(R.id.checkBox2);
        checkBoxSoil3 = findViewById(R.id.checkBox3);

        checkBoxSoil1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (checkBoxSoil1.isChecked()){
                checkBoxSoil1.setEnabled(true);
                checkBoxSoil2.setEnabled(false);
                checkBoxSoil3.setEnabled(false);
                checkBoxSoil2.setChecked(false);
                checkBoxSoil3.setChecked(false);
                checkBoxSoilData1 = checkBoxSoil1.getText().toString();
            } else {
                checkBoxSoil1.setEnabled(false);
                checkBoxSoil2.setEnabled(true);
                checkBoxSoil3.setEnabled(true);
                checkBoxSoil1.setChecked(false);
            }}});

        checkBoxSoil2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxSoil2.isChecked()){
                    checkBoxSoil2.setEnabled(true);
                    checkBoxSoil1.setEnabled(false);
                    checkBoxSoil3.setEnabled(false);
                    checkBoxSoil1.setChecked(false);
                    checkBoxSoil3.setChecked(false);
                    checkBoxSoilData2 = checkBoxSoil2.getText().toString();
                } else {
                    checkBoxSoil2.setEnabled(false);
                    checkBoxSoil1.setEnabled(true);
                    checkBoxSoil3.setEnabled(true);
                    checkBoxSoil2.setChecked(false);
                }}});

        checkBoxSoil3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxSoil3.isChecked()){
                    checkBoxSoil3.setEnabled(true);
                    checkBoxSoil1.setEnabled(false);
                    checkBoxSoil2.setEnabled(false);
                    checkBoxSoil2.setChecked(false);
                    checkBoxSoil1.setChecked(false);
                    checkBoxSoilData3 = checkBoxSoil3.getText().toString();
                } else {
                    checkBoxSoil3.setEnabled(false);
                    checkBoxSoil1.setEnabled(true);
                    checkBoxSoil2.setEnabled(true);
                    checkBoxSoil3.setChecked(false);
                }}});

    }
//Проверка задействованного чекбокса

    public void SoilsMap1() {
        soilMap1 = new HashMap<String, Double>();
        soilMap1.put("Супеси, суглинки и глины при показателе текучести IL > 0,5, крупнообломочные грунты с глинистым заполнителем, пески мелкие и пылеватые при показателе дисперсности D > 5 и степени влажности Sr > 0,95", 110.00);
        soilMap1.put("Супеси, суглинки и глины при 0,5 >= IL > 0,25, крупнообломочные грунты с глинистым заполнителем, пески мелкие и пылеватые при D > 1 и степени влажности 0,95 >= Sr > 0,8", 90.00);
        soilMap1.put("Супеси, суглинки и глины при 0,25 >= IL, крупнообломочные грунты с глинистым заполнителем, пески мелкие и пылеватые при D > 1 и степени влажности 0,8 >= Sr > 0,6", 70.00); }
    public void SoilsMap2() {
        soilMap2 = new HashMap<>();
        soilMap2.put("Супеси, суглинки и глины при показателе текучести IL > 0,5, крупнообломочные грунты с глинистым заполнителем, пески мелкие и пылеватые при показателе дисперсности D > 5 и степени влажности Sr > 0,95", 90.00);
        soilMap2.put("Супеси, суглинки и глины при 0,5 >= IL > 0,25, крупнообломочные грунты с глинистым заполнителем, пески мелкие и пылеватые при D > 1 и степени влажности 0,95 >= Sr > 0,8", 70.00);
        soilMap2.put("Супеси, суглинки и глины при 0,25 >= IL, крупнообломочные грунты с глинистым заполнителем, пески мелкие и пылеватые при D > 1 и степени влажности 0,8 >= Sr > 0,6", 55.00); }
    public void SoilsMap3() {
        soilMap3 = new HashMap<>();
        soilMap3.put("Супеси, суглинки и глины при показателе текучести IL > 0,5, крупнообломочные грунты с глинистым заполнителем, пески мелкие и пылеватые при показателе дисперсности D > 5 и степени влажности Sr > 0,95", 70.00);
        soilMap3.put("Супеси, суглинки и глины при 0,5 >= IL > 0,25, крупнообломочные грунты с глинистым заполнителем, пески мелкие и пылеватые при D > 1 и степени влажности 0,95 >= Sr > 0,8", 55.00);
        soilMap3.put("Супеси, суглинки и глины при 0,25 >= IL, крупнообломочные грунты с глинистым заполнителем, пески мелкие и пылеватые при D > 1 и степени влажности 0,8 >= Sr > 0,6", 40.00);
    }


    void createSpinnerSoils() {
        spinnerSoils = findViewById(R.id.spinnerSoils);
        spinnerSoils.setOnItemSelectedListener(this);
        spinnerArrayListSoils = new ArrayList<>();
        spinnerArrayListSoils.add("Супеси, е=0.45");
        spinnerArrayListSoils.add("Супеси, е=0.55");
        spinnerArrayListSoils.add("Супеси, е=0.65");
        spinnerArrayListSoils.add("Супеси, е=0.75");
        spinnerArrayListSoils.add("Супеси, е=0.85");
        spinnerArrayListSoils.add("Cуглинки, е=0.45");
        spinnerArrayListSoils.add("Cуглинки, е=0.55");
        spinnerArrayListSoils.add("Cуглинки, е=0.65");
        spinnerArrayListSoils.add("Cуглинки, е=0.75");
        spinnerArrayListSoils.add("Cуглинки, е=0.85");
        spinnerArrayListSoils.add("Cуглинки, е=0.95");
        spinnerArrayListSoils.add("Глины, е=0.55");
        spinnerArrayListSoils.add("Глины, е=0.65");
        spinnerArrayListSoils.add("Глины, е=0.75");
        spinnerArrayListSoils.add("Глины, е=0.85");
        spinnerArrayListSoils.add("Глины, е=0.95");
        spinnerArrayListSoils.add("Глины, е=1.05");
        spinnerArrayListSoils.add("Пески мелкие, е=0.45");
        spinnerArrayListSoils.add("Пески мелкие, е=0.55");
        spinnerArrayListSoils.add("Пески мелкие, е=0.65");
        spinnerArrayListSoils.add("Пески мелкие, е=0.75");
        spinnerArrayListSoils.add("Пески пылеватые, е=0.45");
        spinnerArrayListSoils.add("Пески пылеватые, е=0.55");
        spinnerArrayListSoils.add("Пески пылеватые, е=0.65");
        spinnerArrayListSoils.add("Пески пылеватые, е=0.75");
        spinnerArrayListSoils.add("Пески гравелистые, крупные, е=0.45");
        spinnerArrayListSoils.add("Пески гравелистые, крупные, е=0.55");
        spinnerArrayListSoils.add("Пески гравелистые, крупные, е=0.65");
        spinnerAdapterSoils = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArrayListSoils);
        spinnerAdapterSoils.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSoils.setAdapter(spinnerAdapterSoils);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    void SoilsHashMap(){
        SoilsMap = new HashMap<String, Double>();
        SoilsMap.put("Супеси, е=0.45", 0.28);
        SoilsMap.put("Супеси, е=0.55", 0.28);
        SoilsMap.put("Супеси, е=0.65", 0.28);
        SoilsMap.put("Супеси, е=0.75", 0.28);
        SoilsMap.put("Супеси, е=0.85", 0.28);
        SoilsMap.put("Cуглинки, е=0.45", 0.23);
        SoilsMap.put("Cуглинки, е=0.55", 0.23);
        SoilsMap.put("Cуглинки, е=0.65", 0.23);
        SoilsMap.put("Cуглинки, е=0.75", 0.23);
        SoilsMap.put("Cуглинки, е=0.85", 0.23);
        SoilsMap.put("Cуглинки, е=0.95", 0.23);
        SoilsMap.put("Глины, е=0.55", 0.23);
        SoilsMap.put("Глины, е=0.65", 0.23);
        SoilsMap.put("Глины, е=0.75", 0.23);
        SoilsMap.put("Глины, е=0.85", 0.23);
        SoilsMap.put("Глины, е=0.95", 0.23);
        SoilsMap.put("Глины, е=1.05", 0.23);
        SoilsMap.put("Пески мелкие, е=0.45", 0.28);
        SoilsMap.put("Пески мелкие, е=0.55", 0.28);
        SoilsMap.put("Пески мелкие, е=0.65", 0.28);
        SoilsMap.put("Пески мелкие, е=0.75", 0.28);
        SoilsMap.put("Пески пылеватые, е=0.45", 0.28);
        SoilsMap.put("Пески пылеватые, е=0.55", 0.28);
        SoilsMap.put("Пески пылеватые, е=0.65", 0.28);
        SoilsMap.put("Пески пылеватые, е=0.75", 0.28);
        SoilsMap.put("Пески гравелистые, крупные, е=0.45", 0.30);
        SoilsMap.put("Пески гравелистые, крупные, е=0.55", 0.30);
        SoilsMap.put("Пески гравелистые, крупные, е=0.65", 0.30);

    }

    void createSpinnerRegions(){
        spinnerRegions = findViewById(R.id.spinnerRegions);
        spinnerRegions.setOnItemSelectedListener(this);
        spinnerArrayListRegions = new ArrayList<String>();
        spinnerArrayListRegions.add("Абакан");
        spinnerArrayListRegions.add("Агата");
        spinnerArrayListRegions.add("Агзу");
        spinnerArrayListRegions.add("Агинское");
        spinnerArrayListRegions.add("Ай-Петри");
        spinnerArrayListRegions.add("Акша");
        spinnerArrayListRegions.add("Алдан");
        spinnerArrayListRegions.add("Алейск");
        spinnerArrayListRegions.add("Александров Гай");
        spinnerArrayListRegions.add("Александровский Завод");
        spinnerArrayListRegions.add("Александровское");
        spinnerArrayListRegions.add("Александровск-Сахалинский");
        spinnerArrayListRegions.add("Алыгджер");
        spinnerArrayListRegions.add("Амга");
        spinnerArrayListRegions.add("Анадырь");
        spinnerArrayListRegions.add("Анучино");
        spinnerArrayListRegions.add("Апука");
        spinnerArrayListRegions.add("Арзамас");
        spinnerArrayListRegions.add("Арзгир");
        spinnerArrayListRegions.add("Архангельск");
        spinnerArrayListRegions.add("Архара");
        spinnerArrayListRegions.add("Астраханка");
        spinnerArrayListRegions.add("Астрахань");
        spinnerArrayListRegions.add("Ачинск");
        spinnerArrayListRegions.add("Аян");
        spinnerArrayListRegions.add("Бабаево");
        spinnerArrayListRegions.add("Бабушкин");
        spinnerArrayListRegions.add("Багдарин");
        spinnerArrayListRegions.add("Байдуков");
        spinnerArrayListRegions.add("Байкальск");
        spinnerArrayListRegions.add("Байкит");
        spinnerArrayListRegions.add("Балашов");
        spinnerArrayListRegions.add("Барабинск");
        spinnerArrayListRegions.add("Баргузин");
        spinnerArrayListRegions.add("Барнаул");
        spinnerArrayListRegions.add("Батамай");
        spinnerArrayListRegions.add("Бежецк");
        spinnerArrayListRegions.add("Белгород");
        spinnerArrayListRegions.add("Белогорск");
        spinnerArrayListRegions.add("Белорецк");
        spinnerArrayListRegions.add("Бердигястях");
        spinnerArrayListRegions.add("Березово");
        spinnerArrayListRegions.add("Бийск");
        spinnerArrayListRegions.add("Бикин");
        spinnerArrayListRegions.add("Биробиджан");
        spinnerArrayListRegions.add("Бисер");
        spinnerArrayListRegions.add("Благовещенск");
        spinnerArrayListRegions.add("Богополь");
        spinnerArrayListRegions.add("Боготол");
        spinnerArrayListRegions.add("Богучаны");
        spinnerArrayListRegions.add("Бодайбо");
        spinnerArrayListRegions.add("Болотное");
        spinnerArrayListRegions.add("Большерецк");
        spinnerArrayListRegions.add("Бомнак");
        spinnerArrayListRegions.add("Борзя");
        spinnerArrayListRegions.add("Боровичи");
        spinnerArrayListRegions.add("Братолюбовка");
        spinnerArrayListRegions.add("Братск");
        spinnerArrayListRegions.add("Брохово");
        spinnerArrayListRegions.add("Брянск");
        spinnerArrayListRegions.add("Бугульма");
        spinnerArrayListRegions.add("Буяга");
        spinnerArrayListRegions.add("Бысса");
        spinnerArrayListRegions.add("Вайда-Губа");
        spinnerArrayListRegions.add("Ванавара");
        spinnerArrayListRegions.add("Варандей");
        spinnerArrayListRegions.add("Великие Луки");
        spinnerArrayListRegions.add("Великий Новгород");
        spinnerArrayListRegions.add("Вельмо");
        spinnerArrayListRegions.add("Вендинга");
        spinnerArrayListRegions.add("Верхнеимбатск");
        spinnerArrayListRegions.add("Верхне-Марково");
        spinnerArrayListRegions.add("Верхнеуральск");
        spinnerArrayListRegions.add("Верхний Баскунчак");
        spinnerArrayListRegions.add("Верхняя Гутара");
        spinnerArrayListRegions.add("Верхотурье");
        spinnerArrayListRegions.add("Верхоянск");
        spinnerArrayListRegions.add("Вилюйск");
        spinnerArrayListRegions.add("Витим");
        spinnerArrayListRegions.add("Владивосток");
        spinnerArrayListRegions.add("Владикавказ");
        spinnerArrayListRegions.add("Владимир");
        spinnerArrayListRegions.add("Волгоград");
        spinnerArrayListRegions.add("Вологда");
        spinnerArrayListRegions.add("Волочанка");
        spinnerArrayListRegions.add("Воркута");
        spinnerArrayListRegions.add("Воронеж");
        spinnerArrayListRegions.add("Выборг");
        spinnerArrayListRegions.add("Выкса");
        spinnerArrayListRegions.add("Вытегра");
        spinnerArrayListRegions.add("Вяземский");
        spinnerArrayListRegions.add("Вязьма");
        spinnerArrayListRegions.add("Гвасюги");
        spinnerArrayListRegions.add("Гигант");
        spinnerArrayListRegions.add("Глазов");
        spinnerArrayListRegions.add("Грозный");
        spinnerArrayListRegions.add("Дальнереченск");
        spinnerArrayListRegions.add("Дарасун");
        spinnerArrayListRegions.add("Демьянское");
        spinnerArrayListRegions.add("Джалинда");
        spinnerArrayListRegions.add("Джаорэ");
        spinnerArrayListRegions.add("Джарджан");
        spinnerArrayListRegions.add("Джикимда");
        spinnerArrayListRegions.add("Диксон");
        spinnerArrayListRegions.add("Дмитров");
        spinnerArrayListRegions.add("Долинск");
        spinnerArrayListRegions.add("Дуван");
        spinnerArrayListRegions.add("Дудинка");
        spinnerArrayListRegions.add("Екатеринбург");
        spinnerArrayListRegions.add("Екатерино-Никольское");
        spinnerArrayListRegions.add("Елабуга");
        spinnerArrayListRegions.add("Емецк");
        spinnerArrayListRegions.add("Енисейск");
        spinnerArrayListRegions.add("Ербогачен");
        spinnerArrayListRegions.add("Ерофей Павлович");
        spinnerArrayListRegions.add("Ершово");
        spinnerArrayListRegions.add("Жигалово");
        spinnerArrayListRegions.add("Жиганск");
        spinnerArrayListRegions.add("Завитинск");
        spinnerArrayListRegions.add("Земетчино");
        spinnerArrayListRegions.add("Зея");
        spinnerArrayListRegions.add("Зима");
        spinnerArrayListRegions.add("Змеиногорск");
        spinnerArrayListRegions.add("Зырянка");
        spinnerArrayListRegions.add("Иваново");
        spinnerArrayListRegions.add("Ивдель");
        spinnerArrayListRegions.add("Игарка");
        spinnerArrayListRegions.add("Ижевск");
        spinnerArrayListRegions.add("Ика");
        spinnerArrayListRegions.add("Им. Полины Осипенко");
        spinnerArrayListRegions.add("Индига");
        spinnerArrayListRegions.add("Иркутск");
        spinnerArrayListRegions.add("Исиль-Куль");
        spinnerArrayListRegions.add("Исить");
        spinnerArrayListRegions.add("Ича");
        spinnerArrayListRegions.add("Иэма");
        spinnerArrayListRegions.add("Йошкар-Ола");
        spinnerArrayListRegions.add("Казань");
        spinnerArrayListRegions.add("Калакан");
        spinnerArrayListRegions.add("Калевала");
        spinnerArrayListRegions.add("Калининград");
        spinnerArrayListRegions.add("Калуга");
        spinnerArrayListRegions.add("Каменск-Уральский");
        spinnerArrayListRegions.add("Камышин");
        spinnerArrayListRegions.add("Кандалакша");
        spinnerArrayListRegions.add("Канин Нос");
        spinnerArrayListRegions.add("Канск");
        spinnerArrayListRegions.add("Карасук");
        spinnerArrayListRegions.add("Каргополь");
        spinnerArrayListRegions.add("Катанда");
        spinnerArrayListRegions.add("Кашира");
        spinnerArrayListRegions.add("Кемерово");
        spinnerArrayListRegions.add("Кемь");
        spinnerArrayListRegions.add("Кильмезь");
        spinnerArrayListRegions.add("Кинешма");
        spinnerArrayListRegions.add("Киренск");
        spinnerArrayListRegions.add("Киров");
        spinnerArrayListRegions.add("Кировский");
        spinnerArrayListRegions.add("Киселевск");
        spinnerArrayListRegions.add("Кисловодск");
        spinnerArrayListRegions.add("Клепинино");
        spinnerArrayListRegions.add("Ключи");
        spinnerArrayListRegions.add("Ковдор");
        spinnerArrayListRegions.add("Козыревск");
        spinnerArrayListRegions.add("Койнас");
        spinnerArrayListRegions.add("Колпашево");
        spinnerArrayListRegions.add("Комсомольск-на-Амуре");
        spinnerArrayListRegions.add("Кондинское");
        spinnerArrayListRegions.add("Кондома");
        spinnerArrayListRegions.add("Корсаков");
        spinnerArrayListRegions.add("Корф");
        spinnerArrayListRegions.add("Кострома");
        spinnerArrayListRegions.add("Котельниково");
        spinnerArrayListRegions.add("Коткино");
        spinnerArrayListRegions.add("Котлас");
        spinnerArrayListRegions.add("Кочки");
        spinnerArrayListRegions.add("Кош-Агач");
        spinnerArrayListRegions.add("Краснощелье");
        spinnerArrayListRegions.add("Красноярск");
        spinnerArrayListRegions.add("Красный Чикой");
        spinnerArrayListRegions.add("Красный Яр");
        spinnerArrayListRegions.add("Крест-Хальджай");
        spinnerArrayListRegions.add("Кроноки");
        spinnerArrayListRegions.add("Кувандык");
        spinnerArrayListRegions.add("Купино");
        spinnerArrayListRegions.add("Курган");
        spinnerArrayListRegions.add("Курильск");
        spinnerArrayListRegions.add("Курск");
        spinnerArrayListRegions.add("Кызыл");
        spinnerArrayListRegions.add("Кыштовка");
        spinnerArrayListRegions.add("Кюсюр");
        spinnerArrayListRegions.add("Кяхта");
        spinnerArrayListRegions.add("Ленск");
        spinnerArrayListRegions.add("Леуши");
        spinnerArrayListRegions.add("Липецк");
        spinnerArrayListRegions.add("Ловозеро");
        spinnerArrayListRegions.add("Лопатка, мыс");
        spinnerArrayListRegions.add("Магадан");
        spinnerArrayListRegions.add("Магас");
        spinnerArrayListRegions.add("Майкоп");
        spinnerArrayListRegions.add("Макаров");
        spinnerArrayListRegions.add("Мама");
        spinnerArrayListRegions.add("Маргаритово");
        spinnerArrayListRegions.add("Мариинск");
        spinnerArrayListRegions.add("Марково");
        spinnerArrayListRegions.add("Марресаля");
        spinnerArrayListRegions.add("Мезень");
        spinnerArrayListRegions.add("Мелеуз");
        spinnerArrayListRegions.add("Мельничное");
        spinnerArrayListRegions.add("Миллерово");
        spinnerArrayListRegions.add("Мильково");
        spinnerArrayListRegions.add("Минеральные Воды");
        spinnerArrayListRegions.add("Минусинск");
        spinnerArrayListRegions.add("Мирный");
        spinnerArrayListRegions.add("Могоча");
        spinnerArrayListRegions.add("Можайск");
        spinnerArrayListRegions.add("Монды");
        spinnerArrayListRegions.add("Мончегорск");
        spinnerArrayListRegions.add("Москва");
        spinnerArrayListRegions.add("Мурманск");
        spinnerArrayListRegions.add("Муром");
        spinnerArrayListRegions.add("Нагорный");
        spinnerArrayListRegions.add("Нагорское");
        spinnerArrayListRegions.add("Надым");
        spinnerArrayListRegions.add("Наканно");
        spinnerArrayListRegions.add("Нальчик");
        spinnerArrayListRegions.add("Наро-Фоминск");
        spinnerArrayListRegions.add("Нарьян-Мар");
        spinnerArrayListRegions.add("Начики");
        spinnerArrayListRegions.add("Невельск");
        spinnerArrayListRegions.add("Невинномысск");
        spinnerArrayListRegions.add("Непа");
        spinnerArrayListRegions.add("Нера");
        spinnerArrayListRegions.add("Нерчинск");
        spinnerArrayListRegions.add("Нерчинский Завод");
        spinnerArrayListRegions.add("Ниванкюль");
        spinnerArrayListRegions.add("Нижнеангарск");
        spinnerArrayListRegions.add("Нижнетамбовское");
        spinnerArrayListRegions.add("Нижний Новгород");
        spinnerArrayListRegions.add("Николаевск-на-Амуре");
        spinnerArrayListRegions.add("Никольск");
        spinnerArrayListRegions.add("Новая Ладога");
        spinnerArrayListRegions.add("Новоаннинский");
        spinnerArrayListRegions.add("Новомосковский АО");
        spinnerArrayListRegions.add("Новосибирск");
        spinnerArrayListRegions.add("Ноглики");
        spinnerArrayListRegions.add("Ножовка");
        spinnerArrayListRegions.add("Норск");
        spinnerArrayListRegions.add("Нюрба");
        spinnerArrayListRegions.add("Нязепетровск");
        spinnerArrayListRegions.add("о. Беринга");
        spinnerArrayListRegions.add("о. Сосновец");
        spinnerArrayListRegions.add("Облучье");
        spinnerArrayListRegions.add("Объячево");
        spinnerArrayListRegions.add("Оймякон");
        spinnerArrayListRegions.add("Октябрьское");
        spinnerArrayListRegions.add("Олекминск");
        spinnerArrayListRegions.add("Оленек");
        spinnerArrayListRegions.add("Олонец");
        spinnerArrayListRegions.add("Омолон");
        spinnerArrayListRegions.add("Омск");
        spinnerArrayListRegions.add("Омсукчан");
        spinnerArrayListRegions.add("Онгудай");
        spinnerArrayListRegions.add("Онега");
        spinnerArrayListRegions.add("Орел");
        spinnerArrayListRegions.add("Оренбург");
        spinnerArrayListRegions.add("Орлинга");
        spinnerArrayListRegions.add("Оссора");
        spinnerArrayListRegions.add("Островное");
        spinnerArrayListRegions.add("Оха");
        spinnerArrayListRegions.add("Охотск");
        spinnerArrayListRegions.add("Охотский Перевоз");
        spinnerArrayListRegions.add("Паданы");
        spinnerArrayListRegions.add("Палатка");
        spinnerArrayListRegions.add("Партизанск");
        spinnerArrayListRegions.add("Пенза");
        spinnerArrayListRegions.add("Перевоз");
        spinnerArrayListRegions.add("Пермь");
        spinnerArrayListRegions.add("Петрозаводск");
        spinnerArrayListRegions.add("Петропавловск-Камчатский");
        spinnerArrayListRegions.add("Петрунь");
        spinnerArrayListRegions.add("Печора");
        spinnerArrayListRegions.add("Погиби");
        spinnerArrayListRegions.add("Порецкое");
        spinnerArrayListRegions.add("Поронайск");
        spinnerArrayListRegions.add("Посьет");
        spinnerArrayListRegions.add("Поярково");
        spinnerArrayListRegions.add("Преображение");
        spinnerArrayListRegions.add("Преображенка");
        spinnerArrayListRegions.add("Приморско-Ахтарск");
        spinnerArrayListRegions.add("Псков");
        spinnerArrayListRegions.add("Пялица");
        spinnerArrayListRegions.add("Реболы");
        spinnerArrayListRegions.add("Родино");
        spinnerArrayListRegions.add("Ростов-на-Дону");
        spinnerArrayListRegions.add("Рубцовск");
        spinnerArrayListRegions.add("Рудная Пристань");
        spinnerArrayListRegions.add("Рязань");
        spinnerArrayListRegions.add("Салехард");
        spinnerArrayListRegions.add("Самара");
        spinnerArrayListRegions.add("Сангар");
        spinnerArrayListRegions.add("Санкт-Петербург");
        spinnerArrayListRegions.add("Саранск");
        spinnerArrayListRegions.add("Сарапул");
        spinnerArrayListRegions.add("Саратов");
        spinnerArrayListRegions.add("Саскылах");
        spinnerArrayListRegions.add("Свободный");
        spinnerArrayListRegions.add("Семячик");
        spinnerArrayListRegions.add("Сковородино");
        spinnerArrayListRegions.add("Славгород");
        spinnerArrayListRegions.add("Смоленск");
        spinnerArrayListRegions.add("Соболево");
        spinnerArrayListRegions.add("Советская Гавань");
        spinnerArrayListRegions.add("Сорочинск");
        spinnerArrayListRegions.add("Сортавала");
        spinnerArrayListRegions.add("Сосново-Озерское");
        spinnerArrayListRegions.add("Сосуново");
        spinnerArrayListRegions.add("Сосьва");
        spinnerArrayListRegions.add("Софийский Прииск");
        spinnerArrayListRegions.add("Среднекан");
        spinnerArrayListRegions.add("Среднеколымск");
        spinnerArrayListRegions.add("Средний Васюган");
        spinnerArrayListRegions.add("Средний Калар");
        spinnerArrayListRegions.add("Ставрополь");
        spinnerArrayListRegions.add("Старица");
        spinnerArrayListRegions.add("Сунтар");
        spinnerArrayListRegions.add("Сургут");
        spinnerArrayListRegions.add("Сурское");
        spinnerArrayListRegions.add("Сусуман");
        spinnerArrayListRegions.add("Сухана");
        spinnerArrayListRegions.add("Сыктывкар");
        spinnerArrayListRegions.add("Таганрог");
        spinnerArrayListRegions.add("Тайга");
        spinnerArrayListRegions.add("Тайшет");
        spinnerArrayListRegions.add("Тамбов");
        spinnerArrayListRegions.add("Тара");
        spinnerArrayListRegions.add("Тарко-Сале");
        spinnerArrayListRegions.add("Татарск");
        spinnerArrayListRegions.add("Тверь");
        spinnerArrayListRegions.add("Терекли-Мектеб");
        spinnerArrayListRegions.add("Териберка");
        spinnerArrayListRegions.add("Тисуль");
        spinnerArrayListRegions.add("Тихвин");
        spinnerArrayListRegions.add("Тихорецк");
        spinnerArrayListRegions.add("Тобольск");
        spinnerArrayListRegions.add("Тогул");
        spinnerArrayListRegions.add("Токо");
        spinnerArrayListRegions.add("Томмот");
        spinnerArrayListRegions.add("Томпо");
        spinnerArrayListRegions.add("Томск");
        spinnerArrayListRegions.add("Топки");
        spinnerArrayListRegions.add("Тотьма");
        spinnerArrayListRegions.add("Троицкий АО");
        spinnerArrayListRegions.add("Троицкое");
        spinnerArrayListRegions.add("Троицко-Печорское");
        spinnerArrayListRegions.add("Тула");
        spinnerArrayListRegions.add("Тулун");
        spinnerArrayListRegions.add("Тунгокочен");
        spinnerArrayListRegions.add("Туой-Хая");
        spinnerArrayListRegions.add("Тупик");
        spinnerArrayListRegions.add("Тура");
        spinnerArrayListRegions.add("Туринск");
        spinnerArrayListRegions.add("Туруханск");
        spinnerArrayListRegions.add("Тында");
        spinnerArrayListRegions.add("Тюмень");
        spinnerArrayListRegions.add("Тяня");
        spinnerArrayListRegions.add("Уакит");
        spinnerArrayListRegions.add("Угут");
        spinnerArrayListRegions.add("Улан-Удэ");
        spinnerArrayListRegions.add("Ульяновск");
        spinnerArrayListRegions.add("Умба");
        spinnerArrayListRegions.add("Уренгой");
        spinnerArrayListRegions.add("Усть-Воямполка");
        spinnerArrayListRegions.add("Усть-Кабырза");
        spinnerArrayListRegions.add("Усть-Камчатск");
        spinnerArrayListRegions.add("Усть-Мая");
        spinnerArrayListRegions.add("Усть-Миль");
        spinnerArrayListRegions.add("Усть-Мома");
        spinnerArrayListRegions.add("Усть-Нюкжа");
        spinnerArrayListRegions.add("Усть-Озерное");
        spinnerArrayListRegions.add("Усть-Олой");
        spinnerArrayListRegions.add("Усть-Ордынский");
        spinnerArrayListRegions.add("Усть-Уса");
        spinnerArrayListRegions.add("Усть-Хайрюзово");
        spinnerArrayListRegions.add("Усть-Цильма");
        spinnerArrayListRegions.add("Усть-Щугор");
        spinnerArrayListRegions.add("Уфа");
        spinnerArrayListRegions.add("Ухта");
        spinnerArrayListRegions.add("Хабаровск");
        spinnerArrayListRegions.add("Ханты-Мансийск");
        spinnerArrayListRegions.add("Хатанга");
        spinnerArrayListRegions.add("Ходовариха");
        spinnerArrayListRegions.add("Холмск");
        spinnerArrayListRegions.add("Хоринск");
        spinnerArrayListRegions.add("Хоседа-Хард");
        spinnerArrayListRegions.add("Чара");
        spinnerArrayListRegions.add("Чебоксары");
        spinnerArrayListRegions.add("Челюскин, мыс");
        spinnerArrayListRegions.add("Челябинск");
        spinnerArrayListRegions.add("Чердынь");
        spinnerArrayListRegions.add("Черкесск");
        spinnerArrayListRegions.add("Черлак");
        spinnerArrayListRegions.add("Черняево");
        spinnerArrayListRegions.add("Черусти");
        spinnerArrayListRegions.add("Чечуйск");
        spinnerArrayListRegions.add("Чита");
        spinnerArrayListRegions.add("Чугуевка");
        spinnerArrayListRegions.add("Чулым");
        spinnerArrayListRegions.add("Чульман");
        spinnerArrayListRegions.add("Чумикан");
        spinnerArrayListRegions.add("Чурапча");
        spinnerArrayListRegions.add("Чухлома");
        spinnerArrayListRegions.add("Шамары");
        spinnerArrayListRegions.add("Шарья");
        spinnerArrayListRegions.add("Шелагонцы");
        spinnerArrayListRegions.add("Шенкурск");
        spinnerArrayListRegions.add("Шимановск");
        spinnerArrayListRegions.add("Шира");
        spinnerArrayListRegions.add("Эйк");
        spinnerArrayListRegions.add("Экимчан");
        spinnerArrayListRegions.add("Элиста");
        spinnerArrayListRegions.add("Эльтон");
        spinnerArrayListRegions.add("Эньмувеем");
        spinnerArrayListRegions.add("Южно-Курильск");
        spinnerArrayListRegions.add("Южно-Сахалинск");
        spinnerArrayListRegions.add("Яйлю");
        spinnerArrayListRegions.add("Якутск");
        spinnerArrayListRegions.add("Янаул");
        spinnerArrayListRegions.add("Ярославль");
        spinnerArrayListRegions.add("Ярцево");
        spinnerAdapterRegions = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArrayListRegions);
        spinnerAdapterRegions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegions.setAdapter(spinnerAdapterRegions);


    }

    public void RegionsMap() {
        RegionMap = new HashMap();
        RegionMap.put("Абакан", 7.99);
        RegionMap.put("Агата", 12.75);
        RegionMap.put("Агзу", 8.11);
        RegionMap.put("Агинское", 9.17);
        RegionMap.put("Ай-Петри", 2.61);
        RegionMap.put("Акша", 8.96);
        RegionMap.put("Алдан", 11.03);
        RegionMap.put("Алейск", 7.5);
        RegionMap.put("Александров Гай", 5.5);
        RegionMap.put("Александровский Завод", 10.11);
        RegionMap.put("Александровское", 9.06);
        RegionMap.put("Александровск-Сахалинский", 7.5);
        RegionMap.put("Алыгджер", 7.66);
        RegionMap.put("Амга", 13.56);
        RegionMap.put("Анадырь", 10.74);
        RegionMap.put("Анучино", 7.64);
        RegionMap.put("Апука", 7.89);
        RegionMap.put("Арзамас", 5.87);
        RegionMap.put("Арзгир", 2.57);
        RegionMap.put("Архангельск", 6.68);
        RegionMap.put("Архара", 9.5);
        RegionMap.put("Астраханка", 6.96);
        RegionMap.put("Астрахань", 3.29);
        RegionMap.put("Ачинск", 7.6);
        RegionMap.put("Аян", 8.62);
        RegionMap.put("Бабаево", 5.74);
        RegionMap.put("Бабушкин", 7.29);
        RegionMap.put("Багдарин", 10.91);
        RegionMap.put("Байдуков", 8.93);
        RegionMap.put("Байкальск", 7.37);
        RegionMap.put("Байкит", 11.2);
        RegionMap.put("Балашов", 5.22);
        RegionMap.put("Барабинск", 8.21);
        RegionMap.put("Баргузин", 9.74);
        RegionMap.put("Барнаул", 7.6);
        RegionMap.put("Батамай", 13.69);
        RegionMap.put("Бежецк", 5.64);
        RegionMap.put("Белгород", 4.15);
        RegionMap.put("Белогорск", 9.37);
        RegionMap.put("Белорецк", 7.33);
        RegionMap.put("Бердигястях", 13.12);
        RegionMap.put("Березово", 9.49);
        RegionMap.put("Бийск", 7.64);
        RegionMap.put("Бикин", 8.12);
        RegionMap.put("Биробиджан", 8.61);
        RegionMap.put("Бисер", 7.8);
        RegionMap.put("Благовещенск", 8.76);
        RegionMap.put("Богополь", 6.01);
        RegionMap.put("Боготол", 7.87);
        RegionMap.put("Богучаны", 9.38);
        RegionMap.put("Бодайбо", 10.98);
        RegionMap.put("Болотное", 7.92);
        RegionMap.put("Большерецк", 6.56);
        RegionMap.put("Бомнак", 10.82);
        RegionMap.put("Борзя", 9.81);
        RegionMap.put("Боровичи", 4.94);
        RegionMap.put("Братолюбовка", 9.73);
        RegionMap.put("Братск", 8.53);
        RegionMap.put("Брохово", 9.39);
        RegionMap.put("Брянск", 4.45);
        RegionMap.put("Бугульма", 6.67);
        RegionMap.put("Буяга", 12.75);
        RegionMap.put("Бысса", 10.45);
        RegionMap.put("Вайда-Губа", 4.51);
        RegionMap.put("Ванавара", 11.06);
        RegionMap.put("Варандей", 9.35);
        RegionMap.put("Великие Луки", 4.31);
        RegionMap.put("Великий Новгород", 4.63);
        RegionMap.put("Вельмо", 10.66);
        RegionMap.put("Вендинга", 7.5);
        RegionMap.put("Верхнеимбатск", 10.21);
        RegionMap.put("Верхне-Марково", 10.47);
        RegionMap.put("Верхнеуральск", 7.79);
        RegionMap.put("Верхний Баскунчак", 4.32);
        RegionMap.put("Верхняя Гутара", 8.61);
        RegionMap.put("Верхотурье", 7.51);
        RegionMap.put("Верхоянск", 14.91);
        RegionMap.put("Вилюйск", 12.67);
        RegionMap.put("Витим", 10.86);
        RegionMap.put("Владивосток", 5.83);
        RegionMap.put("Владикавказ", 2.02);
        RegionMap.put("Владимир", 5.47);
        RegionMap.put("Волгоград", 4.22);
        RegionMap.put("Вологда", 6.12);
        RegionMap.put("Волочанка", 13.0);
        RegionMap.put("Воркута", 9.99);
        RegionMap.put("Воронеж", 4.53);
        RegionMap.put("Выборг", 4.75);
        RegionMap.put("Выкса", 5.44);
        RegionMap.put("Вытегра", 5.8);
        RegionMap.put("Вяземский", 8.06);
        RegionMap.put("Вязьма", 5.02);
        RegionMap.put("Гвасюги", 9.07);
        RegionMap.put("Гигант", 2.72);
        RegionMap.put("Глазов", 7.01);
        RegionMap.put("Грозный", 2.14);
        RegionMap.put("Дальнереченск", 7.81);
        RegionMap.put("Дарасун", 9.03);
        RegionMap.put("Демьянское", 8.26);
        RegionMap.put("Джалинда", 13.9);
        RegionMap.put("Джаорэ", 8.67);
        RegionMap.put("Джарджан", 13.55);
        RegionMap.put("Джикимда", 11.92);
        RegionMap.put("Диксон", 12.04);
        RegionMap.put("Дмитров", 5.18);
        RegionMap.put("Долинск", 6.47);
        RegionMap.put("Дуван", 7.11);
        RegionMap.put("Дудинка", 12.28);
        RegionMap.put("Екатеринбург", 6.8);
        RegionMap.put("Екатерино-Никольское", 8.14);
        RegionMap.put("Елабуга", 6.42);
        RegionMap.put("Емецк", 6.82);
        RegionMap.put("Енисейск", 8.85);
        RegionMap.put("Ербогачен", 11.53);
        RegionMap.put("Ерофей Павлович", 10.54);
        RegionMap.put("Ершово", 8.98);
        RegionMap.put("Жигалово", 11.53);
        RegionMap.put("Жиганск", 13.43);
        RegionMap.put("Завитинск", 9.84);
        RegionMap.put("Земетчино", 5.54);
        RegionMap.put("Зея", 9.71);
        RegionMap.put("Зима", 8.79);
        RegionMap.put("Змеиногорск", 7.2);
        RegionMap.put("Зырянка", 13.31);
        RegionMap.put("Иваново", 5.73);
        RegionMap.put("Ивдель", 8.17);
        RegionMap.put("Игарка", 11.67);
        RegionMap.put("Ижевск", 6.79);
        RegionMap.put("Ика", 11.08);
        RegionMap.put("Им. Полины Осипенко", 9.81);
        RegionMap.put("Индига", 7.92);
        RegionMap.put("Иркутск", 8.01);
        RegionMap.put("Исиль-Куль", 7.97);
        RegionMap.put("Исить", 12.28);
        RegionMap.put("Ича", 6.91);
        RegionMap.put("Иэма", 14.92);
        RegionMap.put("Йошкар-Ола", 6.34);
        RegionMap.put("Казань", 6.16);
        RegionMap.put("Калакан", 11.87);
        RegionMap.put("Калевала", 6.69);
        RegionMap.put("Калининград", 1.97);
        RegionMap.put("Калуга", 5.02);
        RegionMap.put("Каменск-Уральский", 7.22);
        RegionMap.put("Камышин", 5.05);
        RegionMap.put("Кандалакша", 6.86);
        RegionMap.put("Канин Нос", 6.11);
        RegionMap.put("Канск", 8.59);
        RegionMap.put("Карасук", 8.09);
        RegionMap.put("Каргополь", 6.37);
        RegionMap.put("Катанда", 8.66);
        RegionMap.put("Кашира", 5.24);
        RegionMap.put("Кемерово", 8.04);
        RegionMap.put("Кемь", 6.06);
        RegionMap.put("Кильмезь", 6.55);
        RegionMap.put("Кинешма", 5.74);
        RegionMap.put("Киренск", 10.37);
        RegionMap.put("Киров", 6.57);
        RegionMap.put("Кировский", 7.73);
        RegionMap.put("Киселевск", 7.41);
        RegionMap.put("Кисловодск", 2.26);
        RegionMap.put("Клепинино", 0.84);
        RegionMap.put("Ключи", 7.77);
        RegionMap.put("Ковдор", 7.06);
        RegionMap.put("Козыревск", 8.16);
        RegionMap.put("Койнас", 7.71);
        RegionMap.put("Колпашево", 8.57);
        RegionMap.put("Комсомольск-на-Амуре", 9.24);
        RegionMap.put("Кондинское", 8.28);
        RegionMap.put("Кондома", 7.85);
        RegionMap.put("Корсаков", 5.51);
        RegionMap.put("Корф", 8.31);
        RegionMap.put("Кострома", 5.77);
        RegionMap.put("Котельниково", 3.94);
        RegionMap.put("Коткино", 8.56);
        RegionMap.put("Котлас", 6.77);
        RegionMap.put("Кочки", 8.19);
        RegionMap.put("Кош-Агач", 10.28);
        RegionMap.put("Краснощелье", 7.46);
        RegionMap.put("Красноярск", 7.56);
        RegionMap.put("Красный Чикой", 9.54);
        RegionMap.put("Красный Яр", 8.69);
        RegionMap.put("Крест-Хальджай", 13.79);
        RegionMap.put("Кроноки", 5.62);
        RegionMap.put("Кувандык", 7.01);
        RegionMap.put("Купино", 8.12);
        RegionMap.put("Курган", 7.59);
        RegionMap.put("Курильск", 3.89);
        RegionMap.put("Курск", 4.51);
        RegionMap.put("Кызыл", 10.16);
        RegionMap.put("Кыштовка", 8.3);
        RegionMap.put("Кюсюр", 13.82);
        RegionMap.put("Кяхта", 8.41);
        RegionMap.put("Ленск", 11.14);
        RegionMap.put("Леуши", 7.92);
        RegionMap.put("Липецк", 5.1);
        RegionMap.put("Ловозеро", 7.49);
        RegionMap.put("Лопатка, мыс", 4.21);
        RegionMap.put("Магадан", 8.61);
        RegionMap.put("Магас)", 1.82);
        RegionMap.put("Майкоп", 0.45);
        RegionMap.put("Макаров", 6.63);
        RegionMap.put("Мама", 10.52);
        RegionMap.put("Маргаритово", 6.15);
        RegionMap.put("Мариинск", 7.84);
        RegionMap.put("Марково", 11.68);
        RegionMap.put("Марресаля", 10.66);
        RegionMap.put("Мезень", 7.28);
        RegionMap.put("Мелеуз", 6.95);
        RegionMap.put("Мельничное", 8.61);
        RegionMap.put("Миллерово", 3.94);
        RegionMap.put("Мильково", 8.56);
        RegionMap.put("Минеральные Воды", 2.65);
        RegionMap.put("Минусинск", 7.93);
        RegionMap.put("Мирный", 11.73);
        RegionMap.put("Могоча", 10.74);
        RegionMap.put("Можайск", 5.07);
        RegionMap.put("Монды", 8.68);
        RegionMap.put("Мончегорск", 6.96);
        RegionMap.put("Москва", 4.69);
        RegionMap.put("Мурманск", 6.32);
        RegionMap.put("Муром", 5.58);
        RegionMap.put("Нагорный", 11.58);
        RegionMap.put("Нагорское", 6.94);
        RegionMap.put("Надым", 10.36);
        RegionMap.put("Наканно", 12.23);
        RegionMap.put("Нальчик", 2.35);
        RegionMap.put("Наро-Фоминск", 5.09);
        RegionMap.put("Нарьян-Мар", 8.71);
        RegionMap.put("Начики", 8.55);
        RegionMap.put("Невельск", 4.83);
        RegionMap.put("Невинномысск", 2.49);
        RegionMap.put("Непа", 10.74);
        RegionMap.put("Нера", 14.74);
        RegionMap.put("Нерчинск", 10.46);
        RegionMap.put("Нерчинский Завод", 9.97);
        RegionMap.put("Ниванкюль", 6.96);
        RegionMap.put("Нижнеангарск", 9.21);
        RegionMap.put("Нижнетамбовское", 9.44);
        RegionMap.put("Нижний Новгород", 5.64);
        RegionMap.put("Николаевск-на-Амуре", 9.21);
        RegionMap.put("Никольск", 6.54);
        RegionMap.put("Новая Ладога", 4.89);
        RegionMap.put("Новоаннинский", 4.7);
        RegionMap.put("Новомосковский АО", 5.13);
        RegionMap.put("Новосибирск", 7.94);
        RegionMap.put("Ноглики", 8.15);
        RegionMap.put("Ножовка", 6.8);
        RegionMap.put("Норск", 10.46);
        RegionMap.put("Нюрба", 12.55);
        RegionMap.put("Нязепетровск", 7.25);
        RegionMap.put("о. Беринга", 3.41);
        RegionMap.put("о. Сосновец", 6.15);
        RegionMap.put("Облучье", 9.54);
        RegionMap.put("Объячево", 6.87);
        RegionMap.put("Оймякон", 15.15);
        RegionMap.put("Октябрьское", 8.96);
        RegionMap.put("Олекминск", 11.52);
        RegionMap.put("Оленек", 13.32);
        RegionMap.put("Олонец", 5.57);
        RegionMap.put("Омолон", 13.52);
        RegionMap.put("Омск", 7.91);
        RegionMap.put("Омсукчан", 12.97);
        RegionMap.put("Онгудай", 8.16);
        RegionMap.put("Онега", 6.26);
        RegionMap.put("Орел", 4.7);
        RegionMap.put("Оренбург", 6.58);
        RegionMap.put("Орлинга", 10.11);
        RegionMap.put("Оссора", 8.07);
        RegionMap.put("Островное", 13.12);
        RegionMap.put("Оха", 8.41);
        RegionMap.put("Охотск", 9.55);
        RegionMap.put("Охотский Перевоз", 13.75);
        RegionMap.put("Паданы", 6.07);
        RegionMap.put("Палатка", 10.19);
        RegionMap.put("Партизанск", 5.92);
        RegionMap.put("Пенза", 5.67);
        RegionMap.put("Перевоз", 10.54);
        RegionMap.put("Пермь", 6.86);
        RegionMap.put("Петрозаводск", 5.67);
        RegionMap.put("Петропавловск-Камчатский", 4.83);
        RegionMap.put("Петрунь", 9.32);
        RegionMap.put("Печора", 8.63);
        RegionMap.put("Погиби", 8.69);
        RegionMap.put("Порецкое", 6.03);
        RegionMap.put("Поронайск", 7.35);
        RegionMap.put("Посьет", 4.85);
        RegionMap.put("Поярково", 9.3);
        RegionMap.put("Преображение", 4.44);
        RegionMap.put("Преображенка", 11.08);
        RegionMap.put("Приморско-Ахтарск", 1.41);
        RegionMap.put("Псков", 4.15);
        RegionMap.put("Пялица", 6.55);
        RegionMap.put("Реболы", 6.37);
        RegionMap.put("Родино", 7.67);
        RegionMap.put("Ростов-на-Дону", 2.83);
        RegionMap.put("Рубцовск", 7.61);
        RegionMap.put("Рудная Пристань", 5.56);
        RegionMap.put("Рязань", 5.21);
        RegionMap.put("Салехард", 10.52);
        RegionMap.put("Самара", 5.97);
        RegionMap.put("Сангар", 13.13);
        RegionMap.put("Санкт-Петербург", 4.2);
        RegionMap.put("Саранск", 5.89);
        RegionMap.put("Сарапул", 6.7);
        RegionMap.put("Саратов", 5.01);
        RegionMap.put("Саскылах", 13.98);
        RegionMap.put("Свободный", 9.7);
        RegionMap.put("Семячик", 4.81);
        RegionMap.put("Сковородино", 10.64);
        RegionMap.put("Славгород", 7.96);
        RegionMap.put("Смоленск", 4.63);
        RegionMap.put("Соболево", 7.29);
        RegionMap.put("Советская Гавань", 7.29);
        RegionMap.put("Сорочинск", 6.51);
        RegionMap.put("Сортавала", 5.24);
        RegionMap.put("Сосново-Озерское", 9.68);
        RegionMap.put("Сосуново", 6.35);
        RegionMap.put("Сосьва", 9.31);
        RegionMap.put("Софийский Прииск", 11.41);
        RegionMap.put("Среднекан", 13.2);
        RegionMap.put("Среднеколымск", 13.38);
        RegionMap.put("Средний Васюган", 8.54);
        RegionMap.put("Средний Калар", 12.24);
        RegionMap.put("Ставрополь", 2.39);
        RegionMap.put("Старица", 5.11);
        RegionMap.put("Сунтар", 11.97);
        RegionMap.put("Сургут", 9.07);
        RegionMap.put("Сурское", 5.96);
        RegionMap.put("Сусуман", 13.67);
        RegionMap.put("Сухана", 14.08);
        RegionMap.put("Сыктывкар", 7.16);
        RegionMap.put("Таганрог", 2.66);
        RegionMap.put("Тайга", 8.17);
        RegionMap.put("Тайшет", 8.22);
        RegionMap.put("Тамбов", 5.18);
        RegionMap.put("Тара", 8.17);
        RegionMap.put("Тарко-Сале", 10.66);
        RegionMap.put("Татарск", 8.07);
        RegionMap.put("Тверь", 5.13);
        RegionMap.put("Терекли-Мектеб", 1.7);
        RegionMap.put("Териберка", 5.53);
        RegionMap.put("Тисуль", 7.71);
        RegionMap.put("Тихвин", 5.26);
        RegionMap.put("Тихорецк", 3.62);
        RegionMap.put("Тобольск", 8.07);
        RegionMap.put("Тогул", 7.36);
        RegionMap.put("Токо", 13.12);
        RegionMap.put("Томмот", 12.28);
        RegionMap.put("Томпо", 14.35);
        RegionMap.put("Томск", 8.05);
        RegionMap.put("Топки", 7.89);
        RegionMap.put("Тотьма", 6.38);
        RegionMap.put("Троицкий АО", 5.13);
        RegionMap.put("Троицкое", 8.6);
        RegionMap.put("Троицко-Печорское", 7.99);
        RegionMap.put("Тула", 4.92);
        RegionMap.put("Тулун", 8.49);
        RegionMap.put("Тунгокочен", 11.11);
        RegionMap.put("Туой-Хая", 12.2);
        RegionMap.put("Тупик", 11.41);
        RegionMap.put("Тура", 12.42);
        RegionMap.put("Туринск", 7.58);
        RegionMap.put("Туруханск", 10.97);
        RegionMap.put("Тында", 11.01);
        RegionMap.put("Тюмень", 7.49);
        RegionMap.put("Тяня", 11.88);
        RegionMap.put("Уакит", 11.07);
        RegionMap.put("Угут", 8.79);
        RegionMap.put("Улан-Удэ", 8.97);
        RegionMap.put("Ульяновск", 6.05);
        RegionMap.put("Умба", 6.45);
        RegionMap.put("Уренгой", 11.21);
        RegionMap.put("Усть-Воямполка", 8.51);
        RegionMap.put("Усть-Кабырза", 8.24);
        RegionMap.put("Усть-Камчатск", 6.94);
        RegionMap.put("Усть-Мая", 13.09);
        RegionMap.put("Усть-Миль", 12.85);
        RegionMap.put("Усть-Мома", 14.45);
        RegionMap.put("Усть-Нюкжа", 11.28);
        RegionMap.put("Усть-Озерное", 8.9);
        RegionMap.put("Усть-Олой", 13.45);
        RegionMap.put("Усть-Ордынский", 9.3);
        RegionMap.put("Усть-Уса", 8.76);
        RegionMap.put("Усть-Хайрюзово", 7.47);
        RegionMap.put("Усть-Цильма", 8.14);
        RegionMap.put("Усть-Щугор", 8.73);
        RegionMap.put("Уфа", 6.81);
        RegionMap.put("Ухта", 7.85);
        RegionMap.put("Хабаровск", 8.23);
        RegionMap.put("Ханты-Мансийск", 8.64);
        RegionMap.put("Хатанга", 13.42);
        RegionMap.put("Ходовариха", 8.72);
        RegionMap.put("Холмск", 5.04);
        RegionMap.put("Хоринск", 9.52);
        RegionMap.put("Хоседа-Хард", 9.66);
        RegionMap.put("Чара", 11.8);
        RegionMap.put("Чебоксары", 6.16);
        RegionMap.put("Челюскин, мыс", 13.3);
        RegionMap.put("Челябинск", 7.2);
        RegionMap.put("Чердынь", 7.48);
        RegionMap.put("Черкесск", 2.39);
        RegionMap.put("Черлак", 8.02);
        RegionMap.put("Черняево", 9.99);
        RegionMap.put("Черусти", 5.38);
        RegionMap.put("Чечуйск", 10.39);
        RegionMap.put("Чита", 9.55);
        RegionMap.put("Чугуевка", 8.07);
        RegionMap.put("Чулым", 8.15);
        RegionMap.put("Чульман", 11.67);
        RegionMap.put("Чумикан", 9.18);
        RegionMap.put("Чурапча", 13.66);
        RegionMap.put("Чухлома", 6.22);
        RegionMap.put("Шамары", 7.29);
        RegionMap.put("Шарья", 6.31);
        RegionMap.put("Шелагонцы", 13.86);
        RegionMap.put("Шенкурск", 6.61);
        RegionMap.put("Шимановск", 9.76);
        RegionMap.put("Шира", 7.84);
        RegionMap.put("Эйк", 13.27);
        RegionMap.put("Экимчан", 10.95);
        RegionMap.put("Элиста", 3.44);
        RegionMap.put("Эльтон", 4.71);
        RegionMap.put("Эньмувеем", 11.93);
        RegionMap.put("Южно-Курильск", 3.65);
        RegionMap.put("Южно-Сахалинск", 6.35);
        RegionMap.put("Яйлю", 5.22);
        RegionMap.put("Якутск", 13.12);
        RegionMap.put("Янаул", 7.05);
        RegionMap.put("Ярославль", 5.67);
        RegionMap.put("Ярцево", 9.64);
    }
}










