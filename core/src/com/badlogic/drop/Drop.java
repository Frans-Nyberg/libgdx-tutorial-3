package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

import java.awt.*;

public class Drop extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private Sound dropSound;
	private Music rainMusic;
	private Texture dropImage;
	private Texture bucketImage;
	private OrthographicCamera camera;
	private Rectangle bucket;

    @Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		// store location of bucket
		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		// load 64x64 images
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		// load music and sound effect
        dropSound = Gdx.audio.newSound(Gdx.files.internal("hatch.ogg"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("snow.ogg"));

        // play music immediately
        rainMusic.setLooping(true);
        rainMusic.play();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
	}

	@Override
	public void render () {
        // set clear colour to blue range [0, 1]
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		// clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// update once per frame
		camera.update();

		// render bucket
        // projection matrix set
        batch.setProjectionMatrix(camera.combined);
        // render as many images as possible at once
        batch.begin();
        batch.draw(bucketImage, bucket.x, bucket.y);
        batch.end();

        // input
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

        // stay within screen
        if(bucket.x < 0) bucket.x = 0;
        if(bucket.x > 800 - 64) bucket.x = 800 - 64;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
