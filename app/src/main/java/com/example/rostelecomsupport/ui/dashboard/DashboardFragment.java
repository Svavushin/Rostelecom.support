package com.example.rostelecomsupport.ui.dashboard;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.rostelecomsupport.R;
import com.example.rostelecomsupport.databinding.FragmentDashboardBinding;
import com.example.rostelecomsupport.ui.Dialog_featuresFragment;
import com.example.rostelecomsupport.ui.Dialog_netInfoFragment;
import com.example.rostelecomsupport.ui.Dialog_safetyFragment;
import com.example.rostelecomsupport.ui.Dialog_speedTestFragment;


public class DashboardFragment extends Fragment  {

    public FragmentDashboardBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        // нажатия на cardview с тестом скорости
        CardView speedtest_CardView = root.findViewById(R.id.speedtest_CardView);

        speedtest_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_speedTestFragment dialog_speedTest = new Dialog_speedTestFragment();
                dialog_speedTest.show(getChildFragmentManager(), "dialog_test");
            }
        });

        // нажатия на cardview с инфой
        CardView netInfo_CardView = root.findViewById(R.id.netInfo_CardsView);

        netInfo_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog_netInfoFragment dialog_netInfo = new Dialog_netInfoFragment();
                dialog_netInfo.show(getChildFragmentManager(), "dialog_info");
            }
        });

        CardView safety_CardView = root.findViewById(R.id.safety_CardView);

        safety_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog_safetyFragment dialog_safety = new Dialog_safetyFragment();
                dialog_safety.show(getChildFragmentManager(), "dialog_info");
            }
        });


        CardView features_CardView = root.findViewById(R.id.featuresCardView);

        features_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog_featuresFragment dialog_features = new Dialog_featuresFragment();
                dialog_features.show(getChildFragmentManager(), "dialog_info");
            }
        });



        return root;
    }




    // проверка статуса подключения к сети
    public class NetworkUtils {

        public static void showInternetStatus(Context context, TextView inetStatusView) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    inetStatusView.setText("Подключено");
                } else {
                    inetStatusView.setText("Подключение отсуствует");
                }
            }
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }


}

