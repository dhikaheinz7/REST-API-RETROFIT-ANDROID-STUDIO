package co.kyozen.stokbarang;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("insert.php")
    Call<Value> stok(@Field("id") String id_stok,
                     @Field("nama") String nama_barang,
                     @Field("jml") String jumlah_barang);

    @GET("read.php")
    Call<Value> baca();

    @FormUrlEncoded
    @POST("/update.php")
    Call<Value> ubah(@Field("id") String id_stok,
                     @Field("nama") String nama_barang,
                     @Field("jml") String jumlah_barang);

    @FormUrlEncoded
    @POST("/delete.php")
    Call<Value> del(@Field("id") String id);
}
