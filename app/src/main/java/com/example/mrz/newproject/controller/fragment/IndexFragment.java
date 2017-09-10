package com.example.mrz.newproject.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrz.newproject.R;

/**
 * Created by 那个谁 on 2017/9/10.
 * 奥特曼打小怪兽
 * 作用：课表
 */

public class IndexFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_index,null);
        return view;
    }
}
