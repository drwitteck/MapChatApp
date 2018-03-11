package com.example.derekwitteck.mapchatapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.derekwitteck.mapchatapp.GetPartners;
import com.example.derekwitteck.mapchatapp.Partner;
import com.example.derekwitteck.mapchatapp.R;

import java.util.List;

public class PartnersFragment extends Fragment {
    private TextView results;
    GetPartners partnerList;
    Partner partner;

    public PartnersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Get widget reference from XML layout
        View v = inflater.inflate(R.layout.fragment_partners, container, false);
        results = v.findViewById(R.id.json_data);

        List<Partner> listOfPartners = partnerList.getPartners();



        // Empty the TextView
        results.setText((CharSequence) listOfPartners);

        // Inflate the layout for this fragment
        return v;
    }
}
