package com.klim.nickname.di.saved;

import com.klim.nickname.app.window.saved.StoreFragment;

import dagger.Subcomponent;

@SavedScope
@Subcomponent(modules = {SavedModule.class})
public interface SavedComponent {

    public void inject(StoreFragment fragment);

}
