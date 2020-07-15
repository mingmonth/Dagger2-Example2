package com.hariofspades.dagger2advanced.component;

import com.hariofspades.dagger2advanced.MainActivity;
import com.hariofspades.dagger2advanced.adapter.RandomUserAdapter;
import com.hariofspades.dagger2advanced.interfaces.MainActivityScope;
import com.hariofspades.dagger2advanced.interfaces.RandomUsersApi;
import com.hariofspades.dagger2advanced.module.MainActivityModule;

import dagger.Component;

@Component(modules = MainActivityModule.class, dependencies = RandomUsersComponent.class)
@MainActivityScope
public interface MainActivityComponent {

//    RandomUserAdapter getRandomUserAdapter();
//    RandomUsersApi getRandomUsersService();
    void injectMainActivity(MainActivity mainActivity);
}
