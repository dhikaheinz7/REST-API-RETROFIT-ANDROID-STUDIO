package co.kyozen.stokbarang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "http://aplikasistok99.000webhostapp.com/";
    private ProgressDialog progress;

    @BindView(R.id.editTextID)
    EditText editTextID;
    @BindView(R.id.editTextNama)
    EditText editTextNama;
    @BindView(R.id.editTextJumlah)
    EditText editTextJumlah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.buttonStok) void stok() {
        //Membuat ProgressDialog
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading...");
        progress.show();

        //Mengambil data dari editText
        String id_stok = editTextID.getText().toString();
        String nama_barang = editTextNama.getText().toString();
        String jumlah_barang = editTextJumlah.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.stok(id_stok, nama_barang, jumlah_barang);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progress.dismiss();
                fresh();
                if (value.equals("1")) {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(MainActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick(R.id.buttonLihat) void lihat(){
        startActivity(new Intent(MainActivity.this, ViewActivity.class));

    }

    public void fresh(){
        String kosong = "";
        editTextID.setText(kosong);
        editTextNama.setText(kosong);
        editTextJumlah.setText(kosong);
    }
}
