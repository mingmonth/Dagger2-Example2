package com.hariofspades.dagger2advanced;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hariofspades.dagger2advanced.adapter.RandomUserAdapter;
import com.hariofspades.dagger2advanced.component.DaggerMainActivityComponent;
import com.hariofspades.dagger2advanced.component.DaggerRandomUsersComponent;
import com.hariofspades.dagger2advanced.component.MainActivityComponent;
import com.hariofspades.dagger2advanced.component.RandomUsersComponent;
import com.hariofspades.dagger2advanced.interfaces.RandomUsersApi;
import com.hariofspades.dagger2advanced.model.RandomUsers;
import com.hariofspades.dagger2advanced.module.ContextModule;
import com.hariofspades.dagger2advanced.module.MainActivityModule;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Retrofit retrofit;
    RecyclerView recyclerView;
    @Inject
    RandomUserAdapter mAdapter;

    Context context;
    Picasso picasso;
    @Inject
    RandomUsersApi randomUsersApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
//        Logger.addLogAdapter(new AndroidLogAdapter());
//        afterDagger();
        afterActivityLevelComponent();

//        context = this;
//
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        Gson gson = gsonBuilder.create();
//
//        Logger.addLogAdapter(new AndroidLogAdapter());
//        //Timber.plant(new Timber.DebugTree());
//
//        File cacheFile = new File(context.getCacheDir(), "HttpCache");
//        Logger.d("getCacheDir : " + context.getCacheDir());
//        cacheFile.mkdirs();
//
//        Cache cache = new Cache(cacheFile, 10 * 1000 * 1000); // 10MB
//
//        HttpLoggingInterceptor httpLoggingInterceptor = new
//                HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(@NonNull String message) {
//                //Timber.i(message);
//                Logger.d(message);
//                //Log.d(TAG, "log: " + message);
//            }
//        });
//
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient okHttpClient = new OkHttpClient()
//                .newBuilder()
//                .cache(cache)
//                .addInterceptor(httpLoggingInterceptor)
//                .build();
//
//        OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(okHttpClient);
//
//        picasso = new Picasso.Builder(context).downloader(okHttp3Downloader).build();
//
//        retrofit = new Retrofit.Builder()
//                .client(okHttpClient)
//                .baseUrl("https://randomuser.me/")
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();

        populateUsers();

    }

    private void afterActivityLevelComponent() {
        MainActivityComponent mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .randomUsersComponent(RandomUserApplication.get(this).getRandomUserApplicationComponent())
                .build();
        mainActivityComponent.injectMainActivity(this);
//        randomUsersApi = mainActivityComponent.getRandomUsersService();
//        mAdapter = mainActivityComponent.getRandomUserAdapter();
    }

    private void afterDagger() {
        RandomUsersComponent daggerRandomUserComponent = DaggerRandomUsersComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        //picasso = daggerRandomUserComponent.getPicasso();
        randomUsersApi = daggerRandomUserComponent.getRandomUsersService();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void populateUsers() {
        Call<RandomUsers> randomUsersCall = randomUsersApi.getRandomUsers(10);
        randomUsersCall.enqueue(new Callback<RandomUsers>() {
            @Override
            public void onResponse(Call<RandomUsers> call, @NonNull Response<RandomUsers> response) {
                //Log.d(TAG, "onResponse: " + response);
                Logger.d(response);
                if(response.isSuccessful()) {
                    //mAdapter = new RandomUserAdapter(this, picasso);
                    mAdapter.setItems(response.body().getResults());
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<RandomUsers> call, Throwable t) {
                //Log.d(TAG, "onFailure: " + t.getMessage());
                Logger.d(t.getMessage());
                //Timber.i(t.getMessage());
            }
        });
    }

    public RandomUsersApi getRandomUserService(){
        //return retrofit.create(RandomUsersApi.class);
        return randomUsersApi;
    }

}
