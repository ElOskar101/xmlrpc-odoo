package com.l3soft.odoo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.l3soft.odoo.MainActivity;
import com.l3soft.odoo.R;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;

import java.util.HashMap;
import java.util.Objects;

import static java.util.Arrays.asList;

public class Create extends AppCompatActivity {
    private int uid = 0;
    private String password;
    private String db;
    private XmlRpcClient models;

    private EditText createName;
    private EditText createEmail;
    private EditText createPhone;
    private EditText createAddress;
    private Button createContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        InitViews();
        InitActions();

    }

    private void InitViews(){

        uid = (int) Objects.requireNonNull(getIntent().getExtras()).get("uid");
        db = Objects.requireNonNull(getIntent().getExtras().get("db")).toString();
        password = Objects.requireNonNull(getIntent().getExtras().get("password")).toString();

        createName = findViewById(R.id.create_name);
        createAddress = findViewById(R.id.create_address);
        createEmail = findViewById(R.id.create_email);
        createPhone = findViewById(R.id.create_phone);
        createContact = findViewById(R.id.create_contact);

        
    }

    private void InitActions (){

        createContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SetNew();
            }
        });
    }

    private void SetNew ( XmlRpcClient models) throws XmlRpcException {

        models.execute("execute_kw", asList(
                db, uid, password,
                "res.partner", "create",
                asList(new HashMap() {{ put("name", "Javier Guerra"); }})
        ));
    }
}
