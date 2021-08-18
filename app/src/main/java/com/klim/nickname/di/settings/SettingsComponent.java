package com.klim.nickname.di.settings;

import com.klim.nickname.app.window.settings.SettingsFragment;

import dagger.Subcomponent;

@SettingsScope
@Subcomponent(modules = {SettingsModule.class})
public interface SettingsComponent {

    public void inject(SettingsFragment fragment);

}
