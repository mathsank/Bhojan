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

    public boolean JoinNow(View view) {
        //Password Validation
        final EditText passValidate=(EditText)findViewById(R.id.pwd);
        String pwd=passValidate.getText().toString();
        String passPattern="[a-zA-Z0-9.]";
        if(pwd.matches(passPattern))
        {Toast.makeText(getApplicationContext(), "Valid Password", Toast.LENGTH_SHORT).show();}
        else
        {Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();}

        //Spinner Validation
        final Spinner st=(Spinner)findViewById(R.id.area);
        String area=st.getSelectedItem().toString();
        String area1="Choose Area";
        if(area.equals(area1)) {
            Toast.makeText(getApplicationContext(), "Choose area", Toast.LENGTH_SHORT).show();
        }
        else
        {Toast.makeText(getApplicationContext(), "Valid area", Toast.LENGTH_SHORT).show();}

        //Phone Number validation

        final EditText phValidate=(EditText)findViewById(R.id.phno);
        String phno=phValidate.getText().toString().trim();
        String MobilePattern = "[0-9]{10}";


        if(phno.matches(MobilePattern) && phno.length()==10) {
            Toast.makeText(getApplicationContext(), "Valid phone number", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Invalid Phone number", Toast.LENGTH_SHORT).show();
        }

        //Emailaddress Validation
        final EditText emailValidate = (EditText)findViewById(R.id.email);
        String email = emailValidate.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";


        if (email.matches(emailPattern))
        {
            Toast.makeText(getApplicationContext(),"Valid email address",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
        }



        //Confirm Password and Password match checking

        String password=passValidate.getText().toString();

        EditText cpwd=(EditText) findViewById(R.id.cpwd);
        String cpassword=cpwd.getText().toString();
        if (password.equals(cpassword)) {

            return false;
        }
        else
            {Context context= getApplicationContext();
                CharSequence text="Passwords do not match";
                int duration= Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context,text,duration);
                toast.show();
            }

        return true;
    }
}




