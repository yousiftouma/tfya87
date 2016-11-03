package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entity.AbstractEntity;

import java.util.ArrayList;

/**
 * Created by youto814 on 03/11/16.
 */
public class Window extends ApplicationAdapter {
    SpriteBatch batch;
    AsteroidsGame game;
    Texture background;

    @Override
    public void create () {
        batch = new SpriteBatch();
        game = new AsteroidsGame();
        background = new Texture(Gdx.files.internal("Space.jpg"));
    }

    @Override
    public void render () {
        final float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);

        if (!game.isGameOver()){
            for (AbstractEntity entity : game.getEntities()) {
                entity.draw(batch);
            }
            batch.end();
            game.updateGame(delta);
        }
        else{
            System.exit(0);
        }
    }

    @Override
    public void dispose () {
        batch.dispose();
        background.dispose();
    }
}
