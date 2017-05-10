package com.android.nmobile.equityassurance.ui.micro_insurance;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.nmobile.equityassurance.ui.ConfirmOderActivity;
import com.android.nmobile.equityassurance.R;
import com.android.nmobile.equityassurance.dto.ConfirmOderDetails;
import com.android.nmobile.equityassurance.utils.ApplicationConstants;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Dev Rupesh Saxena
 */

public class EquiCareActivity extends AppCompatActivity {

    private SliderLayout mDemoSlider;
    private MaterialSpinner mProductsSpinner, mOptionsSpinner;
    private String[] products, options;
    private int index = 0;
    private ArrayList<String> pricing;
    private TextView selectedAmountTV, briefDescTV, proceedBTN;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equi_care);

        //set title of toolbar
        getSupportActionBar().setTitle(ApplicationConstants.EQUI_CARE);
        //HomeAsUpEnabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //casting views
        briefDescTV = (TextView) findViewById(R.id.briefDescTV);
        selectedAmountTV = (TextView) findViewById(R.id.selectedAmountTV);
        mProductsSpinner = (MaterialSpinner) findViewById(R.id.spinner_products);
        mOptionsSpinner = (MaterialSpinner) findViewById(R.id.spinner_options);
        proceedBTN = (TextView) findViewById(R.id.proceedBTN);

        //by default disable package spinner
        mOptionsSpinner.setEnabled(false);

        //code for slider
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put(ApplicationConstants.TRAVEL_INSURANCE, R.drawable.banner_4);
        file_maps.put(ApplicationConstants.MICROLIFE_INSURANCE, R.drawable.banner_2);
        file_maps.put(ApplicationConstants.MICRO_INSURANCE, R.drawable.banner_3);
        file_maps.put(ApplicationConstants.HOME_INSURANCE, R.drawable.banner_1);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(EquiCareActivity.this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);


			/*//add your extra information
            textSliderView.getBundle()
			.putString("extra",name);*/

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Stack);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);

        //get array res.
        products = getResources().getStringArray(R.array.spinner_products_ec);
        options = getResources().getStringArray(R.array.spinner_options_ec);

        //setup spinner mProductsSpinner
        mProductsSpinner.setItems(products);
        mProductsSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {


                if (!mProductsSpinner.getText().toString().matches("Choose Equicare Product")) {
                    if (mOptionsSpinner.getText().toString().matches("With Health Plan")) {

                        //set index and amount
                        index = position;
                        mOptionsSpinner.setEnabled(true);
                        String[] temp = getResources().getStringArray(R.array.pricing_with_health_plan_ec);
                        pricing = new ArrayList<String>(Arrays.<String>asList(temp));
                        selectedAmountTV.setText(pricing.get(position));

                    } else if (mOptionsSpinner.getText().toString().matches("Without Health Plan")) {
                        //set index and amount
                        index = position;
                        mOptionsSpinner.setEnabled(true);
                        String[] temp = getResources().getStringArray(R.array.pricing_without_health_plan_ec);
                        pricing = new ArrayList<String>(Arrays.<String>asList(temp));
                        selectedAmountTV.setText(pricing.get(position));
                    } else {

                        //set index and amount
                        index = position;
                        mOptionsSpinner.setEnabled(true);
                    }
                } else {
                    Snackbar.make(view, "Please select equi care product", Snackbar.LENGTH_LONG).show();
                    //set index
                    index = 0;
                    mOptionsSpinner.setEnabled(false);
                    selectedAmountTV.setText("Amount");
                }
            }
        });

        //setup spinner mProductsSpinner
        mOptionsSpinner.setItems(options);
        mOptionsSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if (!mOptionsSpinner.getText().toString().matches("Choose Option") && !mProductsSpinner.getText().toString().matches("Choose Equicare Product")) {
                    //set amount
                    if (mOptionsSpinner.getText().toString().matches("With Health Plan")) {

                        String[] temp = getResources().getStringArray(R.array.pricing_with_health_plan_ec);
                        pricing = new ArrayList<String>(Arrays.<String>asList(temp));
                        selectedAmountTV.setText(pricing.get(index));

                    } else if (mOptionsSpinner.getText().toString().matches("Without Health Plan")) {

                        String[] temp = getResources().getStringArray(R.array.pricing_without_health_plan_ec);
                        pricing = new ArrayList<String>(Arrays.<String>asList(temp));
                        selectedAmountTV.setText(pricing.get(index));

                    }
                } else {
                    selectedAmountTV.setText("Amount");
                }
            }
        });


        //setup click on briefDescTV
        briefDescTV.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(EquiCareActivity.this);
                //alert.setTitle("Brief Descrption");

                WebView wv = new WebView(EquiCareActivity.this);
                wv.loadUrl("file:///android_asset/EquiCare.html");
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);

                        return true;
                    }
                });

                wv.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return true;
                    }
                });

                wv.setLongClickable(false);
                wv.setHapticFeedbackEnabled(false);


                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });


        //proceedBTN setup
        proceedBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mOptionsSpinner.getText().toString().matches("Choose Option") && !mProductsSpinner.getText().toString().matches("Choose Equicare Product")) {

                    //send details to confirm oder activity
                    Intent intent = new Intent(EquiCareActivity.this, ConfirmOderActivity.class);
                    intent.putExtra(ApplicationConstants.CONFIRM_OBJ, new ConfirmOderDetails(referenceId(10), mProductsSpinner.getText().toString() + " (" + mOptionsSpinner.getText().toString() + ")", getResources().getString(R.string.duration_esai), selectedAmountTV.getText().toString(), null));
                    startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(v, "Choose proper options", Snackbar.LENGTH_LONG).show();

                }
            }
        });


    }

    //referenceId generator
    private String referenceId(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(ApplicationConstants.STRING_RANGE.charAt(ApplicationConstants.SECURE_RANDOM.nextInt(ApplicationConstants.STRING_RANGE.length())));
        return sb.toString();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.push_left_out_activity_finish, R.anim.push_left_in_activity_finish);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_out_activity_finish, R.anim.push_left_in_activity_finish);
    }
}
