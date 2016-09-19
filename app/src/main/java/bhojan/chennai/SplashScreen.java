package bhojan.chennai;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (AppStatus.getInstance(this).isOnline()) {
            Context context = getApplicationContext();
            Intent intent = new Intent(this, LogIn.class);
            startActivity(intent);
            finish();

        } else {
            Context context = getApplicationContext();
            CharSequence text = "Check Network Connection and try again.";
            int duration = Toast.LENGTH_SHORT;
            Toast t = Toast.makeText(context, text, duration);
            t.show();
            finish();
            Log.v("Home", "###########################You are not online!!!!");
        }


    }

}