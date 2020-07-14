package com.hariofspades.dagger2advanced.component;

import com.hariofspades.dagger2advanced.interfaces.ApplicationScope;
import com.hariofspades.dagger2advanced.interfaces.RandomUsersApi;
import com.hariofspades.dagger2advanced.module.PicassoModule;
import com.hariofspades.dagger2advanced.module.RandomUsersModule;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.Component;


@ApplicationScope
@Component(modules = {RandomUsersModule.class, PicassoModule.class})
public interface RandomUsersComponent {

    RandomUsersApi getRandomUsersService();

    Picasso getPicasso();
}
