package com.example.therestaurantmobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.therestaurantmobileapplication.Database.User;
import com.example.therestaurantmobileapplication.Database.UserDAO;
import com.example.therestaurantmobileapplication.Database.UserDatabase;

public class SignInSignUpActivity extends AppCompatActivity {
    private EditText signInemail, signInpass, signupName, signUpemail, signUppass, signUpRePass;
    private Button signIn, signUp;
    private TextView enterHere;
    private LinearLayout layout;
    String signInUserID,signInPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_sign_up);

        signInemail = findViewById(R.id.signInemail);
        enterHere = findViewById(R.id.enterheree);
        signInpass = findViewById(R.id.signInpass);
        signUpemail = findViewById(R.id.signUpemail);
        signUppass = findViewById(R.id.signUppass);
        signIn = findViewById(R.id.signIn);
        signupName = findViewById(R.id.signUpname);
        signUp = findViewById(R.id.signUp);
        signUpRePass = findViewById(R.id.signUppass2);
        layout = findViewById(R.id.layoutt);

        UserDatabase database=UserDatabase.getInstance(getApplicationContext());
        UserDAO dao=database.userDAO();
        User muser=new User();

        enterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout.setVisibility(View.VISIBLE);

            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInUserID=signInemail.getText().toString().trim();
                signInPassword=signInpass.getText().toString().trim();

                if (signInUserID.isEmpty()) {
                    signInemail.setError("Please enter your email.");
                }
                if (signInPassword.isEmpty()) {
                    signInpass.setError("Please enter your password.");
                }
                else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user=dao.login(signInUserID,signInPassword);
                            if(user==null)
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignInSignUpActivity.this, "invalid email_ID and password", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            else
                            {
                                startActivity(new Intent(SignInSignUpActivity.this,MainActivity.class)
                                .putExtra("n",dao.getName(signInUserID,signInPassword)));
                            }
                        }
                    }).start();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signUpemail.getText().toString().isEmpty()) {
                    signUpemail.setError("Please enter your email.");
                }
                if (signUppass.getText().toString().isEmpty()) {
                    signUppass.setError("Please enter your password.");
                }
                if (signupName.getText().toString().isEmpty()) {
                    signupName.setError("Please enter your name.");
                }
                if (signUpRePass.getText().toString().isEmpty()) {
                    signUpRePass.setError("Please enter your password again.");
                    if (signUppass.getText().toString()!=(signUpRePass.getText().toString())) {
                        //mainPage();
                        signUpRePass.setError("password mismatch");
                    }
                } else {
                    String name=signupName.getText().toString();
                    String emailID=signUpemail.getText().toString().trim();
                    String password=signUpRePass.getText().toString().trim();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            muser.setName(name);
                            muser.setUserID(emailID);
                            muser.setPassword(password);
                            dao.registerUser(muser);
                            runOnUiThread(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(SignInSignUpActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                            layout.setVisibility(View.INVISIBLE);
                                        }
                                    }
                            );
                        }
                    }).start();

                }
            }
        });

       /* Boolean isBooked=getIntent().getBooleanExtra("B",false);
        muser.setBooked(isBooked);
        dao.registerUser(muser);*/
    }

  /*  public void mainPage() {
        Intent intent = new Intent(SignInSignUpActivity.this, MainActivity.class);
       // intent.putExtra("n",)
        startActivity(intent);
    }
*/
}