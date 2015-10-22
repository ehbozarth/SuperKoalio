package com.theironyard.superkoalio;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SuperKoalio extends ApplicationAdapter {
    SpriteBatch batch;

    TextureRegion stand;
    TextureRegion jump;
    Animation walk;
    FitViewport viewport;
    OrthogonalTiledMapRenderer renderer;

    float x = 0;
    float y = 0;
    //V means Velocity
    float xV = 0;
    float yV = 0;
    float time = 0;
    boolean canJump;

    final int WIDTH = 18;
    final int HEIGHT = 26;
    final float MAX_VELOCITY = 500; //100 Pixels per Second
    final int DRAW_WIDTH = WIDTH * 3;
    final int DRAW_HEIGHT = HEIGHT * 3;
    final int GRAVITY = - 50;
    final float MAX_JUMP_VELOCITY = 1000;


    @Override
    public void create () {
        batch = new SpriteBatch();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Texture sheet = new Texture("koalio.png");
        TextureRegion[][] tiles = TextureRegion.split(sheet, WIDTH, HEIGHT);
        stand = tiles[0][0];
        jump = tiles[0][1];
        walk = new Animation(0.1f, tiles[0][2], tiles[0][3], tiles[0][4]);



    }//End of Create

    @Override
    public void render () {
        move();
        draw();

    }//End of Render Method

    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
    }//End of Override Resize Method

    public void move(){
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && canJump){
            yV = MAX_JUMP_VELOCITY;
            canJump = false;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            yV = MAX_VELOCITY * -1;
            //y--;
        }//Down Arrow Key
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            xV = MAX_VELOCITY;
            //x++;
        }//Right Arrow Key
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            xV = MAX_VELOCITY * -1;
            //x--;
        }//Left Arrow Key

        yV += GRAVITY;

        //Acceleration with Velocity per refreshed Frame
        x += xV * Gdx.graphics.getDeltaTime();
        y += yV * Gdx.graphics.getDeltaTime();

        if(y <0){
            y = 0;
            canJump = true;
        }
        //Dampening Velocity
        xV *= 0.8;
        yV *= 0.9;

    }//END OF MOVE METHOD

    void draw(){
        time += Gdx.graphics.getDeltaTime();

        TextureRegion img;
        if(y > 0 ){
            img = jump;
        }
        else if(Math.round(xV) != 0){
            img = walk.getKeyFrame(time,true);
        }
        else{
            img = stand;
        }

        Gdx.gl.glClearColor(0,0.5f,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        // draw here
        if(xV>=0){
            batch.draw(img, x, y, DRAW_WIDTH, DRAW_HEIGHT);
        }
        else{
            batch.draw(img, x + DRAW_WIDTH,y, DRAW_WIDTH * -1, DRAW_HEIGHT);
        }
        batch.end();
    }//End of Draw Method

}//End of SuperKoalio Class