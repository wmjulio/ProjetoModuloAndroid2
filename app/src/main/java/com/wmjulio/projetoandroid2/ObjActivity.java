package com.wmjulio.projetoandroid2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.wmjulio.projetoandroid2.model.Obj;

import java.util.Map;

public class ObjActivity extends AppCompatActivity{

    private static final String TAG = "AddObjActivity";

    EditText edtTitle;
    EditText edtDesc;
    EditText edtLat;
    EditText edtLon;

    Button btAdd;

    private FirebaseFirestore firestoreDB;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obj);

        edtTitle = findViewById(R.id.edtTitle);
        edtDesc = findViewById(R.id.edtContent);
        edtLat = findViewById(R.id.edtLat);
        edtLon = findViewById(R.id.edtLon);
        btAdd = findViewById(R.id.btAdd);

        firestoreDB = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            id = bundle.getString("UpdateObjId");

            edtTitle.setText(bundle.getString("UpdateObjNome"));
            edtDesc.setText(bundle.getString("UpdateObjDesc"));
            edtLat.setText(bundle.getString("UpdateObjLat"));
            edtLon.setText(bundle.getString("UpdateObjLon"));
        }

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String content = edtDesc.getText().toString();
                String lat = edtLat.getText().toString();
                String lon = edtLon.getText().toString();

                if (title.length() > 0) {
                    if (id.length() > 0) {
                        update(id, title, content, lat, lon);
                    } else {
                        add(title, content, lat, lon);
                    }
                }

                finish();
            }
        });
    }

    private void update(String id, String nome, String desc, String lat, String lon) {
        Map<String, Object> obj = (new Obj(id, nome, desc, lat, lon)).toMap();

        firestoreDB.collection("objs")
                .document(id)
                .set(obj)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "Obj document update successful!");
                        Toast.makeText(getApplicationContext(), "Obj has been updated!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding Obj document", e);
                        Toast.makeText(getApplicationContext(), "Obj could not be updated!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void add(String nome, String desc, String lat, String lon) {
        Map<String, Object> obj = (new Obj(nome, desc, lat, lon)).toMap();

        firestoreDB.collection("objs")
                .add(obj)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Toast.makeText(getApplicationContext(), "Obj has been added!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding Note document", e);
                        Toast.makeText(getApplicationContext(), "Obj could not be added!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
