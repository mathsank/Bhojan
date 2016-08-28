package matt.feedly;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ImageView iv = (ImageView) findViewById(R.id.imageView);

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