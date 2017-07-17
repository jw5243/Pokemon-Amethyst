package com.horse.pokemon.amethyst.data.characters;

import com.badlogic.gdx.math.Rectangle;

public class NPCPositionInformation {
    private NPC       npc;
    private Rectangle collisionRectangle;
    
    public NPCPositionInformation() {
        this(null, null);
    }
    
    public NPCPositionInformation(final NPC npc, final Rectangle collisionRectangle) {
        setNpc(npc);
        setCollisionRectangle(collisionRectangle);
    }
    
    public NPC getNpc() {
        return npc;
    }
    
    public void setNpc(NPC npc) {
        this.npc = npc;
    }
    
    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }
    
    public void setCollisionRectangle(Rectangle collisionRectangle) {
        this.collisionRectangle = collisionRectangle;
    }
}
