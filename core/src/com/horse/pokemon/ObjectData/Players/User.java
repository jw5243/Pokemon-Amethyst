package com.horse.pokemon.ObjectData.Players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.horse.pokemon.AnimationEngine.AnimationInitializer;
import com.horse.pokemon.AnimationEngine.AnimationInterface;
import com.horse.pokemon.AnimationEngine.AnimationManager;
import com.horse.pokemon.Engine;
import com.horse.pokemon.GraphicsEngine.MainInterface.HandleInput;
import com.horse.pokemon.GraphicsEngine.ScreenEngine.MainGameScreen;
import com.horse.pokemon.GraphicsEngine.ScreenEngine.MapCreator;
import com.horse.pokemon.ObjectData.TiledObjects.TileObject;
import com.horse.pokemon.ObjectData.TiledObjects.Water;

import java.util.Arrays;
import java.util.Objects;

public final class User extends AbstractPlayer implements AnimationInterface {
    private static final int    USER_WALK_WIDTH  = 16;
    private static final int    USER_WALK_HEIGHT = 19;
    private static final int    USER_SWIM_WIDTH  = 22;
    private static final int    USER_SWIM_HEIGHT = 24;
    private static final float  ANIMATION_SPEED  = 0.5f;
    private static final String USER_INFORMATION = "User\\GDX_Users\\User.pack";
    private final HandleInput     handleInput;
    private final TextureRegion[] userIdleOnLand;
    private final TextureRegion[] userIdleOnWater;
    private final Animation[]     userWalk;
    private final Animation[]     userSwim;
    private       MapCreator      mapCreator;
    private       float           positionX;
    private       float           positionY;
    private       State           currentState;
    private       State           previousState;
    private       float           stateTimer;
    private       double          currentTime;
    private       char            direction;
    private       boolean         moving;
    private       boolean         aligned;
    private       boolean         futureCollision;
    private       Sprite          userSprite;
    private       boolean         swimming;
    
    public User(MainGameScreen screen, MapCreator mapCreator) {
        super(screen, "SpriteSheetUser");
        setCurrentState(State.IDLE);
        setPreviousState(State.IDLE);
        setStateTimer(0);
        setDirection('D');
        setMoving(false);
        setAligned(true);
        setFutureCollision(false);
        setUserSprite(new Sprite(screen.getAtlas().findRegion("SpriteSheetUser")));
        setSwimming(false);
        
        setMapCreator(mapCreator);
        
        userIdleOnLand = new TextureRegion[4];
        userIdleOnWater = new TextureRegion[4];
        
        userWalk = new Animation[4];
        userSwim = new Animation[4];
        
        initializeAnimation();
        
        setBounds(0, 0, Engine.getTileSize() / 2, Engine.getTileSize() / 2);
        getUserSprite().setRegion(getUserIdleOnLand()[1]);
        
        resetPosition();
        
        handleInput = new HandleInput((float dt) -> {
            if(isAligned()) {
                setMoving(true);
                setAligned(false);
                setDirection('U');
                setFutureCollision(isColliding(getFutureRectangle(0, Engine.getTileSize()), true));
            }
        }, (float dt) -> {
            if(isAligned()) {
                setMoving(true);
                setAligned(false);
                setDirection('D');
                setFutureCollision(isColliding(getFutureRectangle(0, -Engine.getTileSize()), true));
            }
        }, (float dt) -> {
            if(isAligned()) {
                setMoving(true);
                setAligned(false);
                setDirection('R');
                setFutureCollision(isColliding(getFutureRectangle(Engine.getTileSize(), 0), true));
            }
        }, (float dt) -> {
            if(isAligned()) {
                setMoving(true);
                setAligned(false);
                setDirection('L');
                setFutureCollision(isColliding(getFutureRectangle(-Engine.getTileSize(), 0), true));
            }
        }, (float dt) -> {
            if(isAligned()) {
                setMoving(false);
            }
        });
    }
    
    public static int getUserSwimWidth() {
        return USER_SWIM_WIDTH;
    }
    
    public static int getUserSwimHeight() {
        return USER_SWIM_HEIGHT;
    }
    
    public static String getUserInformation() {
        return USER_INFORMATION;
    }
    
    public static int getUserWalkWidth() {
        return USER_WALK_WIDTH;
    }
    
    public static int getUserWalkHeight() {
        return USER_WALK_HEIGHT;
    }
    
    public TextureRegion[] getUserIdleOnWater() {
        return userIdleOnWater;
    }
    
    public Animation[] getUserSwim() {
        return userSwim;
    }
    
    public boolean isSwimming() {
        return swimming;
    }
    
    public void setSwimming(boolean swimming) {
        this.swimming = swimming;
    }
    
    public void resetPosition() {
        resetPosition(getMapCreator(), false);
    }
    
    public void resetPosition(MapCreator mapCreator, boolean resetMapCreator) {
        setMapCreator(resetMapCreator ? mapCreator : getMapCreator());
        setPositionX(mapCreator.getStartPosition().x + getUserWalkWidth() / 2);
        setPositionY(mapCreator.getStartPosition().y + getUserWalkHeight() / 2);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getMapCreator(), getHandleInput(), getUserIdleOnLand(), getUserWalk(), getPositionX(),
                            getPositionY(), getCurrentState(), getPreviousState(), getStateTimer(), getCurrentTime(),
                            getDirection(), isMoving(), isAligned(), isFutureCollision(), getUserSprite()
        );
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User)(o);
        return Float.compare(user.getPositionX(), getPositionX()) == 0 &&
               Float.compare(user.getPositionY(), getPositionY()) == 0 &&
               Float.compare(user.getStateTimer(), getStateTimer()) == 0 &&
               Double.compare(user.getCurrentTime(), getCurrentTime()) == 0 && getDirection() == user.getDirection() &&
               isMoving() == user.isMoving() && isAligned() == user.isAligned() &&
               isFutureCollision() == user.isFutureCollision() &&
               Objects.equals(getMapCreator(), user.getMapCreator()) &&
               Objects.equals(getHandleInput(), user.getHandleInput()) &&
               Arrays.equals(getUserIdleOnLand(), user.getUserIdleOnLand()) &&
               Arrays.equals(getUserWalk(), user.getUserWalk()) && getCurrentState() == user.getCurrentState() &&
               getPreviousState() == user.getPreviousState() && Objects.equals(getUserSprite(), user.getUserSprite());
    }
    
    public float getAnimationSpeed() {
        return ANIMATION_SPEED;
    }
    
    public MapCreator getMapCreator() {
        return mapCreator;
    }
    
    public void setMapCreator(MapCreator mapCreator) {
        this.mapCreator = mapCreator;
    }
    
    public Sprite getUserSprite() {
        return userSprite;
    }
    
    public void setUserSprite(Sprite userSprite) {
        this.userSprite = userSprite;
    }
    
    public boolean isFutureCollision() {
        return futureCollision;
    }
    
    public void setFutureCollision(boolean futureCollision) {
        this.futureCollision = futureCollision;
    }
    
    private Rectangle getFutureRectangle(float offsetX, float offsetY) {
        Rectangle futureRectangle = new Rectangle(getX(), getY(), Engine.getTileSize() / 2, Engine.getTileSize() / 2);
        futureRectangle.setX(futureRectangle.getX() + offsetX);
        futureRectangle.setY(futureRectangle.getY() + offsetY);
        return futureRectangle;
    }
    
    @Override
    @AnimationInitializer(actionTypes = {
                                                "Idle", "Walking", "Running", "Biking", "Using HM Move", "Fishing",
                                                "In Battle"
    })
    public void initializeAnimation() {
        //new AnimationHelper().setUpAnimation(this);
        userWalk[0] = AnimationManager
                              .getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {135, 189, 135, 206}, 6,
                                            getUserWalkWidth(), getUserWalkHeight()
                              );
        userWalk[1] = AnimationManager
                              .getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {62, 80, 62, 97}, 49,
                                            getUserWalkWidth(), getUserWalkHeight()
                              );
        userWalk[2] = AnimationManager
                              .getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {274, 291, 274, 204}, 27,
                                            getUserWalkWidth(), getUserWalkHeight()
                              );
        userWalk[3] = AnimationManager
                              .getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {52, 35, 52, 122}, 27,
                                            getUserWalkWidth(), getUserWalkHeight()
                              );
        
        userIdleOnLand[0] =
                new TextureRegion(getUserSprite().getTexture(), 135, 6, getUserWalkWidth(), getUserWalkHeight());
        userIdleOnLand[1] =
                new TextureRegion(getUserSprite().getTexture(), 62, 49, getUserWalkWidth(), getUserWalkHeight());
        userIdleOnLand[2] =
                new TextureRegion(getUserSprite().getTexture(), 274, 27, getUserWalkWidth(), getUserWalkHeight());
        userIdleOnLand[3] =
                new TextureRegion(getUserSprite().getTexture(), 52, 27, getUserWalkWidth(), getUserWalkHeight());
        
        userSwim[0] = AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {170, 192}, 97,
                                                    getUserSwimWidth(), getUserSwimHeight()
        );
        userSwim[1] = AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {125, 147}, 97,
                                                    getUserSwimWidth(), getUserSwimHeight()
        );
        userSwim[2] = AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {217, 240}, 97,
                                                    getUserSwimWidth(), getUserSwimHeight()
        );
        userSwim[3] = AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {77, 102}, 97,
                                                    getUserSwimWidth(), getUserSwimHeight()
        );
        
        userIdleOnWater[0] =
                new TextureRegion(getUserSprite().getTexture(), 170, 97, getUserSwimWidth(), getUserSwimHeight());
        userIdleOnWater[1] =
                new TextureRegion(getUserSprite().getTexture(), 125, 97, getUserSwimWidth(), getUserSwimHeight());
        userIdleOnWater[2] =
                new TextureRegion(getUserSprite().getTexture(), 217, 97, getUserSwimWidth(), getUserSwimHeight());
        userIdleOnWater[3] =
                new TextureRegion(getUserSprite().getTexture(), 77, 97, getUserSwimWidth(), getUserSwimHeight());
    }
    
    public void handleInput(float deltaTime) {
        getHandleInput().update(deltaTime);
        if(!isAligned() && !isFutureCollision()) {
            if(getDirection() == 'U') {
                setPositionY(getPositionY() + getCurrentState().getSpeed());
            } else if(getDirection() == 'D') {
                setPositionY(getPositionY() - getCurrentState().getSpeed());
            } else if(getDirection() == 'R') {
                setPositionX(getPositionX() + getCurrentState().getSpeed());
            } else if(getDirection() == 'L') {
                setPositionX(getPositionX() - getCurrentState().getSpeed());
            }
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            if(getDirection() == 'D') {
                Rectangle futureRectangle = getFutureRectangle(0, -Engine.getTileSize());
                if(isColliding(futureRectangle, false)) {
                    if(getCollidingTileObject(futureRectangle) instanceof Water) {
                        setPositionY(getPositionY() - Engine.getTileSize());
                    }
                }
            }
        }
    }
    
    private boolean isColliding(Rectangle rectangle, boolean activateCollisionMethod) {
        for(TileObject tileObject : getMapCreator().getTileObjects()) {
            if(tileObject.isColliding(rectangle)) {
                if(activateCollisionMethod) {
                    tileObject.onCollide();
                }
                return !(isSwimming() && tileObject instanceof Water);
            }
        }
        return false;
    }
    
    private TileObject getCollidingTileObject(Rectangle rectangle) {
        for(TileObject tileObject : getMapCreator().getTileObjects()) {
            if(tileObject.isColliding(rectangle)) {
                return tileObject;
            }
        }
        return null;
    }
    
    public TextureRegion[] getUserIdleOnLand() {
        return userIdleOnLand;
    }
    
    public HandleInput getHandleInput() {
        return handleInput;
    }
    
    public float getPositionX() {
        return positionX;
    }
    
    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }
    
    public float getPositionY() {
        return positionY;
    }
    
    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }
    
    @Override
    public void update(float deltaTime) {
        setAligned((getPositionX() + (Engine.getTileSize() / 2)) % Engine.getTileSize() == 0 &&
                   (getPositionY() + (Engine.getTileSize() / 2)) % Engine.getTileSize() - 1 == 0);
        
        setX(getPositionX() - getUserWalkWidth() / 2);
        setY(getPositionY() - getUserWalkHeight() / 2);
        
        setSwimming(getCollidingTileObject(
                new Rectangle(getX(), getY(), Engine.getTileSize() / 2, Engine.getTileSize() / 2)) instanceof Water);
        
        getUserSprite().setRegion(getFrame(deltaTime));
    }
    
    private TextureRegion getFrame(float deltaTime) {
        setCurrentState(getCurrentState());
        
        float stateTime = getStateTimer() * getAnimationSpeed();
        
        setStateTimer(getCurrentState() == getPreviousState() ? getStateTimer() + deltaTime : 0);
        setPreviousState(getCurrentState());
        return (getCurrentState() == State.WALKING)  ? (getDirection() == 'U') ? (TextureRegion)(getUserWalk()[0].getKeyFrame(stateTime, true)) :
                                                       (getDirection() == 'D') ? (TextureRegion)(getUserWalk()[1].getKeyFrame(stateTime, true)) :
                                                       (getDirection() == 'R') ? (TextureRegion)(getUserWalk()[2].getKeyFrame(stateTime, true)) :
                                                       (getDirection() == 'L') ? (TextureRegion)(getUserWalk()[3].getKeyFrame(stateTime, true)) : null :
               (getCurrentState() == State.SWIMMING) ? (getDirection() == 'U') ? (TextureRegion)(getUserSwim()[0].getKeyFrame(stateTime, true)) :
                                                       (getDirection() == 'D') ? (TextureRegion)(getUserSwim()[1].getKeyFrame(stateTime, true)) :
                                                       (getDirection() == 'R') ? (TextureRegion)(getUserSwim()[2].getKeyFrame(stateTime, true)) :
                                                       (getDirection() == 'L') ? (TextureRegion)(getUserSwim()[3].getKeyFrame(stateTime, true)) : null :
               (isSwimming()) ? (getDirection() == 'U') ? getUserIdleOnWater()[0] :
                                (getDirection() == 'D') ? getUserIdleOnWater()[1] :
                                (getDirection() == 'R') ? getUserIdleOnWater()[2] :
                                (getDirection() == 'L') ? getUserIdleOnWater()[3] : null :
               (getDirection() == 'U') ? getUserIdleOnLand()[0] :
               (getDirection() == 'D') ? getUserIdleOnLand()[1] :
               (getDirection() == 'R') ? getUserIdleOnLand()[2] :
               (getDirection() == 'L') ? getUserIdleOnLand()[3] : null;
    }
    
    private State getCurrentState() {
        if(isMoving()) {
            if(isSwimming()) {
                return State.SWIMMING;
            } else {
                return State.WALKING;
            }
        } else {
            return State.IDLE;
        }
    }
    
    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
    
    public Animation[] getUserWalk() {
        return userWalk;
    }
    
    public float getStateTimer() {
        return stateTimer;
    }
    
    public void setStateTimer(float stateTimer) {
        this.stateTimer = stateTimer;
    }
    
    public State getPreviousState() {
        return previousState;
    }
    
    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }
    
    public boolean isMoving() {
        return moving;
    }
    
    public void setMoving(boolean moving) {
        this.moving = moving;
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if((getCurrentState() == State.IDLE && !isSwimming()) || getCurrentState() == State.WALKING) {
            batch.draw(getUserSprite(), getPositionX() - getUserWalkWidth() / 2,
                       getPositionY() - getUserWalkHeight() / 2, getUserWalkWidth(), getUserWalkHeight()
            );
        } else if(isSwimming()) {
            batch.draw(getUserSprite(), getPositionX() - getUserSwimWidth() / 2,
                       getPositionY() - getUserSwimHeight() / 2, getUserSwimWidth(), getUserSwimHeight()
            );
        }
    }
    
    public double getCurrentTime() {
        return currentTime;
    }
    
    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }
    
    public char getDirection() {
        return direction;
    }
    
    public void setDirection(char direction) {
        this.direction = direction;
    }
    
    public boolean isAligned() {
        return aligned;
    }
    
    public void setAligned(boolean aligned) {
        this.aligned = aligned;
    }
    
    private enum State {
        IDLE(0f), WALKING(1f), RUNNING(2f), BIKING(3f), SWIMMING(1f), USING_HM_MOVE(0f), FISHING(0f), IN_BATTLE(0f);
        
        float speed;
        
        State(float speed) {
            this.speed = speed;
        }
        
        public float getSpeed() {
            return speed;
        }
    }
}