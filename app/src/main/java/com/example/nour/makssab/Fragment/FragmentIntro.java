package com.example.nour.makssab.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nour.makssab.R;
import com.heinrichreimersoftware.materialintro.app.SlideFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentIntro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentIntro extends SlideFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView mImageViewIntro;
    private Button mButtonIntro;


    public FragmentIntro() {
        // Required empty public constructor
    }

    @Override
    public boolean canGoForward() {
        return false;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentIntro.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentIntro newInstance(String param1, String param2) {
        FragmentIntro fragment = new FragmentIntro();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro, container, false);
        mImageViewIntro= (ImageView) view.findViewById(R.id.ivIntroImage1);
        mImageViewIntro.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivIntroImage1:
                getActivity().finish();
                break;
        }
    }
}
