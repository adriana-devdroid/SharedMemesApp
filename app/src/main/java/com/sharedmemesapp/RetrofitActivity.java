package com.sharedmemesapp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.sharedmemesapp.adapters.myAdapter;
import com.sharedmemesapp.entities.Imagen;
import com.sharedmemesapp.entities.User;

import java.util.LinkedList;
import java.util.Map;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class RetrofitActivity extends BaseActivity {

    LinkedList<Imagen> imagenes = new LinkedList<>();
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlBase) //url de la app en firebase
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);


        api.getData().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Map<String, Imagen>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Map<String, Imagen> stringImagenMap) {
                        for (String key : stringImagenMap.keySet()) {
                            imagenes.addFirst(stringImagenMap.get(key));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RetrofitActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {
                        mRecyclerView.setAdapter(mAdapter);

                    }
                });

        mlayoutManager = new LinearLayoutManager(this);
        mAdapter = new myAdapter(imagenes, R.layout.list_item);
        mRecyclerView.setLayoutManager(mlayoutManager);




/*
        myUsers.observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Map<String,User>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Map<String, User> stringUserMap) {

                Log.d("examRXS", "onNext(" + stringUserMap.keySet().toString() + ")");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }); */


    }

    public interface Api {
        @POST("/upload/{new}.json")
        Call<User> setData(@Path("new") String s1, @Body User user);

        @GET("/images.json")
        Observable<Map<String, Imagen>> getData();

        @PUT("/upload/{new}.json")
        Call<User> setDataWithoutRandomness(@Path("new") String s1, @Body User user);

    }

}


/* PUT
        Call<User> call1=api.setDataWithoutRandomness("users", new User("Adriana", "Oaxaca"));
        call1.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String s = "Nombre: "+response.body().getName()+" Dirección: "+response.body().getAddress();
                result.setText(s);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                result.setText("Fail");
            }
        });
        */

/* POST

        Call<User> call = api.setData("users", new User("Rocio", "CDMX"));
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
               result.setText("Success");

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                result.setText("fail");
            }
        });





        Call<Map<String,User>> call2=api.getData();
        call2.enqueue(new Callback<Map<String, User>>() {
            @Override
            public void onResponse(Call<Map<String, User>> call, Response<Map<String, User>> response) {

                Map<String, User> map = response.body();

                for (String keys : map.keySet()) {
                    result.setText("Success ");
                }


            }

            @Override
            public void onFailure(Call<Map<String, User>> call, Throwable t) {

            }
        }); */
