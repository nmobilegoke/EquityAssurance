package com.android.nmobile.equityassurance.ui.microlife_insurance;

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

public class EquityTargetInvestmentPlanActivity extends AppCompatActivity {

    private SliderLayout mDemoSlider;
    private MaterialSpinner mPolicyTermSpinner, mSpinnerContributions;
    private String[] policyTerms, contributions;
    private ArrayList<String> pricing;
    private TextView selectedAmountTV, briefDescTV, proceedBTN;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equity_target_investment_plan);
        //set title of toolbar
        getSupportActionBar().setTitle(ApplicationConstants.EQUITY_TARGET_INVESTMENT_PLAN);
        //HomeAsUpEnabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //casting views
        briefDescTV = (TextView) findViewById(R.id.briefDescTV);
        selectedAmountTV = (TextView) findViewById(R.id.selectedAmountTV);
        mPolicyTermSpinner = (MaterialSpinner) findViewById(R.id.spinner_policy_term);
        mSpinnerContributions = (MaterialSpinner) findViewById(R.id.spinner_contributions);
        proceedBTN = (TextView) findViewById(R.id.proceedBTN);

        //by default disable package spinner
        mSpinnerContributions.setEnabled(false);

        //code for slider
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put(ApplicationConstants.TRAVEL_INSURANCE, R.drawable.banner_4);
        file_maps.put(ApplicationConstants.MICROLIFE_INSURANCE, R.drawable.banner_2);
        file_maps.put(ApplicationConstants.MICRO_INSURANCE, R.drawable.banner_3);
        file_maps.put(ApplicationConstants.HOME_INSURANCE, R.drawable.banner_1);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(EquityTargetInvestmentPlanActivity.this);
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
        policyTerms = getResources().getStringArray(R.array.spinner_policy_term_etip);
        contributions = getResources().getStringArray(R.array.spinner_contributions_etip);
        String[] temp = getResources().getStringArray(R.array.pricing_etip);
        pricing = new ArrayList<String>(Arrays.<String>asList(temp));


        //setup spinner mDurationSpinner
        mPolicyTermSpinner.setItems(policyTerms);
        mPolicyTermSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {


                if (!mPolicyTermSpinner.getText().toString().matches("Choose Policy Term")) {

                    if (mSpinnerContributions.getText().toString().matches("Daily")) {
                        mSpinnerContributions.setEnabled(true);
                        selectedAmountTV.setText(pricing.get(1));
                    } else if (mSpinnerContributions.getText().toString().matches("Weekly")) {
                        mSpinnerContributions.setEnabled(true);
                        selectedAmountTV.setText(pricing.get(2));
                    } else if (mSpinnerContributions.getText().toString().matches("Monthly")) {
                        mSpinnerContributions.setEnabled(true);
                        selectedAmountTV.setText(pricing.get(3));
                    } else {
                        mSpinnerContributions.setEnabled(true);
                    }

                } else {
                    Snackbar.make(view, "Please select policy term", Snackbar.LENGTH_LONG).show();
                    mSpinnerContributions.setEnabled(false);
                    selectedAmountTV.setText("Amount");
                }
            }
        });

        //setup spinner mDurationSpinner
        mSpinnerContributions.setItems(contributions);
        mSpinnerContributions.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if (!mSpinnerContributions.getText().toString().matches("Choose Contribution")) {
                    //set amount
                    selectedAmountTV.setText(pricing.get(position));
                    //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
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
                AlertDialog.Builder alert = new AlertDialog.Builder(EquityTargetInvestmentPlanActivity.this);
                //alert.setTitle("Brief Descrption");

                WebView wv = new WebView(EquityTargetInvestmentPlanActivity.this);
                wv.loadUrl("file:///android_asset/EquityTargetInvestmentPlan.html");
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
                if (!mPolicyTermSpinner.getText().toString().matches("Choose Policy Term") && !mSpinnerContributions.getText().toString().matches("Choose Contribution")) {

                    //send details to confirm oder activity
                    Intent intent = new Intent(EquityTargetInvestmentPlanActivity.this, ConfirmOderActivity.class);
                    intent.putExtra(ApplicationConstants.CONFIRM_OBJ, new ConfirmOderDetails(referenceId(10), ApplicationConstants.EQUITY_TARGET_INVESTMENT_PLAN + " (" + mSpinnerContributions.getText() + ")", mPolicyTermSpinner.getText().toString(), selectedAmountTV.getText().toString(), null));
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
