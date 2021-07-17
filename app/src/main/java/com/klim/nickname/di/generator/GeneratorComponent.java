package com.klim.nickname.di.generator;

import com.klim.nickname.ui.generator.GeneratorFragment;

import dagger.Component;
import dagger.Subcomponent;

@GeneratorScope
@Subcomponent(modules = {GeneratorModule.class})
public interface GeneratorComponent {

    public void inject(GeneratorFragment fragment);

}
