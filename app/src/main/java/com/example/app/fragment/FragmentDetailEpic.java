package com.example.app.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.app.model.Epic;


import com.example.app.R;

import androidx.fragment.app.Fragment;

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
    private Epic mParam1;
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
    public static FragmentDetailEpic newInstance(Epic param1, String param2) {
        FragmentDetailEpic fragment = new FragmentDetailEpic();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    EditText editText,editText2,editText3;
    Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_epic, container, false);
        editText = view.findViewById(R.id.etEpicName);
        editText.setFocusable(false);
        editText.setText(mParam1.getName());

        editText3 = view.findViewById(R.id.etEpicDesc);
        editText3.setFocusable(false);
        editText3.setText(mParam1.getSummary());

//        editText2 = view.findViewById(R.id.eEpicStatus);
//        editText2.setFocusable(false);
//        editText2.setText(mParam1.getName());

        button = view.findViewById(R.id.button);
        button.setVisibility(View.GONE);

        return view;
    }

}
