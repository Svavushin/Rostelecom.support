package com.example.rostelecomsupport.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.rostelecomsupport.R;
import com.example.rostelecomsupport.ui.dashboard.DashboardFragment;
import com.google.android.material.transition.platform.MaterialContainerTransform;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Semaphore;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;

public class Dialog_speedTestFragment extends DialogFragment
{
    private SpeedTestTask speedTestTask;
    private PingTask pingTask;


    public void onStart() {
        super.onStart();

        Button testBtn = getView().findViewById(R.id.testNetButton3);
        testBtn.setOnClickListener(new View.OnClickListener() {

        ProgressBar progressBarView = getView().findViewById(R.id.progressBarView);
        ProgressTracker progressTracker = new ProgressTracker(progressBarView);



            //TextView speedView = view.findViewById(R.id.speedView);
            TextView inetStatusView = getView().findViewById(R.id.inetStatusView);


            @Override
            public void onClick(View view)
            {
                SpeedTestTask speedTestTask = new SpeedTestTask(progressTracker);
                PingTask pingTask = new PingTask(progressTracker);
                progressTracker.progress = 0;

                speedTestTask.execute();


                pingTask.execute();

                DashboardFragment.NetworkUtils.showInternetStatus(getContext(), inetStatusView);

            }




        });

        CardView speedTestView = getView().findViewById(R.id.speedtest_CardView);



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
        return inflater.inflate(R.layout.fragment_speeddest_dialog, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentActivity Activity = getActivity();


        ProgressBar progressBarView = view.findViewById(R.id.progressBarView);
        ProgressTracker progressTracker = new ProgressTracker(progressBarView);

        Button testBtn = view.findViewById(R.id.testNetButton3);

        DashboardFragment dg = new DashboardFragment();



    }


    // обновление прогресса
    class ProgressTracker
    {
        private int progress = 0;
        private ProgressBar progressBar;


        public ProgressTracker(ProgressBar progressBar)
        {
            this.progressBar = progressBar;
        }

        public synchronized void updateProgress(int value)
        {
            progress += value;
            progressBar.setProgress(progress);
        }

    }

    // обработка теста скорости
    class SpeedTestTask extends AsyncTask<Void, Void, SpeedTestReport>
    {
        private ProgressTracker progressTracker;

        SpeedTestTask(ProgressTracker progressTracker)
        {
            this.progressTracker = progressTracker;
        }

        TextView speedView = getView().findViewById(R.id.speedView);
        Button testBtn = getView().findViewById(R.id.testNetButton3);


        final Semaphore semaphore = new Semaphore(0);
        SpeedTestReport Report[] = new SpeedTestReport[1];

        @Override
        public SpeedTestReport doInBackground(Void... voids)
        {
            for (int i = 0; i < 50; i++)
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressTracker.updateProgress(1);
            }


            SpeedTestSocket speedTestSocket = new SpeedTestSocket();


            speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {
                @Override
                public void onCompletion(SpeedTestReport speedTestReport) {
                    Report[0] = speedTestReport;
                    semaphore.release();

                }


                @Override
                public void onProgress(float percent, SpeedTestReport speedTestReport)
                {

                    getActivity().runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            speedView.setText(String.format("%.2f", percent) + '%');
                        }
                    });
                }

                @Override
                public void onError(SpeedTestError speedTestError, String errorMessage) {
                    Log.e("SpeedTest", "Error: " + speedTestError + ", " + errorMessage);

                   getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            speedView.setText(errorMessage);
                        }
                    });
                }
            });
            // запускаем тест скорости, результат будет меньше, так как подключаемся к серверу, который находится слишком далеко
            speedTestSocket.startDownload("ftp://speedtest:speedtest@ftp.otenet.gr/test1Mb.db");

            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return Report[0];
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // update progress in UI
        }

        @Override
        protected void onPreExecute() {
            testBtn.setEnabled(false);
        }

        @Override
        protected void onPostExecute(SpeedTestReport speedTestReport) {

            if (speedTestReport != null)
            {
                double speedMbpsD = speedTestReport.getTransferRateBit().doubleValue() / 1000000.0;
                float speedMbpsF = (float) speedMbpsD;
                speedView.setText(String.format("%.2f", speedMbpsF) + " Mbit/s");
              /*  PingTask pingTask = new PingTask(progressTracker);
                pingTask.execute();*/
            }

        }

    }

    // Получаем пинг
    class PingTask extends AsyncTask<String, Void, Long>
    {
        private ProgressTracker progressTracker;

        Button testBtn = getView().findViewById(R.id.testNetButton3);

        PingTask(ProgressTracker progressTracker)
        {
            this.progressTracker = progressTracker;
        }

        TextView pingView = getView().findViewById(R.id.pingStatusView);

        @Override
        protected Long doInBackground(String... strings)
        {
            for (int i = 0; i < 50; i++)
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressTracker.updateProgress(1);
            }


            URL url = null;
            try {
                url = new URL("http://ftp.otenet.gr/");  // инициализируем URL
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            HttpURLConnection connection = null;        // реализация подключения по Http
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            connection.setConnectTimeout(10000);            // установка тайм-аута подключения в миллисекундах
            long startTime = System.currentTimeMillis();
            try {
                connection.connect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            long endTime = System.currentTimeMillis();
            long pingTime = endTime - startTime;  // вычисляем задержку

            return pingTime;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected void onPostExecute(Long pingTime)
        {
            pingView.setText(String.valueOf(pingTime) + " ms");
            testBtn.setEnabled(true);
        }
    }




    @Override
    public void onStop() {
        super.onStop();
        if (speedTestTask != null)
        {
            speedTestTask.cancel(true);
        }
        if (pingTask != null)
        {
            pingTask.cancel(true);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        super.onStop();
        if (speedTestTask != null) {
            speedTestTask.cancel(true);
        }
        if (pingTask != null) {
            pingTask.cancel(true);
        }
    }
}
