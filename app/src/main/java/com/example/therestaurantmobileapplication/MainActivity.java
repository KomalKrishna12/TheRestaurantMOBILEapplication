package com.example.therestaurantmobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.therestaurantmobileapplication.Database.User;
import com.example.therestaurantmobileapplication.Database.UserDAO;
import com.example.therestaurantmobileapplication.Database.UserDatabase;

public class MainActivity extends AppCompatActivity {
    TextView textView,profile_txt;
    Button profile_btn;
    Switch aSwitch;
    Boolean isBooked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.welcoe_txt);
        profile_txt=findViewById(R.id.profile_txt);
        profile_btn=findViewById(R.id.profile_btn);

        String name=getIntent().getStringExtra("n");
        textView.setText("Welcome "+name);
        aSwitch=findViewById(R.id.switch2);

         User u = new User();
        UserDAO dao=UserDatabase.getInstance(getApplicationContext()).userDAO();
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String psw=new SignInSignUpActivity().signInPassword;
                Boolean isBooked=u.getBooked();
                if (isBooked) {
                    profile_txt.setText("Hello " + name + "\nYour table is booked");
                } else if(!isBooked) {
                    profile_txt.setText("Hello " + name + "\nYour table is not booked");
                }

            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            u.setBooked(b);
                            dao.registerUser(u);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Booked Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();

                }
                else if(!b) {
                   u.setBooked(false);
                   dao.registerUser(u);
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Toast.makeText(MainActivity.this, "Table is not booked", Toast.LENGTH_SHORT).show();
                       }
                   });
                }
            }
        });
    }
}