package com.android.nmobile.equityassurance.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.nmobile.equityassurance.ui.auto_insurance.AutoInsuranceActivity;
import com.android.nmobile.equityassurance.ui.claims.ClaimsActivity;
import com.android.nmobile.equityassurance.ui.home_insurance.HomeInsuranceActivity;
import com.android.nmobile.equityassurance.ui.micro_insurance.MicroInsuranceActivity;
import com.android.nmobile.equityassurance.ui.microlife_insurance.MicroLifeInsuranceActivity;
import com.android.nmobile.equityassurance.R;
import com.android.nmobile.equityassurance.ui.travel_insurance.TravelInsuranceActivity;
import com.android.nmobile.equityassurance.adapters.CustomExpandableListAdapter;
import com.android.nmobile.equityassurance.adapters.DashboardOptionsAdapter;
import com.android.nmobile.equityassurance.dto.DashboardOption;
import com.android.nmobile.equityassurance.utils.ApplicationConstants;
import com.android.nmobile.equityassurance.utils.ExpandableListDataSource;
import com.android.nmobile.equityassurance.utils.RecyclerItemClickListener;
import com.android.nmobile.equityassurance.utils.SessionManager;
import com.android.nmobile.equityassurance.utils.VerticalSpaceItemDecoration;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author Dev Rupesh Saxena
 */

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;
    private RecyclerView recyclerView;
    ArrayList<DashboardOption> dashboardOptions;
    private ExpandableListView mExpandableListView;
    private ExpandableListAdapter mExpandableListAdapter;
    private List<String> mExpandableListTitle;
    private int VERTICAL_ITEM_SPACE = 5;
    private HashMap<String, List<String>> mExpandableListData;
    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup data for options
        dashboardOptions = new ArrayList<DashboardOption>();
        prepareDataSource();

        //setup recyclerview
        // 1. get a reference to recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTitles);


        // 2. set layoutManger
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        // TODO Handle item click
                        DashboardOption dashboardOption = dashboardOptions.get(position);
                        /*if (!dashboardOption.getTitle().matches(ApplicationConstants.LOGOUT)) {
                            startActivity(dashboardOption.getIntent());
                            overridePendingTransition(R.anim.push_left_in_activity_start, R.anim.push_left_out_activity_start);

                        } else {
                            SessionManager.intialize(MainActivity.this);
                            SessionManager.clearAll();
                            finish();
                        }*/

                        if (dashboardOption.getTitle().matches(ApplicationConstants.LOGOUT)) {
                            SessionManager.intialize(MainActivity.this);
                            SessionManager.clearAll();
                            finish();
                        } else if (dashboardOption.getTitle().matches(ApplicationConstants.EXIT)) {
                            finish();
                        } else if (dashboardOption.getTitle().matches(ApplicationConstants.AGENT)) {

                        } else {
                            startActivity(dashboardOption.getIntent());
                            overridePendingTransition(R.anim.push_left_in_activity_start, R.anim.push_left_out_activity_start);
                        }

                    }
                })
        );
        recyclerView.setAdapter(new DashboardOptionsAdapter(dashboardOptions, this));

        //code for slider
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put(ApplicationConstants.TRAVEL_INSURANCE, R.drawable.banner_4);
        file_maps.put(ApplicationConstants.MICROLIFE_INSURANCE, R.drawable.banner_2);
        file_maps.put(ApplicationConstants.MICRO_INSURANCE, R.drawable.banner_3);
        file_maps.put(ApplicationConstants.HOME_INSURANCE, R.drawable.banner_1);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(MainActivity.this);
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

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        mExpandableListView = (ExpandableListView) findViewById(R.id.navList);

        initItems();

        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.nav_header, null, false);
        mExpandableListView.addHeaderView(listHeaderView);
        mExpandableListData = ExpandableListDataSource.getData(this);
        mExpandableListTitle = new ArrayList();

        Resources res = getResources();
        Collections.addAll(mExpandableListTitle, res.getStringArray(R.array.drawer_main_options));
        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initItems() {
        items = getResources().getStringArray(R.array.drawer_main_options);
    }

    private void addDrawerItems() {
        mExpandableListAdapter = new CustomExpandableListAdapter(this, mExpandableListTitle, mExpandableListData);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(mExpandableListTitle.get(groupPosition).toString());
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle(R.string.nav_menus);
            }
        });

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String selectedItem = ((List) (mExpandableListData.get(mExpandableListTitle.get(groupPosition))))
                        .get(childPosition).toString();
                switch (selectedItem) {
                    case ApplicationConstants.EQUITY_SCHENGEN_TRAVEL_INSURANCE:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;

                    case ApplicationConstants.EQUITY_NON_SCHENGEN_TRAVEL_INSURANCE:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;

                    case ApplicationConstants.STUDENT_TRAVEL_INSURANCE:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;

                    case ApplicationConstants.EQUITY_PASSENGER_INSURANCE:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;

                    case ApplicationConstants.EQUITY_EDUCATION_ACCUMULATION_PLAN:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;

                    case ApplicationConstants.EQUITY_LEGACY_ASSURANCE_PLANE:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;

                    case ApplicationConstants.EQUITY_MICRO_SAVINGS_PLAN:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;

                    case ApplicationConstants.EQUITY_TARGET_INVESTMENT_PLAN:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;

                    case ApplicationConstants.EQUITY_MICRO_CREDIT_PLAN:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;

                    case ApplicationConstants.EQUI_CARE:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;

                    case ApplicationConstants.EQUITY_STUDENT_ACCIDENT_INSURANCE:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;

                    case ApplicationConstants.EQUI_SECURE:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;

                    case ApplicationConstants.HOME_INSURANCE:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;
                    case ApplicationConstants.CLAIMS:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;
                    case ApplicationConstants.BUY_NOW:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;
                    case ApplicationConstants.MAKE_PAYMENT:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;

                    case ApplicationConstants.CONTACT_US:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;

                    case ApplicationConstants.ABOUT_US:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        getSupportActionBar().setTitle(selectedItem);
                        break;

                    case ApplicationConstants.LOGOUT:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        break;

                    case ApplicationConstants.EXIT:
                        Toast.makeText(MainActivity.this, "" + selectedItem, Toast.LENGTH_SHORT).show();
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.nav_menus);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void prepareDataSource() {
        dashboardOptions.add(new DashboardOption(ApplicationConstants.AUTO_INSURANCE, R.drawable.logo_auto_ins, new Intent(MainActivity.this, AutoInsuranceActivity.class)));
        dashboardOptions.add(new DashboardOption(ApplicationConstants.TRAVEL_INSURANCE, R.drawable.logo_travel_ins, new Intent(MainActivity.this, TravelInsuranceActivity.class)));
        dashboardOptions.add(new DashboardOption(ApplicationConstants.MICROLIFE_INSURANCE, R.drawable.logo_microlife_ins, new Intent(MainActivity.this, MicroLifeInsuranceActivity.class)));
        dashboardOptions.add(new DashboardOption(ApplicationConstants.MICRO_INSURANCE, R.drawable.logo_micro_ins, new Intent(MainActivity.this, MicroInsuranceActivity.class)));
        dashboardOptions.add(new DashboardOption(ApplicationConstants.HOME_INSURANCE, R.drawable.logo_home_ins, new Intent(MainActivity.this, HomeInsuranceActivity.class)));
        dashboardOptions.add(new DashboardOption(ApplicationConstants.CLAIMS, R.drawable.logo_claims, new Intent(MainActivity.this, ClaimsActivity.class)));
        //dashboardOptions.add(new DashboardOption(ApplicationConstants.BUY_NOW, R.drawable.logo_buy_now, new Intent(MainActivity.this, BuyNowActivity.class)));
        dashboardOptions.add(new DashboardOption(ApplicationConstants.MAKE_PAYMENT, R.drawable.logo_make_payment, new Intent(MainActivity.this, MakePaymentActivity.class)));
        dashboardOptions.add(new DashboardOption(ApplicationConstants.AGENT, R.drawable.logo_agent, null));
        dashboardOptions.add(new DashboardOption(ApplicationConstants.CONTACT_US, R.drawable.logo_contact_us, new Intent(MainActivity.this, ContactUsActivity.class)));
        dashboardOptions.add(new DashboardOption(ApplicationConstants.ABOUT_US, R.drawable.logo_about_us, new Intent(MainActivity.this, AboutUsActivity.class)));
        dashboardOptions.add(new DashboardOption(ApplicationConstants.LOGOUT, R.drawable.logo_logout, null));
        dashboardOptions.add(new DashboardOption(ApplicationConstants.EXIT, R.drawable.logo_exit, null));
    }
}
