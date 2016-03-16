package matt.feedly;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }

    public boolean JoinNow(String pwd, String cpwd) {
        Pattern pattern = Pattern.compile(pwd, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(cpwd);

        if (!matcher.matches()) {
            // do your Toast("passwords are not matching");

            {Context context= getApplicationContext();
                CharSequence text="Passwords do not match";
                int duration= Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context,text,duration);

            }

            return false;
        }

        return true;
    }


}


