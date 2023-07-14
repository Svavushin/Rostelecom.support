package com.example.rostelecomsupport.ui.notifications;

import static com.yandex.mapkit.map.TextStyle.Placement.BOTTOM;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rostelecomsupport.R;
import com.example.rostelecomsupport.databinding.FragmentNotificationsBinding;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.TextStyle;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class NotificationsFragment extends Fragment  {
    private MapView mapView;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView addressView = root.findViewById(R.id.address);
        TextView addressView_2 = root.findViewById(R.id.address_2);

        ImageButton btn1 = root.findViewById(R.id.permissionsBtn);
        ImageButton btn2 = root.findViewById(R.id.gotoBtn_2);





                                // Стиль для текста маркера //
        TextStyle markerTextStyle = new TextStyle(12, Color.rgb(114, 52, 136), Color.WHITE,
                                                    BOTTOM,-45,true,
                                                     true);

        mapView = root.findViewById(R.id.mapview);

            // инициализация карты
        mapView.getMap().move(new CameraPosition(new Point(50.2796100, 127.5405000), 12.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        Point point1 = new Point(50.264513, 127.531584);
        Point point2 = new Point(50.263893, 127.531100);

        // получение геоеодера гугловского
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        // обработка адресов

        try {
            // первый адрес //
            List<Address> addresses = geocoder.getFromLocation(50.264513, 127.531584, 1);
            if (addresses.size() > 0)
            {
                Address address = addresses.get(0);

                String thoroughfare = address.getThoroughfare(); // улица
                String featureName = address.getFeatureName(); // номер дома
                String city = address.getLocality();
                String Address = city + ", " + thoroughfare + " " + featureName;

                addressView.setText(Address);

            }

            // второй адрес //
            List<Address> addresses_2 = geocoder.getFromLocation(50.263893, 127.531100, 1);
            if (addresses_2.size() > 0)
            {
                Address address_2 = addresses_2.get(0);

                String thoroughfare_2 = address_2.getThoroughfare(); // улица
                String featureName_2 = address_2.getFeatureName(); // номер дома
                String city_2 = address_2.getLocality();
                String Address_2 = city_2 + ", " + thoroughfare_2 + " " + featureName_2;

                addressView_2.setText(Address_2);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        ImageProvider imageIcon = ImageProvider.fromResource(getContext(),R.mipmap.ic_marker_foreground);


        PlacemarkMapObject placemark1 = mapView.getMap().getMapObjects().addPlacemark(point1,imageIcon);
        PlacemarkMapObject placemark2 = mapView.getMap().getMapObjects().addPlacemark(point2,imageIcon);

        placemark1.setText("Ростелеком", markerTextStyle);
        placemark2.setText("Ростелеком", markerTextStyle);


        placemark1.setOpacity(1f);
        placemark2.setOpacity(1f);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CameraPosition cameraPosition = new CameraPosition(point1,18.0f,0,0);
                mapView.getMap().move(cameraPosition,
                                new Animation(Animation.Type.SMOOTH,1),null);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CameraPosition cameraPosition = new CameraPosition(point2,18.0f,0,0);
                mapView.getMap().move(cameraPosition,
                                new Animation(Animation.Type.SMOOTH,1),null);
            }
        });


        ImageButton callBtn = root.findViewById(R.id.callBtn);
        ImageButton vkBtn = root.findViewById(R.id.vkBtn);
        ImageButton webBtn = root.findViewById(R.id.webBtn);



        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String phoneNumber = "tel:88003018189";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phoneNumber));
                startActivity(intent);
            }
        });

        vkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String groupUrl = "https://vk.com/rostelecom";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(groupUrl));
                startActivity(intent);
            }
        });

        webBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String groupUrl = "https://amur.rt.ru/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(groupUrl));
                startActivity(intent);
            }
        });


        return root;
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    public void onStart() {
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
        super.onStart();
    }
}