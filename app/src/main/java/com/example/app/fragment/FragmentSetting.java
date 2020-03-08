package com.example.app.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.utils.MainViewModel;
import com.example.app.R;
import com.example.app.adapter.AlertAddUser;
import com.example.app.adapter.UserAdapter;
import com.example.app.model.Project;
import com.example.app.model.User;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSetting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSetting extends Fragment implements View.OnClickListener, UserAdapter.UserViewHolder.ClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Project project;
    private String PID;
    private MainViewModel model;
    EditText txtName,txtDescription, txtKey;

    Button btnAdd, btnConfirm;
    RecyclerView rvUser;
    UserAdapter mAdapter;
    public FragmentSetting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSetting.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSetting newInstance(Project param1, String param2) {
        FragmentSetting fragment = new FragmentSetting();
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
            project = getArguments().getParcelable(ARG_PARAM1);
            PID = getArguments().getString(ARG_PARAM2);
        }

        model = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
        model.getListUser().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(@Nullable ArrayList<User> users) {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_setting, container, false);
        txtName = view.findViewById(R.id.editText10);
        txtName.setText(project.getName());
        txtDescription = view.findViewById(R.id.editText12);
        txtDescription.setText(project.getDescription());
        txtKey = view.findViewById(R.id.editText11);
        txtKey.setText(project.getId());
        txtKey.setFocusable(false);

        btnAdd = view.findViewById(R.id.button9);
        btnAdd.setOnClickListener(this);
        btnConfirm = view.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);
        rvUser = view.findViewById(R.id.rvTim);
        mAdapter = new UserAdapter(this.getActivity(), model.getListUser().getValue(),this::onItemClicked);
        rvUser.setAdapter(mAdapter);
        rvUser.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view==btnAdd){
            openDialog();
        }else if (view==btnConfirm){
            project.setName(txtName.getText().toString());
            project.setDescription(txtDescription.getText().toString());
            model.projectEdit(project);
        }
    }

    public void openDialog() {
        AlertAddUser alertAddUser= new AlertAddUser();
        alertAddUser.show(getActivity().getSupportFragmentManager(), "Alert Add User");
    }

    @Override
    public void onItemClicked(int position, User user, UserAdapter adapter) {
        if (user.getEmail().equals( model.getUser().getEmail())){
            Toast.makeText(getActivity(),"Can't delete",Toast.LENGTH_LONG).show();
        }else {
            initializeAlertDialog(user);
            AlertDialog alert11 = builder.create();
            alert11.show();
        }
    }

    private AlertDialog.Builder builder;

    private void initializeAlertDialog(User user){
        builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure  ?");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        model.removeUser(user,PID);
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
    }
}
