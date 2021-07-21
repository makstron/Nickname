package com.klim.nickname.di.settings;

import com.klim.nickname.app.window.settings.SettingsFragment;
import com.klim.nickname.di.saved.SavedScope;

import dagger.Subcomponent;

@SettingsScope
@Subcomponent(modules = {SettingsModule.class})
public interface SettingsComponent {

    public void inject(SettingsFragment fragment);

}
