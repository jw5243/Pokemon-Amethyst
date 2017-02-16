package com.horse.pokemon.amethyst.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.horse.pokemon.amethyst.Engine;

public class Hud implements Disposable {
    public  Stage    stage;
    private Label    moneyLabel;
    private Viewport viewport;
    private int      money;
    
    public Hud(Engine engine) {
        money = 0;
        viewport = new FitViewport(Engine.getvWidth(), Engine.getvHeight(), new OrthographicCamera());
        stage = new Stage(viewport, engine.getBatch());
        
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        
        moneyLabel = new Label(String.format("Money: %d", money), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(moneyLabel).expandX().padTop(10);
        stage.addActor(table);
    }
    
    public void addMoney(int money) {
        this.money += money;
        moneyLabel.setText(String.format("Money: %d", money));
    }
    
    @Override
    public void dispose() {
        stage.dispose();
    }
}