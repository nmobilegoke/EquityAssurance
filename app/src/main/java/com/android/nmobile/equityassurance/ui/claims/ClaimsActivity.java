package com.android.nmobile.equityassurance.ui.claims;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.nmobile.equityassurance.R;
import com.android.nmobile.equityassurance.adapters.InsuranceCategoriesAdapter;
import com.android.nmobile.equityassurance.dto.InsuranceCategorie;
import com.android.nmobile.equityassurance.utils.ApplicationConstants;
import com.android.nmobile.equityassurance.utils.RecyclerItemClickListener;
import com.android.nmobile.equityassurance.utils.VerticalSpaceItemDecoration;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Dev Rupesh Saxena
 */

public class ClaimsActivity extends AppCompatActivity {

    private int VERTICAL_ITEM_SPACE = 5;
    private RecyclerView recyclerView;
    private HashMap<String, List<String>> mExpandableListData;
    private SliderLayout mDemoSlider;
    private ArrayList<InsuranceCategorie> categories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claims);

        //set title of toolbar
        getSupportActionBar().setTitle(ApplicationConstants.CLAIMS);

        //HomeAsUpEnabled
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setup data for options
        categories = new ArrayList<InsuranceCategorie>();
        prepareDataSource();

        //setup recyclerview
        // 1. get a reference to recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTitles);


        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(ClaimsActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        // TODO Handle item click
                        InsuranceCategorie insuranceCategorie = categories.get(position);
                        startActivity(new Intent(insuranceCategorie.getIntent()));
                        overridePendingTransition(R.anim.push_left_in_activity_start, R.anim.push_left_out_activity_start);
                    }
                })
        );
        recyclerView.setAdapter(new InsuranceCategoriesAdapter(categories, this));

        //code for slider
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put(ApplicationConstants.TRAVEL_INSURANCE, R.drawable.banner_4);
        file_maps.put(ApplicationConstants.MICROLIFE_INSURANCE, R.drawable.banner_2);
        file_maps.put(ApplicationConstants.MICRO_INSURANCE, R.drawable.banner_3);
        file_maps.put(ApplicationConstants.HOME_INSURANCE, R.drawable.banner_1);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(ClaimsActivity.this);
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

    }

    private void prepareDataSource() {
        categories.add(new InsuranceCategorie(ApplicationConstants.VEHICLE_REGISTRATION, new Intent(ClaimsActivity.this, VehicleRegistrationActivity.class)));
        categories.add(new InsuranceCategorie(ApplicationConstants.DRIVER_DETAILS, new Intent(ClaimsActivity.this, DriverDetailsActivity.class)));
        categories.add(new InsuranceCategorie(ApplicationConstants.PARTICULAR_OF_THEFT_FIRE_INCIDENT, new Intent(ClaimsActivity.this, ParticularsTheftActivity.class)));
        categories.add(new InsuranceCategorie(ApplicationConstants.DAMAGE_TO_INSURED_VEHICLE, new Intent(ClaimsActivity.this, DamageToInsuredVehicleActivity.class)));
        categories.add(new InsuranceCategorie(ApplicationConstants.THIRD_PARTIES_INVOLVED_IN_THE_ACCIDENT, new Intent(ClaimsActivity.this, ThirdPartiesInvolvedAccidentActivity.class)));

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