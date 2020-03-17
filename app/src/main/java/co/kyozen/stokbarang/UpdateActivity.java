package co.kyozen.stokbarang;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateActivity extends AppCompatActivity {

    private static final String URL = "http://aplikasistok99.000webhostapp.com/";
    private ProgressDialog process;

    @BindView(R.id.editTextID) EditText editTextID;
    @BindView(R.id.editTextNama) EditText editTextNama;
    @BindView(R.id.editTextJumlah) EditText editTextJumlah;

    @OnClick(R.id.buttonUbah) void Ubah(){
        process = new ProgressDialog(this);
        process.setMessage("Loading...");
        process.setCancelable(false);
        process.show();

        String id = editTextID.getText().toString();
        String nama = editTextNama.getText().toString();
        String jml = editTextJumlah.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterAPI api = retrofit.create(RegisterAPI.class);
        retrofit2.Call<Value> call = api.ubah(id,nama,jml);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(retrofit2.Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                process.dismiss();
                if (value.equals("1")){
                    Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Value> call, Throwable t) {
                t.printStackTrace();
                process.dismiss();
                Toast.makeText(UpdateActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Data");

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String nama = intent.getStringExtra("nama");
        String jml = intent.getStringExtra("jml");

        editTextID.setText(id);
        editTextNama.setText(nama);
        editTextJumlah.setText(jml);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
                break;
            case R.id.action_delete:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Peringatan!");
                alertDialogBuilder
                        .setMessage("Apakah Anda Yakin Ingin Menghapus Data?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = editTextID.getText().toString();

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                RegisterAPI api = retrofit.create(RegisterAPI.class);
                                retrofit2.Call<Value> call = api.del(id);
                                call.enqueue(new Callback<Value>() {
                                    @Override
                                    public void onResponse(retrofit2.Call<Value> call, Response<Value> response) {
                                        String value = response.body().getValue();
                                        String message = response.body().getMessage();
                                        if (value.equals("1")){
                                            Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(UpdateActivity.this, ViewActivity.class));
                                            finish();
                                        }else {
                                            Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(retrofit2.Call<Value> call, Throwable t) {
                                        t.printStackTrace();
                                        Toast.makeText(UpdateActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }
}
