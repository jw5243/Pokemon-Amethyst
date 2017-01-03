package com.horse.pokemon.ObjectData.Players;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.horse.pokemon.AnimationEngine.AnimationInitializer;
import com.horse.pokemon.AnimationEngine.AnimationInterface;
import com.horse.pokemon.Engine;
import com.horse.pokemon.GraphicsEngine.MainInterface.HandleInput;
import com.horse.pokemon.GraphicsEngine.ScreenEngine.MainGameScreen;
import com.horse.pokemon.GraphicsEngine.ScreenEngine.MapCreator;
import com.horse.pokemon.ObjectData.TiledObjects.TileObject;

import java.util.Arrays;
import java.util.Objects;

public final class User extends AbstractPlayer implements AnimationInterface {
    private static final int    USER_WIDTH       = 16;
    private static final int    USER_HEIGHT      = 19;
    private static final float  ANIMATION_SPEED  = 0.5f;
    private static final String USER_INFORMATION = "User\\GDX_Users\\User.pack";
    private final HandleInput     handleInput;
    private final TextureRegion[] userIdle;
    private final Animation[]     userWalk;
    private final Animation[] userSwim;
    private       MapCreator  mapCreator;
    private       float       positionX;
    private       float       positionY;
    private       State       currentState;
    private       State       previousState;
    private       float       stateTimer;
    private       double      currentTime;
    private       char        direction;
    private       boolean     moving;
    private       boolean     aligned;
    private       boolean     futureCollision;
    private       Sprite      userSprite;
    private boolean swimming;
    
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
        
        userIdle = new TextureRegion[4];
        userWalk = new Animation[4];
        userSwim = new Animation[4];
        
        initializeAnimation();
        
        setBounds(0, 0, Engine.getTileSize() / 2, Engine.getTileSize() / 2);
        getUserSprite().setRegion(getUserIdle()[1]);
        
        resetPosition();
        
        handleInput = new HandleInput((float dt) -> {
            if(isAligned()) {
                setMoving(true);
                setAligned(false);
                setDirection('U');
                setFutureCollision(isColliding(getFutureRectangle(0, Engine.getTileSize())));
            }
        }, (float dt) -> {
            if(isAligned()) {
                setMoving(true);
                setAligned(false);
                setDirection('D');
                setFutureCollision(isColliding(getFutureRectangle(0, -Engine.getTileSize())));
            }
        }, (float dt) -> {
            if(isAligned()) {
                setMoving(true);
                setAligned(false);
                setDirection('R');
                setFutureCollision(isColliding(getFutureRectangle(Engine.getTileSize(), 0)));
            }
        }, (float dt) -> {
            if(isAligned()) {
                setMoving(true);
                setAligned(false);
                setDirection('L');
                setFutureCollision(isColliding(getFutureRectangle(-Engine.getTileSize(), 0)));
            }
        }, (float dt) -> {
            if(isAligned()) {
                setMoving(false);
            }
        });
    }
    
    public static String getUserInformation() {
        return USER_INFORMATION;
    }
    
    public static int getUserWidth() {
        return USER_WIDTH;
    }
    
    public static int getUserHeight() {
        return USER_HEIGHT;
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
        setPositionX(mapCreator.getStartPosition().x + getUserWidth() / 2);
        setPositionY(mapCreator.getStartPosition().y + getUserHeight() / 2);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getMapCreator(), getHandleInput(), getUserIdle(), getUserWalk(), getPositionX(),
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
               Arrays.equals(getUserIdle(), user.getUserIdle()) && Arrays.equals(getUserWalk(), user.getUserWalk()) &&
               getCurrentState() == user.getCurrentState() && getPreviousState() == user.getPreviousState() &&
               Objects.equals(getUserSprite(), user.getUserSprite());
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
        Array<TextureRegion> frames = new Array<>();
        for(int frameIndex = 0; frameIndex <= 3; frameIndex++) {
            int yPosition = 6;
            int xPosition = (frameIndex == 0) ? 135 : (frameIndex == 1) ? 189 : (frameIndex == 3) ? 206 : 135;
            frames.add(new TextureRegion(getUserSprite().getTexture(), xPosition, yPosition, getUserWidth(),
                                         getUserHeight()
            ));
        }
        userWalk[0] = new Animation<>(0.1f, frames);
        frames.clear();
        
        for(int frameIndex = 0; frameIndex <= 3; frameIndex++) {
            int yPosition = 49;
            int xPosition = (frameIndex == 0) ? 62 : (frameIndex == 1) ? 80 : (frameIndex == 3) ? 97 : 62;
            frames.add(new TextureRegion(getUserSprite().getTexture(), xPosition, yPosition, getUserWidth(),
                                         getUserHeight()
            ));
        }
        userWalk[1] = new Animation<>(0.1f, frames);
        frames.clear();
        
        for(int frameIndex = 0; frameIndex <= 3; frameIndex++) {
            int yPosition = 27;
            int xPosition = (frameIndex == 0) ? 274 : (frameIndex == 1) ? 291 : (frameIndex == 3) ? 204 : 274;
            frames.add(new TextureRegion(getUserSprite().getTexture(), xPosition, yPosition, getUserWidth(),
                                         getUserHeight()
            ));
        }
        userWalk[2] = new Animation<>(0.1f, frames);
        frames.clear();
        
        for(int frameIndex = 0; frameIndex <= 3; frameIndex++) {
            int yPosition = 27;
            int xPosition = (frameIndex == 0) ? 52 : (frameIndex == 1) ? 35 : (frameIndex == 3) ? 122 : 52;
            frames.add(new TextureRegion(getUserSprite().getTexture(), xPosition, yPosition, getUserWidth(),
                                         getUserHeight()
            ));
        }
        userWalk[3] = new Animation<>(0.1f, frames);
        frames.clear();
        
        userIdle[0] = new TextureRegion(getUserSprite().getTexture(), 101, 6, getUserWidth(), getUserHeight());
        userIdle[1] = new TextureRegion(getUserSprite().getTexture(), 62, 49, getUserWidth(), getUserHeight());
        userIdle[2] = new TextureRegion(getUserSprite().getTexture(), 274, 27, getUserWidth(), getUserHeight());
        userIdle[3] = new TextureRegion(getUserSprite().getTexture(), 52, 27, getUserWidth(), getUserHeight());
        
        final int userSwimLength = 22;
        final int userSwimHeight = 24;
        
        for(int frameIndex = 0; frameIndex <= 2; frameIndex++) {
            int yPosition = 97;
            int xPosition = (frameIndex == 0) ? 170 : 192;
            frames.add(new TextureRegion(getUserSprite().getTexture(), xPosition, yPosition, userSwimLength,
                                         userSwimHeight
            ));
        }
        userSwim[0] = new Animation<>(0.1f, frames);
        frames.clear();
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
        }
    }
    
    private boolean isColliding(Rectangle rectangle) {
        for(TileObject tileObject : getMapCreator().getTileObjects()) {
            if(tileObject.isColliding(rectangle)) {
                tileObject.onCollide();
                return true;
            }
        }
        return false;
    }
    
    public TextureRegion[] getUserIdle() {
        return userIdle;
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
        setX(getPositionX() - getUserWidth() / 2);
        setY(getPositionY() - getUserHeight() / 2);
        getUserSprite().setRegion(getFrame(deltaTime));
    }
    
    private TextureRegion getFrame(float deltaTime) {
        setCurrentState(getCurrentState());
        
        TextureRegion region = new TextureRegion();
        if(getCurrentState() == State.WALKING) {
            if(getDirection() == 'U') {
                region = (TextureRegion)(getUserWalk()[0].getKeyFrame(getStateTimer() * getAnimationSpeed(), true));
            } else if(getDirection() == 'D') {
                region = (TextureRegion)(getUserWalk()[1].getKeyFrame(getStateTimer() * getAnimationSpeed(), true));
            } else if(getDirection() == 'R') {
                region = (TextureRegion)(getUserWalk()[2].getKeyFrame(getStateTimer() * getAnimationSpeed(), true));
            } else if(getDirection() == 'L') {
                region = (TextureRegion)(getUserWalk()[3].getKeyFrame(getStateTimer() * getAnimationSpeed(), true));
            }
        } else if(getCurrentState() == State.SWIMMING) {
            if(getDirection() == 'U') {
                region = (TextureRegion)(getUserSwim()[0].getKeyFrame(getStateTimer() * getAnimationSpeed(), true));
            } else {
                region = (TextureRegion)(getUserSwim()[0].getKeyFrame(getStateTimer() * getAnimationSpeed(), true));
            }
        } else {
            if(getDirection() == 'U') {
                region = getUserIdle()[0];
            } else if(getDirection() == 'D') {
                region = getUserIdle()[1];
            } else if(getDirection() == 'R') {
                region = getUserIdle()[2];
            } else if(getDirection() == 'L') {
                region = getUserIdle()[3];
            }
        }
        setStateTimer(getCurrentState() == getPreviousState() ? getStateTimer() + deltaTime : 0);
        setPreviousState(getCurrentState());
        return region;
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
        batch.draw(getUserSprite(), getPositionX() - getUserWidth() / 2, getPositionY() - getUserHeight() / 2,
                   getUserWidth(), getUserHeight()
        );
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