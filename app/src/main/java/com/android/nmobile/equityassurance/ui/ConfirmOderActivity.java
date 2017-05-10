package com.android.nmobile.equityassurance.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.nmobile.equityassurance.R;
import com.android.nmobile.equityassurance.dto.ConfirmOderDetails;
import com.android.nmobile.equityassurance.utils.ApplicationConstants;
import com.jaredrummler.materialspinner.MaterialSpinner;

/**
 * @author Dev Rupesh Saxena
 */

public class ConfirmOderActivity extends AppCompatActivity {

    private TextView refIdTV, productTV, durationTV, amountTV, payNowBTN;
    MaterialSpinner paymentOptions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_oder);

        //set title of toolbar
        getSupportActionBar().setTitle(ApplicationConstants.CONFIRM_ORDER);

        //HomeAsUpEnabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //casting View
        refIdTV = (TextView) findViewById(R.id.refIdTV);
        productTV = (TextView) findViewById(R.id.productTV);
        durationTV = (TextView) findViewById(R.id.selectedAmountTV);
        amountTV = (TextView) findViewById(R.id.amountTV);
        payNowBTN = (TextView) findViewById(R.id.payNowBTN);

        //getting details with intent
        ConfirmOderDetails confirmOderDetails = (ConfirmOderDetails) getIntent().getSerializableExtra(ApplicationConstants.CONFIRM_OBJ);

        //setup view
        initView(confirmOderDetails);

        //setup spinner
        paymentOptions = (MaterialSpinner) findViewById(R.id.spinner_payment_options);
        paymentOptions.setItems(getResources().getStringArray(R.array.spinner_payment_options));
        paymentOptions.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

            }
        });
    }

    private void initView(ConfirmOderDetails confirmOderDetails) {
        refIdTV.setText(confirmOderDetails.getReferenceId());
        productTV.setText(confirmOderDetails.getProduct());
        durationTV.setText(confirmOderDetails.getDuration());
        amountTV.setText(confirmOderDetails.getAmount());
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
