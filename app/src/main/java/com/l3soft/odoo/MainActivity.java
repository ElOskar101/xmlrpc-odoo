package com.l3soft.odoo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.l3soft.odoo.activities.Create;
import com.l3soft.odoo.adapter.ContactAdapter;
import com.l3soft.odoo.models.Contact;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import static java.util.Collections.emptyMap;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.apache.log4j.MDC.put;

public class MainActivity extends AppCompatActivity {

    // Global variables
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private FloatingActionButton floating;
    public static XmlRpcClient models;




    private final String url = "https://demo44.odoo.com", // All data we need to connect to our Odoo host
            db = "demo44",
            username = "oscaresga22@gmail.com",
            password = "P@ssword";

    int uid = 0;

    List<Object> data  = new ArrayList<>(); // Array to get information from server
    ArrayList<Contact> contacts = new ArrayList<>(); // Array to catch information from server

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Prepare the recycler
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // We gonna launch another activity with this
        floating = findViewById(R.id.floating);

        // With these we allow internet protocol http
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // The actions!!
        InitActions();

        // We call initial method
        try {
            Initialize();
        } catch (MalformedURLException | XmlRpcException e) {
            e.printStackTrace();
        }
    }

    private void InitActions(){
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Create(uid);
            }
        });
    }

    public void Initialize () throws MalformedURLException, XmlRpcException {

            // Client and common config
            final XmlRpcClient client = new XmlRpcClient();
            final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();

            // Create a model
            models = new XmlRpcClient() {{
                setConfig(new XmlRpcClientConfigImpl() {{
                    setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));
                }});
            }};

            // Stabilising server
            common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/common", url)));
            client.execute(common_config, "version", emptyList());

            // Authenticate in the server
            uid = Authenticate(client, common_config);

            // We can get all id's contacts. Only people are allowed
            final List ids = asList((Object[])models.execute(
                "execute_kw", asList(
                        db, uid, password,
                        "res.partner", "search",
                        asList(asList(
                                asList("is_company", "=", false))),
                        new HashMap() {{ put("limit", 50); }})));

            /*final Map record = (Map)((Object[])models.execute( // Do not touch that. Leave it like that.
                    "execute_kw", asList(
                            db, uid, password,
                            "res.partner", "read",
                            asList(ids)
                    )
            ))[0];*/

            // So, now we can get all contact of people from our Odoo.
            data = asList((Object[])models.execute("execute_kw", asList(
                    db, uid, password,
                    "res.partner", "read",
                    asList(ids),
                    new HashMap() {{
                        put("fields", asList("name", "phone", "email", "street")); // Fields we need.
                    }}
            )));

            // Now, that data should be save in an object to be use in the adapter
            for(int i = 0; i < data.size(); i++ ){
                Contact element = new Contact();
                element.setName(((HashMap<String, String>) data.get(i)).get("name"));
                element.setAddress(((HashMap<String, String>) data.get(i)).get("street"));
                element.setImage("none");
                element.setPhone(((HashMap<String, String>) data.get(i)).get("phone"));
                element.setEmail(((HashMap<String, String>) data.get(i)).get("email"));

                contacts.add(i, element); // Adding each contact as a new model
            }
            System.out.println(contacts.size() + " <- Primer registro ->"); // Ignore it. Just was an old way to debug

            // Send registers to the adapter
            adapter = new ContactAdapter(contacts, this);
            recyclerView.setAdapter(adapter);


    }

    // Another authentication method. We just need uid.
    private int Authenticate (XmlRpcClient client, XmlRpcClientConfigImpl common_config){
        int uido = 0;
        try {
            uido = (int) client.execute(
                    common_config, "authenticate", asList(
                            db, username, password, emptyMap()));
        }catch ( Exception e){
            e.printStackTrace();
            System.out.println("Sorry, That error i was not expecting");
        }
        return uido;
    }

    private void Create (int uid){
        Intent i = new Intent(this, Create.class);
        i.putExtra("uid", uid);
        i.putExtra("db", db);
        i.putExtra("password", password);


        this.startActivity(i);
    }
}
