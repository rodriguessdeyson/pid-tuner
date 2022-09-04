/*package rad.com.pidtuner;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class CompareMethodsActivity extends Activity {

    protected int process, controller;
    protected double K, T, teta, Y, Kp, Ki, Kd;
    protected int decimalPlaces = 2;
    protected TextView tvSr1, tvSr2, tvSr3, tvSr4, tvKp1,tvKp2,tvKp3,tvKp4,tvKp5,tvKp6,tvKp7, tvKi1,tvKi2,tvKi3,tvKi4,tvKi5,tvKi6,tvKi7, tvKd1,tvKd2,tvKd3,tvKd4,tvKd5,tvKd6,tvKd7;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        Intent RetrieveExtras = getIntent();
        Bundle bundleRetrieve = RetrieveExtras.getExtras();

        process = bundleRetrieve.getInt(Methods.EXTRA_PROCESS);
        controller = bundleRetrieve.getInt(Methods.EXTRA_CONTROLLER);
        K = bundleRetrieve.getDouble(Methods.EXTRA_GAIN);
        T = bundleRetrieve.getDouble(Methods.EXTRA_TIME_CONSTANT);
        teta = bundleRetrieve.getDouble(Methods.EXTRA_TRANSPORT_DELAY);
        Y = bundleRetrieve.getDouble(Methods.EXTRA_LAMBIDA);
        decimalPlaces = bundleRetrieve.getInt(Methods.EXTRA_DECIMAL_PLACES);

        setContentView(R.layout.activity_compare);

        tvSr1 = (TextView)findViewById(R.id.tvSR1);
        tvSr2 = (TextView)findViewById(R.id.tvSR2);
        tvSr3 = (TextView)findViewById(R.id.tvSR3);
        tvSr4 = (TextView)findViewById(R.id.tvSR4);

        tvKp1 = (TextView)findViewById(R.id.tvKp1);
        tvKp2 = (TextView)findViewById(R.id.tvKp2);
        tvKp3 = (TextView)findViewById(R.id.tvKp3);
        tvKp4 = (TextView)findViewById(R.id.tvKp4);
        tvKp5 = (TextView)findViewById(R.id.tvKp5);
        tvKp6 = (TextView)findViewById(R.id.tvKp6);
        tvKp7 = (TextView)findViewById(R.id.tvKp7);

        tvKi1 = (TextView)findViewById(R.id.tvKi1);
        tvKi2 = (TextView)findViewById(R.id.tvKi2);
        tvKi3 = (TextView)findViewById(R.id.tvKi3);
        tvKi4 = (TextView)findViewById(R.id.tvKi4);
        tvKi5 = (TextView)findViewById(R.id.tvKi5);
        tvKi6 = (TextView)findViewById(R.id.tvKi6);
        tvKi7 = (TextView)findViewById(R.id.tvKi7);

        tvKd1 = (TextView)findViewById(R.id.tvKd1);
        tvKd2 = (TextView)findViewById(R.id.tvKd2);
        tvKd3 = (TextView)findViewById(R.id.tvKd3);
        tvKd4 = (TextView)findViewById(R.id.tvKd4);
        tvKd5 = (TextView)findViewById(R.id.tvKd5);
        tvKd6 = (TextView)findViewById(R.id.tvKd6);
        tvKd7 = (TextView)findViewById(R.id.tvKd7);

        ComputeTableMethods();
    }

    private void ComputeTableMethods()
    {
        ComputeZN(controller);
        ComputeCHR(controller, process);
        ComputeIAE(controller, process);
        ComputeITAE(controller, process);
        ComputeCC(controller);
        ComputeIMC(controller);
    }


    private void ComputeZN(int uC)
    {
        switch (uC)
        {
            case 1: //P
                tvKp1.setText(String.format("%."+String.valueOf(decimalPlaces)+"f",(T / (K * teta))));
                tvKi1.setText("-");
                tvKd1.setText("-");
                break;

            case 2: //PI
                tvKp1.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (0.9 * T / (K * teta))));
                tvKi1.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (3.33 * teta)));
                tvKd1.setText("-");
                break;

            case 3: //PID
                tvKp1.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (1.2 * T / (K * teta))));
                tvKi1.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (2 * teta)));
                tvKd1.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (0.5 * teta)));
                break;
        }
    }

    private void ComputeCHR(int uC, int uP)
    {
        switch (uP)
        {
            case 1: //Servo
                switch (uC)
                {
                    case 1: //P
                        tvKp2.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((0.3*T)/(K*teta))));
                        tvKi2.setText("-");
                        tvKd2.setText("-");

                        tvKp3.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((0.7*T)/(K*teta))));
                        tvKi3.setText("-");
                        tvKd3.setText("-");
                        break;

                    case 2: //PI
                        tvKp2.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((0.35*T)/(K*teta))));
                        tvKi2.setText(String.format("%."+String.valueOf(decimalPlaces)+"f",(1.16*T)));
                        tvKd2.setText("-");

                        tvKp3.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((0.6*T)/(K*teta))));
                        tvKi3.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (teta)));
                        tvKd3.setText("-");
                        break;

                    case 3:
                        tvKp2.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((0.6*T)/(K*teta))));
                        tvKi2.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (teta)));
                        tvKd2.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((teta/2))));

                        tvKp3.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((0.95*T)/(K*teta))));
                        tvKi3.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (1.357*teta)));
                        tvKd3.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (0.473*teta)));
                        break;
                }
                tvSr1.setText(R.string.rbServo);
                tvSr2.setText(R.string.rbServo);
                tvSr3.setText(R.string.rbServo);
                tvSr4.setText(R.string.rbServo);
                break;

            case 2: //Regulator
                switch (uC)
                {
                    case 1:
                        tvKp2.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((0.3*T)/(K*teta))));
                        tvKi2.setText("-");
                        tvKd2.setText("-");

                        tvKp3.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((0.7*T)/teta)));
                        tvKi3.setText("-");
                        tvKd3.setText("-");
                        break;
                    case 2:
                        tvKp2.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((0.6*T)/(K*teta))));
                        tvKi2.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (4*teta)));
                        tvKd2.setText("-");

                        tvKp3.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((0.7*T)/teta)));
                        tvKi3.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (2.3*teta)));
                        tvKd3.setText("-");
                        break;
                    case 3:
                        tvKp2.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((0.95*T)/(K*teta))));
                        tvKi2.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (2.375*teta)));
                        tvKd2.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (0.421*teta)));

                        tvKp3.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((1.2*T)/teta)));
                        tvKi3.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (2*teta)));
                        tvKd3.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (0.42*teta)));
                        break;
                }
                tvSr1.setText(R.string.rbRegula);
                tvSr2.setText(R.string.rbRegula);
                tvSr3.setText(R.string.rbRegula);
                tvSr4.setText(R.string.rbRegula);
                break;
        }
    }

    private void ComputeCC(int uC)
    {
        switch (uC)
        {
            case 1:
                tvKp6.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((1.03 + (0.35*(teta/T)))*(T/(K*teta)))));
                tvKi6.setText("-");
                tvKd6.setText("-");
                break;

            case 2:
                tvKp6.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((0.9 + (0.083*(teta/T)))*(T/(K*teta)))));
                tvKi6.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (((0.9 + (0.083*(teta/T)))/(1.27 + (0.6*(teta/T))))*teta)));
                tvKd6.setText("-");
                break;

            case 3:
                tvKp6.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((1.35 + (0.25*(teta/T)))*(T/(K*teta)))));
                tvKi6.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (((1.35 + (0.25*(teta/T)))/(0.54 + (0.33*(teta/T))))*teta)));
                tvKd6.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (((0.5*teta)/(1.35+(0.25*(teta/T)))))));
                break;
        }

    }

    private void ComputeIMC(int uC)
    {
        if(Y == 0)
        {
            Y = ((2*(T+teta))/3);
            Toast.makeText(this, R.string.DefaultLambida, Toast.LENGTH_SHORT).show();
        }

        switch (uC)
        {
            case 1:
                tvKp7.setText("-");
                tvKi7.setText("-");
                tvKd7.setText("-");
                break;

            case 2:
                tvKp7.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (((2*T+teta)/(K*2*Y)))));
                tvKi7.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (T +(teta/2))));
                tvKd7.setText("-");
                break;

            case 3:
                tvKp7.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (((2*T+teta)/(K*(2*Y+teta))))));
                tvKi7.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", (T+(teta/2))));
                tvKd7.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((T*teta/(2*T+teta)))));
                break;
        }
    }

    private void ComputeITAE(int uC, int uP)
    {
        switch (uP)
        {
            case 1: //Servo
                switch (uC)
                {
                    case 1:
                        tvKp5.setText("-");
                        tvKi5.setText("-");
                        tvKd5.setText("-");
                        break;

                    case 2:
                        tvKp5.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((1/K) * (0.586 * Math.pow((teta/T), (-0.916))))));
                        tvKi5.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((T/(1.03 + ((-0.165) * (teta/T)))))));
                        tvKd5.setText("-");
                        break;

                    case 3:
                        tvKp5.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((1/K) * (0.965 * Math.pow((teta/T),(-0.850))))));
                        tvKi5.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((T/(0.796 + ((-0.147) * (teta/T)))))));
                        tvKd5.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((T*(0.308 * Math.pow((teta/T),(0.929)))))));
                        break;
                }
                break;

            case 2: //Regulator
                switch (uC)
                {
                    case 1:
                        tvKp5.setText("-");
                        tvKi5.setText("-");
                        tvKd5.setText("-");
                        break;

                    case 2:
                        tvKp5.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((1/K)*(0.859 * Math.pow((teta/T),(-0.977))))));
                        tvKi5.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((T/(0.674 * Math.pow ((teta/T),(-0.68)))))));
                        tvKd5.setText("-");
                        break;

                    case 3:
                        tvKp5.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((1/K)*(1.357 * Math.pow((teta/T),(-0.947))))));
                        tvKi5.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((T/(0.842 * Math.pow((teta/T),(-0.738)))))));
                        tvKd5.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((T*(0.381 * Math.pow((teta/T),(0.995)))))));
                        break;
                }
                break;
        }

    }

    private void ComputeIAE(int uC, int uP)
    {
        switch (uP)
        {
            case 1: //Servo
                switch (uC)
                {
                    case 1:
                        tvKp4.setText("-");
                        tvKi4.setText("-");
                        tvKd4.setText("-");
                        break;

                    case 2:
                        tvKp4.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((1/K) * (0.758 * Math.pow((teta/T), (-0.861))))));
                        tvKi4.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((T/(1.02 + ((-0.323) * (teta/T)))))));
                        tvKd4.setText("-");
                        break;

                    case 3:
                        tvKp4.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((1/K) * (1.086 * Math.pow((teta/T),(-0.869))))));
                        tvKi4.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((T/(0.740 + ((-0.130) * (teta/T)))))));
                        tvKd4.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((T*(0.348 * Math.pow((teta/T),(0.914)))))));
                        break;
                }
                break;

            case 2: //Regulator
                switch (uC)
                {
                    case 1:
                        tvKp4.setText("-");
                        tvKi4.setText("-");
                        tvKd4.setText("-");
                        break;

                    case 2:
                        tvKp4.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((1/K)*(0.984 * Math.pow((teta/T),(-0.986))))));
                        tvKi4.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((T/(0.608 * Math.pow ((teta/T),(-0.707)))))));
                        tvKd4.setText("-");
                        break;

                    case 3:
                        tvKp4.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((1/K)*(1.435 * Math.pow((teta/T),(-0.921))))));
                        tvKi4.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((T/(0.878 * Math.pow((teta/T),(-0.749)))))));
                        tvKd4.setText(String.format("%."+String.valueOf(decimalPlaces)+"f", ((T*(0.482 * Math.pow((teta/T),(1.137)))))));
                        break;
                }
                break;
        }
    }
}
*/