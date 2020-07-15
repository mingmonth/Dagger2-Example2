package com.hariofspades.dagger2advanced;

import android.app.Activity;
import android.app.Application;

import com.hariofspades.dagger2advanced.component.DaggerRandomUsersComponent;
import com.hariofspades.dagger2advanced.component.RandomUsersComponent;
import com.hariofspades.dagger2advanced.module.ContextModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class RandomUserApplication extends Application {

    private RandomUsersComponent randomUserApplicationComponent;

    public static RandomUserApplication get(Activity activity) {
        return (RandomUserApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());

        randomUserApplicationComponent = DaggerRandomUsersComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public RandomUsersComponent getRandomUserApplicationComponent() {
        return randomUserApplicationComponent;
    }
}
