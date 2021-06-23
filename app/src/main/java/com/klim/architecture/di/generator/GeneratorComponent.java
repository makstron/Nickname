package com.klim.architecture.di.generator;

import com.klim.architecture.ui.generator.GeneratorFragment;

import dagger.Component;
import dagger.Subcomponent;

@GeneratorScope
@Subcomponent(modules = {GeneratorModule.class})
public interface GeneratorComponent {

    public void inject(GeneratorFragment fragment);

}
