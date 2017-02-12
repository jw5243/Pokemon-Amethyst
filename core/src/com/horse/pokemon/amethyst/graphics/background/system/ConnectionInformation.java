package com.horse.pokemon.amethyst.graphics.background.system;

import com.badlogic.gdx.math.Rectangle;

public class ConnectionInformation {
    private Rectangle rectangle;
    private String    connectingMap;
    
    public ConnectionInformation(Rectangle rectangle, String connectingMap) {
        setRectangle(rectangle);
        setConnectingMap(connectingMap);
    }
    
    public Rectangle getRectangle() {
        return rectangle;
    }
    
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
    
    public String getConnectingMap() {
        return connectingMap;
    }
    
    public void setConnectingMap(String connectingMap) {
        this.connectingMap = connectingMap;
    }
}
