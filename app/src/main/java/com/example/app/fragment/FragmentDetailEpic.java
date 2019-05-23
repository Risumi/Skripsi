package com.example.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDetailEpic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDetailEpic extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentDetailEpic() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDetailEpic.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDetailEpic newInstance(String param1, String param2) {
        FragmentDetailEpic fragment = new FragmentDetailEpic();
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

    EditText editText,editText2,editText3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_epic, container, false);
        editText = view.findViewById(R.id.etEpicName);
        editText.setEnabled(false);

        editText3 = view.findViewById(R.id.etEpicDesc);
        editText3.setEnabled(false);

        editText2 = view.findViewById(R.id.eEpicStatus);
        editText2.setEnabled(false);

        return view;
    }

}
