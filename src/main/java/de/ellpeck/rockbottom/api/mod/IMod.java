package de.ellpeck.rockbottom.api.mod;

import de.ellpeck.rockbottom.api.IApiHandler;
import de.ellpeck.rockbottom.api.IGameInstance;
import de.ellpeck.rockbottom.api.assets.IAssetManager;
import de.ellpeck.rockbottom.api.event.IEventHandler;

public interface IMod{

    String getDisplayName();

    String getId();

    String getVersion();

    default void preInit(IGameInstance game, IAssetManager assetManager, IApiHandler apiHandler, IEventHandler eventHandler){

    }

    default void init(IGameInstance game, IAssetManager assetManager, IApiHandler apiHandler, IEventHandler eventHandler){

    }

    default void postInit(IGameInstance game, IAssetManager assetManager, IApiHandler apiHandler, IEventHandler eventHandler){

    }
}