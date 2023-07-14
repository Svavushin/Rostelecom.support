package com.example.rostelecomsupport.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rostelecomsupport.R;
import com.example.rostelecomsupport.databinding.FragmentHomeBinding;
import com.example.rostelecomsupport.ui.Dialog_netInfoFragment;
import com.example.rostelecomsupport.ui.Dialog_sendrequestFragment;
import com.example.rostelecomsupport.ui.Dialog_speedTestFragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        CardView cardView_request = view.findViewById(R.id.cardView_request);

        cardView_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)

            {
                Dialog_sendrequestFragment dialog_sendrequestFragment = new Dialog_sendrequestFragment();
                dialog_sendrequestFragment.show(getChildFragmentManager(), "dialog_info");
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}