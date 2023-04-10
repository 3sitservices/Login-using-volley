/*****
Use the following details to login
"email": "eve.holt@reqres.in",
"password": "cityslicka"
 *****/
package in.it.volleylogindemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private AppCompatButton btn_login;
    private EditText edt_email,edt_pass;
    private final String URL_LOGIN = "https://reqres.in/api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_email = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email", edt_email.getText().toString());
                    jsonObject.put("password", edt_pass.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, URL_LOGIN, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, error -> {
                            int responsecode = error.networkResponse.statusCode;
                            if (responsecode == 400) {
                                Log.w("Message",""+error.getMessage());
                                Log.w("Message1",""+error.networkResponse);
                                Toast.makeText(LoginActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Something went wrong Try again...", Toast.LENGTH_SHORT).show();
                            }
                            // TODO: Handle error
                            error.printStackTrace();
                        });
                // Adding request to request queue
                queue.add(jsonObjectRequest);
            }
        });

    }
}