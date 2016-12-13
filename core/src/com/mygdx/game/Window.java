package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private double time;
    private String score;

    private BitmapFont scoreBmf;

    @Override
    public void create () {
	batch = new SpriteBatch();
	game = new AsteroidsGame();
	background = new Texture(Gdx.files.internal("Space.jpg"));
	this.score = "";
	this.scoreBmf = new BitmapFont();
	this.time = 0f;
    }

    @Override
    public void render () {
	Gdx.gl.glClearColor(1, 0, 0, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	batch.begin();
	batch.draw(background, 0, 0);

	if (!game.isGameOver()){
	    final float delta = Gdx.graphics.getDeltaTime();
	    time += delta;
	    double timeToShow = Math.floor(time);
	    score = "Time survived: " + timeToShow;
	    for (AbstractEntity entity : game.getEntities()) {
		entity.draw(batch);
	    }
	    scoreBmf.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	    scoreBmf.draw(batch, score, AsteroidsGame.GAME_WIDTH/2-85f, AsteroidsGame.GAME_HEIGHT-10f);
	    batch.end();
	    game.updateGame(delta);
	}
	else{
	    String finalScore = score;
	    scoreBmf.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	    scoreBmf.draw(batch, finalScore, AsteroidsGame.GAME_WIDTH/2-85f, AsteroidsGame.GAME_HEIGHT-10f);
	    batch.end();
	    this.pause();
	    //Gdx.app.exit();
	}
    }

    @Override
    public void dispose () {
	batch.dispose();
	background.dispose();
    }
}
