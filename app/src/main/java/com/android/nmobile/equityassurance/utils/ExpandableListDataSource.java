package com.android.nmobile.equityassurance.utils;

import android.content.Context;

import com.android.nmobile.equityassurance.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Dev Rupesh Saxena
 */

public class ExpandableListDataSource {

    public static HashMap<String, List<String>> getData(Context context) {
        HashMap<String, List<String>> expandableListData = new HashMap<>();

        List<String> drawer_main_options = Arrays.asList(context.getResources().getStringArray(R.array.drawer_main_options));
        List<String> child_travel_insurance = Arrays.asList(context.getResources().getStringArray(R.array.child_travel_insurance));
        List<String> child_microlife_insurance = Arrays.asList(context.getResources().getStringArray(R.array.child_microlife_insurance));
        List<String> child_micro_insurance = Arrays.asList(context.getResources().getStringArray(R.array.child_micro_insurance));
        List<String> child_home_insurance = Arrays.asList(context.getResources().getStringArray(R.array.child_home_insurance));
        List<String> child_claims = Arrays.asList(context.getResources().getStringArray(R.array.child_claims));
        List<String> child_buy_now = Arrays.asList(context.getResources().getStringArray(R.array.child_buy_now));
        List<String> child_make_payment = Arrays.asList(context.getResources().getStringArray(R.array.child_make_payment));
        List<String> child_contact_us = Arrays.asList(context.getResources().getStringArray(R.array.child_contact_us));
        List<String> child_about_us = Arrays.asList(context.getResources().getStringArray(R.array.child_about_us));
        List<String> child_logout = Arrays.asList(context.getResources().getStringArray(R.array.child_logout));
        List<String> child_exit = Arrays.asList(context.getResources().getStringArray(R.array.child_exit));

        expandableListData.put(drawer_main_options.get(0), child_travel_insurance);
        expandableListData.put(drawer_main_options.get(1), child_microlife_insurance);
        expandableListData.put(drawer_main_options.get(2), child_micro_insurance);
        expandableListData.put(drawer_main_options.get(3), child_home_insurance);
        expandableListData.put(drawer_main_options.get(4), child_claims);
        expandableListData.put(drawer_main_options.get(5), child_buy_now);
        expandableListData.put(drawer_main_options.get(6), child_make_payment);
        expandableListData.put(drawer_main_options.get(7), child_contact_us);
        expandableListData.put(drawer_main_options.get(8), child_about_us);
        expandableListData.put(drawer_main_options.get(9), child_logout);
        expandableListData.put(drawer_main_options.get(10), child_exit);
        return expandableListData;
    }
}
