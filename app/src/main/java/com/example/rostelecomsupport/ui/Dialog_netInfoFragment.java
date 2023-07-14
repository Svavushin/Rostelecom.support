package com.example.rostelecomsupport.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.InetAddresses;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.rostelecomsupport.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;

public class Dialog_netInfoFragment extends DialogFragment
{
    GetEInfo getEInfo = new GetEInfo();


    public void onStart() {
        super.onStart();

        new GetEInfo().execute();

        // Изменить размеры диалогового окна
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_netino_dialog, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView ipLocalAddressView1 = view.findViewById(R.id.ipLocalAddressView2);
        TextView typeConnectionView = view.findViewById(R.id.typeConnectionView2);


        WifiManager WM = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        int ip = WM.getConnectionInfo().getIpAddress();
        String ipAddressLocal = Formatter.formatIpAddress(ip);
        ipLocalAddressView1.setText(ipAddressLocal);

        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

      //  LinkProperties linkProperties = connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork());
       // List<InetAddress> dnsServers = linkProperties.getDnsServers(); // список днс серверов
       // InetAddress gateway = linkProperties.getRoutes().get(0).getGateway(); // адрес шлюза подключения


        // выдача разрешения
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }


        //
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null)
        {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
            {
                WifiManager wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ssid = wifiInfo.getSSID();

                typeConnectionView.setText(ssid);
            } else if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                {
                    TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
                    String operator = telephonyManager.getSimOperatorName();

                    typeConnectionView.setText(operator);
                }
        }





    }

    public class GetEInfo extends AsyncTask<Void,Void,String>
    {


        @Override
        protected String doInBackground(Void... voids) {
            URL url;
            BufferedReader in;
            String ipExternal = "";
            try
            {
                url = new URL("https://api.ipify.org");
                in = new BufferedReader(new InputStreamReader(url.openStream()));
                ipExternal = in.readLine();
            } catch (Exception e) {      e.printStackTrace();    }

            return ipExternal;
        }

        protected void onPostExecute(String ipExternal)
        {
            if (getView() != null)
            {
                TextView ipExternalView = getView().findViewById(R.id.ipExternalipView);
                ipExternalView.setText(ipExternal);
            }
        }
    }


    @Override
    public void onStop()
    {
        getEInfo.cancel(true);
        super.onStop();
        if (getEInfo != null)
            {
                getEInfo.cancel(true);
            }

    }

    @Override
    public void onDestroy()
    {
        getEInfo.cancel(true);
        super.onDestroy();
        super.onStop();
        if (getEInfo != null)
        {
            getEInfo.cancel(true);
        }

    }
}
