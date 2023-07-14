package com.example.rostelecomsupport.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.rostelecomsupport.R;

public class Dialog_sendrequestFragment extends DialogFragment
{

    int count = 0;


    @Override
    public void onStart() {
        super.onStart();

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_sendrequest_dialog, container, false);




    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        Button submitBtn = view.findViewById(R.id.submitBtn2);
        EditText editTextName = view.findViewById(R.id.editTextName_1);
        EditText editTextName2 = view.findViewById(R.id.editTextName_2);
        EditText editTextName3 = view.findViewById(R.id.editTextName_3);
        EditText editTextNumAcc = view.findViewById(R.id.editTextNumAcc);
        EditText editTextNumber = view.findViewById(R.id.editTextNumber);
        EditText editTextTextMultiLine = view.findViewById(R.id.editTextTextMultiLine);



        ViewGroup parent = getActivity().findViewById(R.id.requestsLayout);


        // обработка кнопки

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                String title = editTextNumAcc.getText().toString();



                // если ЛС не пустой то создается айтем
                if (!editTextNumAcc.getText().toString().trim().isEmpty())
                {
                    count++;
                    Request request = new Request(title, count);
                    View requestView = LayoutInflater.from(getContext()).inflate(R.layout.request_item,
                            parent, false);
                    TextView titleView = requestView.findViewById(R.id.titleView);
                    TextView countRequsetView = requestView.findViewById(R.id.countRequest);
                    titleView.setText(request.getTitle());
                    countRequsetView.setText(String.valueOf(request.getCount()));

                    // Добавление элемента в список
                    parent.addView(requestView);
                }



                // очищаются эдити
                editTextName.setText("");
                editTextName2.setText("");
                editTextName3.setText("");
                editTextNumAcc.setText("");
                editTextNumber.setText("");
                editTextTextMultiLine.setText("");

                // закрытие клавиатуры
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                dismiss();
            }
        });


    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class Request {
        private int count;
        private String title;
        private String description;

        public Request(String title, int count)
        {
            this.count = count;
            this.title = title;

        }

        public int getCount()
        {
            return count;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }

}
