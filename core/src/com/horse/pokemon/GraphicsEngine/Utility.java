package com.horse.pokemon.GraphicsEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public final class Utility {
    private static final AssetManager               ASSET_MANAGER              = new AssetManager();
    private static final String                     TAG                        = Utility.class.getSimpleName();
    private static       InternalFileHandleResolver internalFileHandleResolver = new InternalFileHandleResolver();
    
    public static void unloadAsset(String assetFilePath) {
        if(ASSET_MANAGER.isLoaded(assetFilePath)) {
            ASSET_MANAGER.unload(assetFilePath);
        } else {
            Gdx.app.log(TAG, String.format("Asset unable to load.\nGiven file path %s", assetFilePath));
        }
    }
    
    public static float loadCompleted() {
        return ASSET_MANAGER.getProgress();
    }
    
    public static int numberAssetsQueued() {
        return ASSET_MANAGER.getQueuedAssets();
    }
    
    public static boolean updateAssetLoading() {
        return ASSET_MANAGER.update();
    }
    
    public static boolean isAssetLoaded(String fileName) {
        return ASSET_MANAGER.isLoaded(fileName);
    }
    
    public static void loadMapAsset(String mapFilenamePath) {
        if(mapFilenamePath == null || mapFilenamePath.isEmpty()) {
            return;
        }
        if(internalFileHandleResolver.resolve(mapFilenamePath).exists()) {
            ASSET_MANAGER.setLoader(TiledMap.class, new TmxMapLoader(internalFileHandleResolver));
            ASSET_MANAGER.load(mapFilenamePath, TiledMap.class);
            
            ASSET_MANAGER.finishLoadingAsset(mapFilenamePath);
            Gdx.app.debug(TAG, String.format("Map loaded!: %s", mapFilenamePath));
        } else {
            Gdx.app.debug(TAG, String.format("Map does not exist: %s", mapFilenamePath));
        }
    }
    
    public static TiledMap getMapAsset(String mapFilenamePath) {
        TiledMap map = null;
        if(ASSET_MANAGER.isLoaded(mapFilenamePath)) {
            map = ASSET_MANAGER.get(mapFilenamePath, TiledMap.class);
        } else {
            Gdx.app.debug(TAG, String.format("Map is not loaded: %s", mapFilenamePath));
        }
        return map;
    }
    
    public static void loadTextureAsset(String textureFilenamePath) {
        if(textureFilenamePath == null || textureFilenamePath.isEmpty()) {
            return;
        }
        if(internalFileHandleResolver.resolve(textureFilenamePath).exists()) {
            ASSET_MANAGER.setLoader(Texture.class, new TextureLoader(internalFileHandleResolver));
            ASSET_MANAGER.load(textureFilenamePath, Texture.class);
            
            ASSET_MANAGER.finishLoadingAsset(textureFilenamePath);
        } else {
            Gdx.app.debug(TAG, String.format("Texture does not exist: %s", textureFilenamePath));
        }
    }
    
    public static Texture getTextureAsset(String textureFilenamePath) {
        Texture texture = null;
        if(ASSET_MANAGER.isLoaded(textureFilenamePath)) {
            texture = ASSET_MANAGER.get(textureFilenamePath, Texture.class);
        } else {
            Gdx.app.debug(TAG, String.format("Texture is not loaded: %s", textureFilenamePath));
        }
        return texture;
    }
}
