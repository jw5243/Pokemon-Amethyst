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
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class User extends AbstractPlayer implements AnimationInterface {
    private static final int    USER_WALK_WIDTH        = 16;
    private static final int    USER_WALK_HEIGHT       = 19;
    private static final int    USER_SWIM_WIDTH        = 22;
    private static final int    USER_SWIM_HEIGHT       = 24;
    private static final float  ANIMATION_SPEED        = 0.5f;
    private static final String USER_INFORMATION       = "User\\GDX_Users\\User.pack";
    private static final String USER_ATLAS_REGION_NAME = "SpriteSheetUser";
    private final TextureRegion[] userIdleOnLand;
    private final TextureRegion[] userIdleOnWater;
    private final Animation[]     userWalk;
    private final Animation[]     userSwim;
    private final HandleInput     handleInput;
    private       MapCreator      mapCreator;
    private       Sprite          userSprite;
    private       Rectangle       currentCollisionRectangle;
    private       State           previousState;
    private       float           positionX;
    private       float           positionY;
    private       float           stateTimer;
    private       boolean         moving;
    private       boolean         aligned;
    private       boolean         futureCollision;
    private       boolean         swimming;
    private       char            direction;
    
    public User(MainGameScreen screen, MapCreator mapCreator) {
        super(screen, getUserAtlasRegionName());
        
        setPreviousState(getCurrentState());
        
        setStateTimer(0);
        setDirection('D');
        
        setMoving(false);
        setAligned(true);
        setFutureCollision(false);
        setSwimming(false);
        
        setUserSprite(new Sprite(screen.getAtlas().findRegion(getUserAtlasRegionName())));
        
        setMapCreator(mapCreator);
        
        userIdleOnLand = new TextureRegion[4];
        userIdleOnWater = new TextureRegion[4];
        
        userWalk = new Animation[4];
        userSwim = new Animation[4];
        
        initializeAnimation();
        
        setBounds(0, 0, Engine.getTileSize() / 2, Engine.getTileSize() / 2);
        getUserSprite().setRegion(getUserIdleOnLand()[1]);
        
        resetPosition();
        
        updateCurrentCollisionRectangle();
        
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
    
    private static String getUserAtlasRegionName() {
        return USER_ATLAS_REGION_NAME;
    }
    
    private static int getUserSwimWidth() {
        return USER_SWIM_WIDTH;
    }
    
    private static int getUserSwimHeight() {
        return USER_SWIM_HEIGHT;
    }
    
    public static String getUserInformation() {
        return USER_INFORMATION;
    }
    
    private static int getUserWalkWidth() {
        return USER_WALK_WIDTH;
    }
    
    private static int getUserWalkHeight() {
        return USER_WALK_HEIGHT;
    }
    
    private Rectangle getCurrentCollisionRectangle() {
        return currentCollisionRectangle;
    }
    
    private void setCurrentCollisionRectangle(Rectangle currentCollisionRectangle) {
        this.currentCollisionRectangle = currentCollisionRectangle;
    }
    
    private void updateCurrentCollisionRectangle() {
        setCurrentCollisionRectangle(new Rectangle(getX(), getY(), Engine.getTileSize() / 2, Engine.getTileSize() / 2));
    }
    
    private TextureRegion[] getUserIdleOnWater() {
        return userIdleOnWater;
    }
    
    private Animation[] getUserSwim() {
        return userSwim;
    }
    
    private boolean isSwimming() {
        return swimming;
    }
    
    private void setSwimming(boolean swimming) {
        this.swimming = swimming;
    }
    
    private void resetPosition() {
        resetPosition(getMapCreator(), false);
    }
    
    public void resetPosition(MapCreator mapCreator, boolean resetMapCreator) {
        setMapCreator(resetMapCreator ? mapCreator : getMapCreator());
        setPositionX(mapCreator.getStartPosition().x + getUserWalkWidth() / 2);
        setPositionY(mapCreator.getStartPosition().y + getUserWalkHeight() / 2);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getMapCreator(), getHandleInput(), getUserIdleOnLand(), getUserWalk(), getPositionX(), getPositionY(), getCurrentState(),
                            getPreviousState(), getStateTimer(), getDirection(), isMoving(), isAligned(), isFutureCollision(), getUserSprite()
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
        return Float.compare(user.getPositionX(), getPositionX()) == 0 && Float.compare(user.getPositionY(), getPositionY()) == 0 &&
               Float.compare(user.getStateTimer(), getStateTimer()) == 0 && getDirection() == user.getDirection() && isMoving() == user.isMoving() &&
               isAligned() == user.isAligned() && isFutureCollision() == user.isFutureCollision() &&
               Objects.equals(getMapCreator(), user.getMapCreator()) && Objects.equals(getHandleInput(), user.getHandleInput()) &&
               Arrays.equals(getUserIdleOnLand(), user.getUserIdleOnLand()) && Arrays.equals(getUserWalk(), user.getUserWalk()) &&
               getCurrentState() == user.getCurrentState() && getPreviousState() == user.getPreviousState() &&
               Objects.equals(getUserSprite(), user.getUserSprite());
    }
    
    private float getAnimationSpeed() {
        return ANIMATION_SPEED;
    }
    
    private MapCreator getMapCreator() {
        return mapCreator;
    }
    
    public void setMapCreator(MapCreator mapCreator) {
        this.mapCreator = mapCreator;
    }
    
    private Sprite getUserSprite() {
        return userSprite;
    }
    
    private void setUserSprite(Sprite userSprite) {
        this.userSprite = userSprite;
    }
    
    private boolean isFutureCollision() {
        return futureCollision;
    }
    
    private void setFutureCollision(boolean futureCollision) {
        this.futureCollision = futureCollision;
    }
    
    private Rectangle getFutureRectangle(float offsetX, float offsetY) {
        Rectangle futureRectangle = getCurrentCollisionRectangle();
        futureRectangle.setX(futureRectangle.getX() + offsetX);
        futureRectangle.setY(futureRectangle.getY() + offsetY);
        return futureRectangle;
    }
    
    @Override
    @AnimationInitializer(actionTypes = {
                                                "Idle", "Walking", "Running", "Biking", "Using HM Move", "Fishing", "In Battle"
    })
    public void initializeAnimation() {
        //new AnimationHelper().setUpAnimation(this);
        userWalk[0] = AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {189, 135, 206, 135}, 6, getUserWalkWidth(),
                                                    getUserWalkHeight()
        );
        userWalk[1] = AnimationManager
                              .getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {80, 62, 97, 62}, 49, getUserWalkWidth(), getUserWalkHeight());
        userWalk[2] = AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {291, 274, 204, 274}, 27, getUserWalkWidth(),
                                                    getUserWalkHeight()
        );
        userWalk[3] = AnimationManager.getAnimation(getUserSprite().getTexture(), 4, 0.1f, new int[] {35, 52, 122, 52}, 27, getUserWalkWidth(),
                                                    getUserWalkHeight()
        );
        
        userIdleOnLand[0] = new TextureRegion(getUserSprite().getTexture(), 135, 6, getUserWalkWidth(), getUserWalkHeight());
        userIdleOnLand[1] = new TextureRegion(getUserSprite().getTexture(), 62, 49, getUserWalkWidth(), getUserWalkHeight());
        userIdleOnLand[2] = new TextureRegion(getUserSprite().getTexture(), 274, 27, getUserWalkWidth(), getUserWalkHeight());
        userIdleOnLand[3] = new TextureRegion(getUserSprite().getTexture(), 52, 27, getUserWalkWidth(), getUserWalkHeight());
        
        userSwim[0] =
                AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {192, 170}, 97, getUserSwimWidth(), getUserSwimHeight());
        userSwim[1] =
                AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {147, 125}, 97, getUserSwimWidth(), getUserSwimHeight());
        userSwim[2] =
                AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {217, 240}, 97, getUserSwimWidth(), getUserSwimHeight());
        userSwim[3] =
                AnimationManager.getAnimation(getUserSprite().getTexture(), 2, 0.1f, new int[] {102, 77}, 97, getUserSwimWidth(), getUserSwimHeight());
        
        userIdleOnWater[0] = new TextureRegion(getUserSprite().getTexture(), 170, 97, getUserSwimWidth(), getUserSwimHeight());
        userIdleOnWater[1] = new TextureRegion(getUserSprite().getTexture(), 125, 97, getUserSwimWidth(), getUserSwimHeight());
        userIdleOnWater[2] = new TextureRegion(getUserSprite().getTexture(), 240, 97, getUserSwimWidth(), getUserSwimHeight());
        userIdleOnWater[3] = new TextureRegion(getUserSprite().getTexture(), 77, 97, getUserSwimWidth(), getUserSwimHeight());
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
            AlterPlayerPosition alterPlayerPosition =
                    (Consumer<Float> setPositionMethod, Supplier<Float> getPositionMethod, float alterValue) -> setPositionMethod
                                                                                                                        .accept(getPositionMethod.get() +
                                                                                                                                alterValue);
    
            Rectangle futureRectangle = (getDirection() == 'U') ? getFutureRectangle(0, Engine.getTileSize()) :
                                        (getDirection() == 'D') ? getFutureRectangle(0, -Engine.getTileSize()) :
                                        (getDirection() == 'R') ? getFutureRectangle(Engine.getTileSize(), 0) :
                                        getFutureRectangle(-Engine.getTileSize(), 0);
    
            Runnable alterAction =
                    (getDirection() == 'U') ? () -> alterPlayerPosition.alterPosition(this::setPositionY, this::getPositionY, Engine.getTileSize()) :
                    (getDirection() == 'D') ? () -> alterPlayerPosition.alterPosition(this::setPositionY, this::getPositionY, -Engine.getTileSize()) :
                    (getDirection() == 'R') ? () -> alterPlayerPosition.alterPosition(this::setPositionX, this::getPositionX, Engine.getTileSize()) :
                    () -> alterPlayerPosition.alterPosition(this::setPositionX, this::getPositionX, -Engine.getTileSize());
    
            if(isColliding(futureRectangle, false) && getCollidingTileObject(futureRectangle) instanceof Water) {
                alterAction.run();
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
    
    private TextureRegion[] getUserIdleOnLand() {
        return userIdleOnLand;
    }
    
    private HandleInput getHandleInput() {
        return handleInput;
    }
    
    public float getPositionX() {
        return positionX;
    }
    
    private void setPositionX(float positionX) {
        this.positionX = positionX;
    }
    
    public float getPositionY() {
        return positionY;
    }
    
    private void setPositionY(float positionY) {
        this.positionY = positionY;
    }
    
    @Override
    public void update(float deltaTime) {
        updateAlignment();
        updateActorXY();
        updateCurrentCollisionRectangle();
        updateSwimming();
        updateAnimation(deltaTime);
    }
    
    private void updateAlignment() {
        setAligned((getPositionX() + (Engine.getTileSize() / 2)) % Engine.getTileSize() == 0 &&
                   (getPositionY() + (Engine.getTileSize() / 2)) % Engine.getTileSize() - 1 == 0);
    }
    
    private void updateSwimming() {
        setSwimming(getCollidingTileObject(getCurrentCollisionRectangle()) instanceof Water);
    }
    
    private void updateAnimation(float deltaTime) {
        getUserSprite().setRegion(getFrame(deltaTime));
    }
    
    private void updateActorXY() {
        setX(getPositionX() - getUserWalkWidth() / 2);
        setY(getPositionY() - getUserWalkHeight() / 2);
    }
    
    private TextureRegion getFrame(float deltaTime) {
        float stateTime = getStateTimer() * getAnimationSpeed();
        
        setStateTimer(getCurrentState() == getPreviousState() ? getStateTimer() + deltaTime : 0);
        setPreviousState(getCurrentState());
        return (getCurrentState() == State.WALKING) ? (getDirection() == 'U') ? (TextureRegion)(getUserWalk()[0].getKeyFrame(stateTime, true)) :
                                                      (getDirection() == 'D') ? (TextureRegion)(getUserWalk()[1].getKeyFrame(stateTime, true)) :
                                                      (getDirection() == 'R') ? (TextureRegion)(getUserWalk()[2].getKeyFrame(stateTime, true)) :
                                                      (TextureRegion)(getUserWalk()[3].getKeyFrame(stateTime, true)) :
               (getCurrentState() == State.SWIMMING) ? (getDirection() == 'U') ? (TextureRegion)(getUserSwim()[0].getKeyFrame(stateTime, true)) :
                                                       (getDirection() == 'D') ? (TextureRegion)(getUserSwim()[1].getKeyFrame(stateTime, true)) :
                                                       (getDirection() == 'R') ? (TextureRegion)(getUserSwim()[2].getKeyFrame(stateTime, true)) :
                                                       (TextureRegion)(getUserSwim()[3].getKeyFrame(stateTime, true)) : (isSwimming()) ?
                                                                                                                        (getDirection() == 'U') ?
                                                                                                                        getUserIdleOnWater()[0] :
                                                                                                                        (getDirection() == 'D') ?
                                                                                                                        getUserIdleOnWater()[1] :
                                                                                                                        (getDirection() == 'R') ?
                                                                                                                        getUserIdleOnWater()[2] :
                                                                                                                        getUserIdleOnWater()[3] :
                                                                                                                        (getDirection() == 'U') ?
                                                                                                                        getUserIdleOnLand()[0] :
                                                                                                                        (getDirection() == 'D') ?
                                                                                                                        getUserIdleOnLand()[1] :
                                                                                                                        (getDirection() == 'R') ?
                                                                                                                        getUserIdleOnLand()[2] :
                                                                                                                        getUserIdleOnLand()[3];
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
    
    private Animation[] getUserWalk() {
        return userWalk;
    }
    
    private float getStateTimer() {
        return stateTimer;
    }
    
    private void setStateTimer(float stateTimer) {
        this.stateTimer = stateTimer;
    }
    
    private State getPreviousState() {
        return previousState;
    }
    
    private void setPreviousState(State previousState) {
        this.previousState = previousState;
    }
    
    private boolean isMoving() {
        return moving;
    }
    
    private void setMoving(boolean moving) {
        this.moving = moving;
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if((getCurrentState() == State.IDLE && !isSwimming()) || getCurrentState() == State.WALKING) {
            batch.draw(getUserSprite(), getPositionX() - getUserWalkWidth() / 2, getPositionY() - getUserWalkHeight() / 2, getUserWalkWidth(),
                       getUserWalkHeight()
            );
        } else if(isSwimming()) {
            batch.draw(getUserSprite(), getPositionX() - getUserSwimWidth() / 2, getPositionY() - getUserSwimHeight() / 2, getUserSwimWidth(),
                       getUserSwimHeight()
            );
        }
    }
    
    private char getDirection() {
        return direction;
    }
    
    private void setDirection(char direction) {
        this.direction = direction;
    }
    
    private boolean isAligned() {
        return aligned;
    }
    
    private void setAligned(boolean aligned) {
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