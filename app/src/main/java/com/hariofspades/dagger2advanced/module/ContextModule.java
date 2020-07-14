package com.hariofspades.dagger2advanced.module;

import android.content.Context;


import com.hariofspades.dagger2advanced.interfaces.ApplicationScope;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    //@ApplicationContext
    @Inject
    @ApplicationScope
    @Provides
    public Context context() { return context.getApplicationContext(); }

}
