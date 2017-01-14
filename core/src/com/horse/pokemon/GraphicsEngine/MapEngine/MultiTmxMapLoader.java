package com.horse.pokemon.GraphicsEngine.MapEngine;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;
import java.util.HashMap;

public class MultiTmxMapLoader extends TmxMapLoader {
    private int offsetX;
    private int offsetY;
    
    public MultiTmxMapLoader() {
        setOffsetX(0);
        setOffsetY(0);
    }
    
    public MultiTmxMapLoader(int offsetX, int offsetY) {
        setOffsetX(offsetX);
        setOffsetY(offsetY);
    }
    
    public int getOffsetX() {
        return offsetX;
    }
    
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }
    
    public int getOffsetY() {
        return offsetY;
    }
    
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
    
    public MultiTiledMap[] loadAllMaps(String... mapNames) {
        HashMap<String, int[]> offsetMap = new HashMap<>(mapNames.length);
        for(String mapName : mapNames) {
            offsetMap.put(mapName, new int[] {0, 0});
        }
        MultiTiledMap[] tiledMaps = new MultiTiledMap[mapNames.length];
        for(int index = 0; index < mapNames.length; index++) {
            MultiTiledMap multiTiledMap = load(mapNames[index]);
            offsetMap.put(multiTiledMap.getConnectingMap(), multiTiledMap.getConnectionOffset());
            multiTiledMap.dispose();
        }
        for(int index = 0; index < mapNames.length; index++) {
            setOffsetX(offsetMap.get(mapNames[index])[0]);
            setOffsetY(offsetMap.get(mapNames[index])[1]);
            MultiTiledMap multiTiledMap = load(mapNames[index]);
            multiTiledMap.setOffsetX(offsetMap.get(mapNames[index])[0]);
            multiTiledMap.setOffsetY(offsetMap.get(mapNames[index])[1]);
            tiledMaps[index] = multiTiledMap;
        }
        return tiledMaps;
    }
    
    @Override
    protected void loadTileLayer(TiledMap map, XmlReader.Element element) {
        if(element.getName().equals("layer")) {
            int               width      = element.getIntAttribute("width", 0);
            int               height     = element.getIntAttribute("height", 0);
            int               tileWidth  = element.getParent().getIntAttribute("tilewidth", 0);
            int               tileHeight = element.getParent().getIntAttribute("tileheight", 0);
            TiledMapTileLayer layer      = new TiledMapTileLayer(width * width, height * height, tileWidth, tileHeight);
            
            loadBasicLayerInfo(layer, element);
            
            int[]            ids      = getTileIds(element, width, height);
            TiledMapTileSets tilesets = map.getTileSets();
            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    int     id               = ids[y * width + x];
                    boolean flipHorizontally = ((id & FLAG_FLIP_HORIZONTALLY) != 0);
                    boolean flipVertically   = ((id & FLAG_FLIP_VERTICALLY) != 0);
                    boolean flipDiagonally   = ((id & FLAG_FLIP_DIAGONALLY) != 0);
                    
                    TiledMapTile tile = tilesets.getTile(id & ~MASK_CLEAR);
                    if(tile != null) {
                        TiledMapTileLayer.Cell cell = createTileLayerCell(flipHorizontally, flipVertically, flipDiagonally);
                        cell.setTile(tile);
                        layer.setCell(x + getOffsetX(), (flipY ? height - 1 - y : y) + getOffsetY(), cell);
                    }
                }
            }
            
            XmlReader.Element properties = element.getChildByName("properties");
            if(properties != null) {
                loadProperties(layer.getProperties(), properties);
            }
            map.getLayers().add(layer);
        }
    }
    
    /**
     * Loads the {@link TiledMap} from the given file. The file is resolved via the {@link FileHandleResolver} set in the
     * constructor of this class. By default it will resolve to an internal file. The map will be loaded for a y-up coordinate
     * system.
     *
     * @param fileName the filename
     *
     * @return the TiledMap
     */
    @Override
    public MultiTiledMap load(String fileName) {
        return load(fileName, new TmxMapLoader.Parameters());
    }
    
    /**
     * Loads the {@link TiledMap} from the given file. The file is resolved via the {@link FileHandleResolver} set in the
     * constructor of this class. By default it will resolve to an internal file.
     *
     * @param fileName   the filename
     * @param parameters specifies whether to use y-up, generate mip maps etc.
     *
     * @return the TiledMap
     */
    @Override
    public MultiTiledMap load(String fileName, TmxMapLoader.Parameters parameters) {
        try {
            this.convertObjectToTileSpace = parameters.convertObjectToTileSpace;
            this.flipY = parameters.flipY;
            FileHandle tmxFile = resolve(fileName);
            root = xml.parse(tmxFile);
            ObjectMap<String, Texture> textures     = new ObjectMap<>();
            Array<FileHandle>          textureFiles = loadTilesets(root, tmxFile);
            textureFiles.addAll(loadImages(root, tmxFile));
            
            for(FileHandle textureFile : textureFiles) {
                Texture texture = new Texture(textureFile, parameters.generateMipMaps);
                texture.setFilter(parameters.textureMinFilter, parameters.textureMagFilter);
                textures.put(textureFile.path(), texture);
            }
            
            ImageResolver.DirectImageResolver imageResolver = new ImageResolver.DirectImageResolver(textures);
            MultiTiledMap                     map           = loadTilemap(root, tmxFile, imageResolver);
            map.setOwnedResources(textures.values().toArray());
            return map;
        } catch(IOException e) {
            throw new GdxRuntimeException("Couldn't load tilemap '" + fileName + "'", e);
        }
    }
    
    /**
     * Loads the map data, given the XML root element and an {@link ImageResolver} used to return the tileset Textures
     *
     * @param root          the XML root element
     * @param tmxFile       the Filehandle of the tmx file
     * @param imageResolver the {@link ImageResolver}
     *
     * @return the {@link TiledMap}
     */
    @Override
    protected MultiTiledMap loadTilemap(XmlReader.Element root, FileHandle tmxFile, ImageResolver imageResolver) {
        MultiTiledMap map = new MultiTiledMap(getOffsetX(), getOffsetY());
        
        String mapOrientation     = root.getAttribute("orientation", null);
        int    mapWidth           = root.getIntAttribute("width", 0);
        int    mapHeight          = root.getIntAttribute("height", 0);
        int    tileWidth          = root.getIntAttribute("tilewidth", 0);
        int    tileHeight         = root.getIntAttribute("tileheight", 0);
        int    hexSideLength      = root.getIntAttribute("hexsidelength", 0);
        String staggerAxis        = root.getAttribute("staggeraxis", null);
        String staggerIndex       = root.getAttribute("staggerindex", null);
        String mapBackgroundColor = root.getAttribute("backgroundcolor", null);
        
        MapProperties mapProperties = map.getProperties();
        if(mapOrientation != null) {
            mapProperties.put("orientation", mapOrientation);
        }
        mapProperties.put("width", mapWidth);
        mapProperties.put("height", mapHeight);
        mapProperties.put("tilewidth", tileWidth);
        mapProperties.put("tileheight", tileHeight);
        mapProperties.put("hexsidelength", hexSideLength);
        if(staggerAxis != null) {
            mapProperties.put("staggeraxis", staggerAxis);
        }
        if(staggerIndex != null) {
            mapProperties.put("staggerindex", staggerIndex);
        }
        if(mapBackgroundColor != null) {
            mapProperties.put("backgroundcolor", mapBackgroundColor);
        }
        mapTileWidth = tileWidth;
        mapTileHeight = tileHeight;
        mapWidthInPixels = mapWidth * tileWidth;
        mapHeightInPixels = mapHeight * tileHeight;
        
        if(mapOrientation != null) {
            if("staggered".equals(mapOrientation)) {
                if(mapHeight > 1) {
                    mapWidthInPixels += tileWidth / 2;
                    mapHeightInPixels = mapHeightInPixels / 2 + tileHeight / 2;
                }
            }
        }
        
        XmlReader.Element properties = root.getChildByName("properties");
        if(properties != null) {
            loadProperties(map.getProperties(), properties);
        }
        Array<XmlReader.Element> tilesets = root.getChildrenByName("tileset");
        for(XmlReader.Element element : tilesets) {
            loadTileSet(map, element, tmxFile, imageResolver);
            root.removeChild(element);
        }
        for(int i = 0, j = root.getChildCount(); i < j; i++) {
            XmlReader.Element element = root.getChild(i);
            String            name    = element.getName();
            if(name.equals("layer")) {
                loadTileLayer(map, element);
            } else if(name.equals("objectgroup")) {
                loadObjectGroup(map, element);
            } else if(name.equals("imagelayer")) {
                loadImageLayer(map, element, tmxFile, imageResolver);
            }
        }
        return map;
    }
}
