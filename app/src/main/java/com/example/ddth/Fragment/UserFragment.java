package com.example.ddth.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ddth.Common.Common;
import com.example.ddth.R;


public class UserFragment extends Fragment {

    TextView txtFullName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_user, container, false);
        txtFullName = v.findViewById(R.id.txt_fullName);
        txtFullName.setText(Common.currentUser.getName());





        return v;
    }
}