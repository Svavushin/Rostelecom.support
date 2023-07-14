package com.example.rostelecomsupport.ui;

import static android.content.Context.ACTIVITY_SERVICE;

import android.app.ActivityManager;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.rostelecomsupport.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Dialog_featuresFragment extends DialogFragment
{
    @Override
    public void onStart()
    {
        super.onStart();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_features_dialog, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        TextView modelView = getView().findViewById(R.id.modelDeviceView);
        TextView manufacturerView = getView().findViewById(R.id.manufacturerDeviceView);
        TextView versionView = getView().findViewById(R.id.versionDeviceView);
        TextView cpuView = getView().findViewById(R.id.cpuDeviceView);
        TextView RAMView = getView().findViewById(R.id.RAMDeviceView);
        TextView ROMView = getView().findViewById(R.id.ROMDeviceView);
        TextView DISPLAYView = getView().findViewById(R.id.DISPLAYDeviceView);


        String model = Build.MODEL;
        modelView.setText(model);


        String manufacturer = Build.MANUFACTURER;
        manufacturerView.setText(manufacturer);


        String version = Build.VERSION.RELEASE;
        versionView.setText(version);



        // Получение информации о RAM устройства
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        long totalMemoryBytes = memoryInfo.totalMem;
        long availableMemoryBytes = memoryInfo.availMem;

        int totalMemoryGB = (int) (totalMemoryBytes / (1024.0 * 1024.0 * 1024.0) + 1);
        int availableMemoryGB = (int) (availableMemoryBytes / (1024.0 * 1024.0 * 1024.0) + 1);
        int usingMemoryGB = totalMemoryGB - availableMemoryGB;


        String totalMemoryGBstr = String.valueOf(totalMemoryGB);
        String usingMemoryGBstr = String.valueOf(usingMemoryGB);

        String fullMemoryRam = usingMemoryGBstr + "/" + totalMemoryGBstr + " ГБ";

        RAMView.setText(fullMemoryRam);


        // Получение информации о ROM устройства
        File internalStorage = Environment.getDataDirectory();
        StatFs stat = new StatFs(internalStorage.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        long totalInternalMemory = totalBlocks * blockSize;
        long availableInternalMemory = availableBlocks * blockSize;

        int totalInternalMemoryGB = (int) (totalInternalMemory / (1024.0 * 1024.0 * 1024.0));
        int availableInternalMemoryGB = (int) (availableInternalMemory / (1024.0 * 1024.0 * 1024.0));
        int usingInternalMemoryGB = totalInternalMemoryGB - availableInternalMemoryGB;

        String totalInternalMemoryGBstr = String.valueOf(totalInternalMemoryGB);
        String usingInternalMemoryGBstr = String.valueOf(usingInternalMemoryGB);

        String fullMemory = usingInternalMemoryGBstr + "/" + totalInternalMemoryGBstr + " ГБ";

        ROMView.setText(fullMemory);

        String display = Build.DISPLAY;
        DISPLAYView.setText(display);



    }

}
