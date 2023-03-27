package com.example.weatherapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherapplication.viewmodel.MyViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link sortie#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sortie extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    public sortie() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sortie.
     */
    // TODO: Rename and change types and number of parameters
    public static sortie newInstance(String param1, String param2, String param3, int tag) {
        sortie fragment = new sortie();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putInt("Tag", tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            tag = getArguments().getInt("Tag");
        }
    }

    AppCompatButton btn;
    MyViewModel viewModel;
    TextView name,time,friends;
    int tag;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sortie, container, false);
        btn = (AppCompatButton) view.findViewById(R.id.editbutton);
        name = (TextView) view.findViewById(R.id.fragname);
        time = (TextView) view.findViewById(R.id.fragtime);
        friends = (TextView) view.findViewById(R.id.fragfriends);

        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        name.setText(mParam1);
        friends.setText(mParam2);
        time.setText(mParam3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(container.getContext(), Activity3.class);
                intent.putExtra("showdelete", true);
                intent.putExtra("idoffrag1", tag);
                startActivityForResult(intent, 2);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == 2) {
            if (resultCode == 10) {
                //code for deleting frag
                // delete frag from database
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                sortie frag = (sortie) manager.findFragmentByTag(Integer.toString(this.tag));
                transaction.remove(frag);

                transaction.commit();
            }else{
                if(resultCode == 20){
                    //code to confirm
                    FragmentManager manager = getParentFragmentManager();
                    sortie frag = (sortie) manager.findFragmentByTag(Integer.toString(this.tag));
                    FragmentTransaction transaction = manager.beginTransaction();

                    transaction.detach(frag).attach(frag).commit();
                }
            }

        }
    }
}