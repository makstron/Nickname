package com.klim.nickname.di.generator;

import com.klim.nickname.app.window.generator.GeneratorFragment;

import dagger.Subcomponent;

@GeneratorScope
@Subcomponent(modules = {GeneratorModule.class})
public interface GeneratorComponent {

    public void inject(GeneratorFragment fragment);

}
