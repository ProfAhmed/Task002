package com.pro.ahmed.hardtask002;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.pro.ahmed.hardtask002.models.ModelCity;
import com.pro.ahmed.hardtask002.models.ModelCode;
import com.pro.ahmed.hardtask002.models.ModelCountry;
import com.pro.ahmed.hardtask002.models.ModelRegistration;
import com.pro.ahmed.hardtask002.patterns.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

    // initialize Views
    @BindView(R.id.tvRegister)
    TextView tvRegister;
    @BindView(R.id.etFullName)
    EditText etFullName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etCode)
    EditText etCode;
    @BindView(R.id.spCode)
    Spinner spCode;
    @BindView(R.id.spCountry)
    Spinner spCountry;
    @BindView(R.id.spCity)
    Spinner spCity;
    @BindView(R.id.tvClickTerms)
    TextView tvClickTerms;
    @BindView(R.id.btnChangeLang)
    Button btnChangeLang;

    // declare lists for spinners
    private ArrayList<ModelCountry> countries;
    private ArrayList<ModelCity> cities;
    private ArrayList<ModelCode> codes;

    //declare User Information
    private String fullName;
    private String password;
    private String codeName;
    private String emailAddress;
    private String countryName;
    private String cityName;
    private int code;

    private boolean isSpinnerInitial = false; // check if this first time to prevent Liste
    private boolean checkSelectedCountry = false; // check if country is selected
    private boolean checkSelectedCity = false; // check if city is selected
    private boolean checkSelectedCode = false; // check if code is selected

    // declare Lists for Spinners
    private ArrayAdapter<ModelCountry> dataAdapterCountry;
    private ArrayAdapter<ModelCode> dataAdapterCode;
    private ArrayAdapter<ModelCity> dataAdapterCity;

    // declare global City Object
    private ModelCity mCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init(); // initialize UI

        openTermsAndConditions(); //open Terms and Conditions in WebViewActivity

        changeLang(); // to change Language

        setSpinnerListener(); // to set Spinners Listener

        initLists(); // initialize Array Lists

        initAdapter(); // initialize Adapters for Spinners

        sendCountryRequest(); // get Countries Data

        register(); // Submit Registration

    }

    private void init() {
        if (Build.VERSION.SDK_INT >= 21) {
            spCity.setBackground(getResources().getDrawable(R.drawable.sp_background_target_api21));
            spCountry.setBackground(getResources().getDrawable(R.drawable.sp_background_target_api21));
            spCode.setBackground(getResources().getDrawable(R.drawable.sp_background_target_api21));
        } else {
            spCity.setBackground(getResources().getDrawable(R.drawable.sp_background));
            spCountry.setBackground(getResources().getDrawable(R.drawable.sp_background));
            spCode.setBackground(getResources().getDrawable(R.drawable.sp_background));
        }
    }

    private void openTermsAndConditions() {
        tvClickTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
            }
        });
    }

    private void changeLang() {

        btnChangeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String languageToLoad; // to set selected Language
                languageToLoad = Locale.getDefault().getISO3Language();
                Log.v("LanguagePrefs", languageToLoad);
                if (languageToLoad.equals("eng")) {
                    languageToLoad = "ar";
                    Log.v("LocalLanguageClick", Locale.getDefault().getISO3Language());
                } else {
                    languageToLoad = "en";
                    Log.v("LocalLanguageElseClick", Locale.getDefault().getISO3Language());
                }
                setLangToLoad(languageToLoad); // to change Localization
            }
        });
    }

    private void setLangToLoad(String languageToLoad) {
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        resetView(); // to reset Views
    }

    private void resetView() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void setSpinnerListener() {
        spCountry.setOnItemSelectedListener(this);
        spCity.setOnItemSelectedListener(this);
        spCode.setOnItemSelectedListener(this);
        spCountry.setOnTouchListener(this);
        spCity.setOnTouchListener(this);
        spCode.setOnTouchListener(this);
    }

    private void initLists() {
        countries = new ArrayList<>();
        cities = new ArrayList<>();
        codes = new ArrayList<>();
    }

    private void initAdapter() {
        // initialize default spinner
        String countryDefault = getResources().getString(R.string.country);
        String codeDefault = getResources().getString(R.string.code);
        String cityDefault = getResources().getString(R.string.city);

        ModelCountry mCountry = new ModelCountry(countryDefault, countryDefault, Locale.getDefault().getISO3Language());
        ModelCode mCode = new ModelCode(codeDefault, codeDefault, Locale.getDefault().getISO3Language());
        mCity = new ModelCity(cityDefault, cityDefault, Locale.getDefault().getISO3Language());

        countries.add(mCountry);
        codes.add(mCode);
        cities.add(mCity);

        // Creating adapter for spinner
        dataAdapterCountry = new ArrayAdapter<>(MainActivity.this, R.layout.spinner_item, countries);
        dataAdapterCode = new ArrayAdapter<>(MainActivity.this, R.layout.spinner_item, codes);
        dataAdapterCity = new ArrayAdapter<ModelCity>(MainActivity.this, R.layout.spinner_item, cities);

        // Drop down layout style - list view with radio button
        dataAdapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterCode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spCountry.setAdapter(dataAdapterCountry);
        spCode.setAdapter(dataAdapterCode);
        spCity.setAdapter(dataAdapterCity);

    }

    private void register() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check required Data
                if (TextUtils.isEmpty(etFullName.getText().toString())) {
                    etFullName.setError(getResources().getString(R.string.etMessageError));

                } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                    etPassword.setError(getResources().getString(R.string.etMessageError));

                } else if (!checkSelectedCode) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.coedMessageError), Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(etEmail.getText().toString())) {
                    etEmail.setError(getResources().getString(R.string.etMessageError));

                } else if (!checkSelectedCountry) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.countryMessageError), Toast.LENGTH_SHORT).show();

                } else if (!checkSelectedCity) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.cityMessageError), Toast.LENGTH_SHORT).show();

                } else {
                    fullName = etFullName.getText().toString();
                    password = etPassword.getText().toString();
                    emailAddress = etEmail.getText().toString();
                    code = Integer.parseInt(etCode.getText().toString());

                    ModelRegistration registration = new ModelRegistration(fullName, password, codeName,
                            emailAddress, countryName, cityName, code);
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.success) + "\n"
                            + registration, Toast.LENGTH_LONG).show();
                    Log.v("Registration", registration.toString());
                }
            }
        });
    }

    private void sendCountryRequest() {

        final String url = "http://souq.hardtask.co/app/app.asmx/GetCountries"; // countries webservice url

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try {
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject countryOBJ = response.getJSONObject(i);

                                // Get the current County (json object) data
                                String titleEN = countryOBJ.getString("TitleEN");
                                String titleAR = countryOBJ.getString("TitleAR");
                                String codeEN = countryOBJ.getString("CodeEN");
                                String codeAR = countryOBJ.getString("CodeAR");
                                String checkLang = Locale.getDefault().getISO3Language();
                                int code = countryOBJ.getInt("Code");
                                int id = countryOBJ.getInt("Id");
                                ModelCode mCode = new ModelCode(codeEN, codeAR, code, checkLang);
                                ModelCountry mCountry = new ModelCountry(titleEN, titleAR, id, mCode, checkLang);
                                countries.add(mCountry);
                                codes.add(mCode);
                            }

                            dataAdapterCountry.notifyDataSetChanged(); // update Country spinner
                            dataAdapterCode.notifyDataSetChanged();    // update Code spinner

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // show error message
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.loadingError), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    private void sendCityRequest(int id) {

        final String url = "http://souq.hardtask.co/app/app.asmx/GetCities?countryId=" + String.valueOf(id); // cities webservice url

        if (cities.size() > 0) { //clear cities list;
            cities.clear();
            cities.add(mCity);
        }
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try {
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject cityOBJ = response.getJSONObject(i);

                                // Get the current County (json object) data
                                String titleEN = cityOBJ.getString("TitleEN");
                                String titleAR = cityOBJ.getString("TitleAR");
                                String checkLang = Locale.getDefault().getISO3Language();
                                ModelCity mCity = new ModelCity(titleEN, titleAR, checkLang);
                                cities.add(mCity);
                            }

                            if (cities.size() <= 1) { // no cities is found
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.noCities), Toast.LENGTH_SHORT).show();
                            }

                            spCity.setAdapter(dataAdapterCity); // update City spinner


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // show error message
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.loadingError), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (isSpinnerInitial) {

            String item = parent.getItemAtPosition(position).toString();

            if (parent.getItemAtPosition(position) instanceof ModelCountry) { // check if spinner is Country

                ModelCountry mCountry = (ModelCountry) parent.getItemAtPosition(position);
                countryName = item;
                sendCityRequest(mCountry.getId()); // get Cities Data

                if (item.equals(getResources().getString(R.string.country))) { // check if country is selected
                    checkSelectedCountry = false;
                } else {
                    checkSelectedCountry = true;
                }

            } else if (parent.getItemAtPosition(position) instanceof ModelCode) { // check if spinner is Code
                ModelCode mCode = (ModelCode) parent.getItemAtPosition(position);
                codeName = item;
                code = mCode.getCode();
                etCode.setText(String.valueOf(code)); // set Code EditTest field
                if (item.equals(getResources().getString(R.string.code))) {  // check if code is selected
                    checkSelectedCode = false;
                } else {
                    checkSelectedCode = true;
                }
            } else if (parent.getItemAtPosition(position) instanceof ModelCity) { // check if spinner is City
                cityName = item;
                if (item.equals(getResources().getString(R.string.city))) { // check if city is selected
                    checkSelectedCity = false;
                } else {
                    checkSelectedCity = true;
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        isSpinnerInitial = true;
        return false;
    }
}
