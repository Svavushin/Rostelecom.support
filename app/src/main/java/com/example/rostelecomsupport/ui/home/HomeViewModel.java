package com.example.rostelecomsupport.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HomeViewModel extends ViewModel {


    private final MutableLiveData<String> mText;

    public HomeViewModel() throws IOException {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

    }

    public LiveData<String> getText() {

        return mText;
    }
}