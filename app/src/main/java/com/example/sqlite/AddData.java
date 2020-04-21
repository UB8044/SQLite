package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddData extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private Database mydb ;

    EditText nama ;
    EditText phone;
    EditText email;
    EditText alamat;

    int id_To_Update = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        nama = (EditText) findViewById(R.id.editNama);
        phone = (EditText) findViewById(R.id.editNoTlpn);
        email = (EditText) findViewById(R.id.editEmail);
        alamat = (EditText) findViewById(R.id.editAlamat);

        mydb = new Database(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(Database.PRS_COLUMN_NAMA));
                String phon = rs.getString(rs.getColumnIndex(Database.PRS_COLUMN_TELEPON));
                String mail = rs.getString(rs.getColumnIndex(Database.PRS_COLUMN_EMAIL));
                String address = rs.getString(rs.getColumnIndex(Database.PRS_COLUMN_ALAMAT));
                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.btn_simpan);
                b.setVisibility(View.INVISIBLE);

                nama.setText((CharSequence)nam);
                nama.setFocusable(false);
                nama.setClickable(false);

                phone.setText((CharSequence)phon);
                phone.setFocusable(false);
                phone.setClickable(false);

                email.setText((CharSequence)mail);
                email.setFocusable(false);
                email.setClickable(false);

                alamat.setText((CharSequence)address);
                alamat.setFocusable(false);
                alamat.setClickable(false);
            }
        }
    }

    public void run(View view){
        if (nama.getText().toString().equals("")||phone.getText().toString().equals("")||email.getText().toString().equals("")||alamat.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Data Harus Lengkap", Toast.LENGTH_LONG).show();
        } else {
            mydb.insertContact(nama.getText().toString(), phone.getText().toString(), email.getText().toString(), alamat.getText().toString());

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }
}
