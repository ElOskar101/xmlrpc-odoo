package com.l3soft.odoo.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.l3soft.odoo.R;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;

import java.util.HashMap;

import static java.util.Arrays.asList;

public class Create extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

    }

    /*private void SetNew (int uid, XmlRpcClient models) throws XmlRpcException {

        models.execute("execute_kw", asList(
                db, uid, password,
                "res.partner", "create",
                asList(new HashMap() {{ put("name", "Javier Guerra"); }})
        ));
    }*/
}
