package com.custardgames.worldtrotter.states;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.custardgames.worldtrotter.Core;
import com.custardgames.worldtrotter.entities.ecs.components.Box2dBodyComponent;
import com.custardgames.worldtrotter.entities.ecs.components.Box2dCameraComponent;
import com.custardgames.worldtrotter.entities.ecs.components.CameraInputComponent;
import com.custardgames.worldtrotter.entities.ecs.components.CharacterDataComponent;
import com.custardgames.worldtrotter.entities.ecs.components.CharacterFeetComponent;
import com.custardgames.worldtrotter.entities.ecs.components.CharacterInputComponent;
import com.custardgames.worldtrotter.entities.ecs.components.CharacterLegComponent;
import com.custardgames.worldtrotter.entities.ecs.factories.Box2DMapObjectParserFactory;
import com.custardgames.worldtrotter.entities.ecs.systems.CameraMovementSystem;
import com.custardgames.worldtrotter.entities.ecs.systems.CharacterMovementSystem;
import com.custardgames.worldtrotter.entities.ecs.systems.JointAngleTargetingSystem;
import com.custardgames.worldtrotter.entities.ecs.systems.UpdatePhysicalCharacterInputSystem;
import com.custardgames.worldtrotter.events.ChangeWorldEvent;
import com.custardgames.worldtrotter.events.NextLevelEvent;
import com.custardgames.worldtrotter.events.PlayerDiedEvent;
import com.custardgames.worldtrotter.handlers.ContactHandler;
import com.custardgames.worldtrotter.managers.EventManager;

import net.dermetfan.gdx.graphics.g2d.AnimatedBox2DSprite;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Play implements Screen, EventListener {
    public static final float TICK_STEP = 1 / 60f;
    private AssetManager assetManager;
    private World artemisWorld;
    private com.badlogic.gdx.physics.box2d.World box2dWorld;
    private ContactHandler contactHandler;
    private boolean reset;
    private int level;
    private int levelCooldown;
    private OrthographicCamera b2dCam;
    private Box2DDebugRenderer b2dr;
    private TiledMap tileMap1;
    private OrthogonalTiledMapRenderer tmr1;
    private TiledMap tileMap2;
    private OrthogonalTiledMapRenderer tmr2;
    private TiledMap tileMap3;
    private OrthogonalTiledMapRenderer tmr3;
    private int currentWorld;
    private Stage background;
    private Image backgroundImage;
    private Stage ui;
    private Image colourWheel;
    private RayHandler handler;
    private DirectionalLight sun1;
    private DirectionalLight sun2;
    private Music music;
    private SpriteBatch spriteBatch;
    private List<Body> b2dPlayerBodies;
    private List<Box2DSprite> b2dSprites;
    private float tickCounter;
    private float frameCounter;
    private float secondCounter;

    public Play() {
        EventManager.getInstance().register(ChangeWorldEvent.class, this);
        EventManager.getInstance().register(PlayerDiedEvent.class, this);
        EventManager.getInstance().register(NextLevelEvent.class, this);
        assetManager = new PlayLoadAssets().loadAssets(new AssetManager());
        level = 0;
        init();

        music = assetManager.get("music/music.mp3");
        music.setLooping(true);
        music.setVolume(1f);
        music.play();
    }

    public void init() {
        currentWorld = 1;
        reset = false;

        levelCooldown = 60;

        box2dWorld = new com.badlogic.gdx.physics.box2d.World(new Vector2(0, -20f), true);
        box2dWorld.setContactListener(new ContactHandler());

        contactHandler = new ContactHandler();
        box2dWorld.setContactListener(contactHandler);
        b2dr = new Box2DDebugRenderer();

        artemisWorld = new World(new WorldConfiguration()
                .setSystem(new CharacterMovementSystem())
                .setSystem(new CameraMovementSystem())
                .setSystem(new UpdatePhysicalCharacterInputSystem())
                .setSystem(new JointAngleTargetingSystem()));

        b2dCam = new OrthographicCamera();
        b2dCam.setToOrtho(false, Core.WIDTH, Core.HEIGHT);
        b2dCam.zoom = 1f;
        b2dCam.update();
        artemisWorld.getSystem(UpdatePhysicalCharacterInputSystem.class).setCamera(b2dCam);

        tileMap1 = new TmxMapLoader().load("maps/world1/level" + level + ".tmx");
        Box2DMapObjectParserFactory box2DMapObjectParser = new Box2DMapObjectParserFactory();
        box2DMapObjectParser.load(box2dWorld, tileMap1);

        tmr1 = new OrthogonalTiledMapRenderer(tileMap1, box2DMapObjectParser.getUnitScale());

        tileMap2 = new TmxMapLoader().load("maps/world2/level" + level + ".tmx");
        tmr2 = new OrthogonalTiledMapRenderer(tileMap2, box2DMapObjectParser.getUnitScale());

        tileMap3 = new TmxMapLoader().load("maps/world3/level" + level + ".tmx");
        tmr3 = new OrthogonalTiledMapRenderer(tileMap3, box2DMapObjectParser.getUnitScale());

        Body playerBody = box2DMapObjectParser.getBodies().get("player");

        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(b2dCam.combined);
        b2dPlayerBodies = new ArrayList<Body>();
        b2dSprites = new ArrayList<Box2DSprite>();

        b2dPlayerBodies.add(playerBody);
        b2dSprites.add(new Box2DSprite(new Sprite((Texture) assetManager.get("images/player.png"))));

        Entity player = artemisWorld.createEntity();
        player.edit().add(new Box2dBodyComponent(playerBody));
        player.edit().add(new CharacterInputComponent());
        player.edit().add(new CharacterFeetComponent(box2DMapObjectParser.getFixtures().get("feet")));
        player.edit().add(new CharacterLegComponent(box2DMapObjectParser.getFixtures().get("leg")));
        player.edit().add(new CharacterDataComponent());

        TextureRegion frame1 = new TextureRegion((Texture) assetManager.get("images/portal.png"), 0, 0, 32, 64);
        TextureRegion frame2 = new TextureRegion((Texture) assetManager.get("images/portal.png"), 32, 0, 32, 64);
        TextureRegion frame3 = new TextureRegion((Texture) assetManager.get("images/portal.png"), 64, 0, 32, 64);
        Animation animation = new Animation(1 / 20f, frame1, frame2, frame3);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        AnimatedSprite animatedSprite = new AnimatedSprite(animation);
        AnimatedBox2DSprite animatedBox2DSprite = new AnimatedBox2DSprite(animatedSprite);
        Body portal = box2DMapObjectParser.getBodies().get("nextlevel");
        b2dPlayerBodies.add(portal);
        b2dSprites.add(animatedBox2DSprite);

        if (box2DMapObjectParser.getBodies().containsKey("title")) {
            b2dPlayerBodies.add(box2DMapObjectParser.getBodies().get("title"));
            b2dSprites.add(new Box2DSprite(new Sprite((Texture) assetManager.get("images/title.png"))));
        }

        if (box2DMapObjectParser.getBodies().containsKey("title2")) {
            b2dPlayerBodies.add(box2DMapObjectParser.getBodies().get("title2"));
            b2dSprites.add(new Box2DSprite(new Sprite((Texture) assetManager.get("images/title2.png"))));
        }

        if (box2DMapObjectParser.getBodies().containsKey("title3")) {
            b2dPlayerBodies.add(box2DMapObjectParser.getBodies().get("title3"));
            b2dSprites.add(new Box2DSprite(new Sprite((Texture) assetManager.get("images/title3.png"))));
        }

        if (box2DMapObjectParser.getBodies().containsKey("title4")) {
            b2dPlayerBodies.add(box2DMapObjectParser.getBodies().get("title4"));
            b2dSprites.add(new Box2DSprite(new Sprite((Texture) assetManager.get("images/title4.png"))));
        }

        if (box2DMapObjectParser.getBodies().containsKey("dialogue1")) {
            b2dPlayerBodies.add(box2DMapObjectParser.getBodies().get("dialogue1"));
            b2dSprites.add(new Box2DSprite(new Sprite((Texture) assetManager.get("images/dialogue/dialogue1.png"))));
        }

        if (box2DMapObjectParser.getBodies().containsKey("dialogue2")) {
            b2dPlayerBodies.add(box2DMapObjectParser.getBodies().get("dialogue2"));
            b2dSprites.add(new Box2DSprite(new Sprite((Texture) assetManager.get("images/dialogue/dialogue2.png"))));
        }

        if (box2DMapObjectParser.getBodies().containsKey("dialogue3")) {
            b2dPlayerBodies.add(box2DMapObjectParser.getBodies().get("dialogue3"));
            b2dSprites.add(new Box2DSprite(new Sprite((Texture) assetManager.get("images/dialogue/dialogue3.png"))));
        }

        if (box2DMapObjectParser.getBodies().containsKey("dialogue4")) {
            b2dPlayerBodies.add(box2DMapObjectParser.getBodies().get("dialogue4"));
            b2dSprites.add(new Box2DSprite(new Sprite((Texture) assetManager.get("images/dialogue/dialogue4.png"))));
        }

        if (box2DMapObjectParser.getBodies().containsKey("dialogue5")) {
            b2dPlayerBodies.add(box2DMapObjectParser.getBodies().get("dialogue5"));
            b2dSprites.add(new Box2DSprite(new Sprite((Texture) assetManager.get("images/dialogue/dialogue5.png"))));
        }

        if (box2DMapObjectParser.getBodies().containsKey("dialogue6")) {
            b2dPlayerBodies.add(box2DMapObjectParser.getBodies().get("dialogue6"));
            b2dSprites.add(new Box2DSprite(new Sprite((Texture) assetManager.get("images/dialogue/dialogue6.png"))));
        }

        if (box2DMapObjectParser.getBodies().containsKey("dialogue7")) {
            b2dPlayerBodies.add(box2DMapObjectParser.getBodies().get("dialogue7"));
            b2dSprites.add(new Box2DSprite(new Sprite((Texture) assetManager.get("images/dialogue/dialogue7.png"))));
        }

        if (box2DMapObjectParser.getBodies().containsKey("dialogue8")) {
            b2dPlayerBodies.add(box2DMapObjectParser.getBodies().get("dialogue8"));
            b2dSprites.add(new Box2DSprite(new Sprite((Texture) assetManager.get("images/dialogue/dialogue8.png"))));
        }

        if (box2DMapObjectParser.getBodies().containsKey("dialogue9")) {
            b2dPlayerBodies.add(box2DMapObjectParser.getBodies().get("dialogue9"));
            b2dSprites.add(new Box2DSprite(new Sprite((Texture) assetManager.get("images/dialogue/dialogue9.png"))));
        }

        if (box2DMapObjectParser.getBodies().containsKey("dialogue10")) {
            b2dPlayerBodies.add(box2DMapObjectParser.getBodies().get("dialogue10"));
            b2dSprites.add(new Box2DSprite(new Sprite((Texture) assetManager.get("images/dialogue/dialogue10.png"))));
        }

        if (box2DMapObjectParser.getBodies().containsKey("dialogue11")) {
            b2dPlayerBodies.add(box2DMapObjectParser.getBodies().get("dialogue11"));
            b2dSprites.add(new Box2DSprite(new Sprite((Texture) assetManager.get("images/dialogue/dialogue11.png"))));
        }

        Entity camera = artemisWorld.createEntity();
        camera.edit().add(new Box2dCameraComponent(b2dCam));
        camera.edit().add(new CameraInputComponent(playerBody));

        handler = new RayHandler(box2dWorld);
        handler.setAmbientLight(0.8f);
        handler.setBlur(true);
        handler.setCulling(true);
        handler.setCombinedMatrix(b2dCam);

        new PointLight(handler, 400, new Color(1, 1, 1, 0.5f), 50, 0, 0).attachToBody(box2DMapObjectParser.getBodies().get("light1"), 0, 0);
        new PointLight(handler, 400, new Color(1, 1, 1, 0.5f), 50, 0, 0).attachToBody(box2DMapObjectParser.getBodies().get("light2"), 0, 0);
        new PointLight(handler, 400, new Color(1, 1, 1, 0.5f), 50, 0, 0).attachToBody(box2DMapObjectParser.getBodies().get("light3"), 0, 0);

        if (level != 5) {
            sun1 = new DirectionalLight(handler, 400, new Color(1, 0, 0, 0.6f), -75);
            sun2 = new DirectionalLight(handler, 400, new Color(1, 0, 0, 0.6f), 105);
        }
        PointLight.setGlobalContactFilter((short) 19, (short) 0, (short) 19);

        ui = new Stage();
        colourWheel = new Image((Texture) assetManager.get("images/colourwheelr.png"));
        colourWheel.setSize(200, 200);
        colourWheel.setPosition(Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 220);
        ui.addActor(colourWheel);

        background = new Stage();
        background.getViewport().setWorldSize(1280, 720);
        backgroundImage = new Image((Texture) assetManager.get("images/backgroundr.png"));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.addActor(backgroundImage);
    }

    @Override
    public void render(float delta) {
        tickCounter += Gdx.graphics.getDeltaTime();
        while (tickCounter >= TICK_STEP) {
            tickCounter -= TICK_STEP;
            update(TICK_STEP);
        }

        render();
        frameCounter++;

        if (secondCounter > 1) {
            System.out.println("Frames Per Second: " + frameCounter);
            secondCounter--;
            frameCounter = 0;
        }
        secondCounter += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void resize(int width, int height) {
        spriteBatch.setProjectionMatrix(b2dCam.combined);
        handler.setCombinedMatrix(b2dCam);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        if (level != 5) {
            sun1.remove();
            sun2.remove();
        }
    }

    private void update(float dt) {
        box2dWorld.step(dt, 6, 2);
        artemisWorld.setDelta(dt);
        artemisWorld.process();
        handler.update();
        ui.act(dt);

        levelCooldown--;

        if (reset) {
            reset = false;
            init();
        }
    }

    private void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        background.draw();

        if (currentWorld == 1) {
            tmr1.setView(b2dCam);
            tmr1.render();
        } else if (currentWorld == 2) {
            tmr2.setView(b2dCam);
            tmr2.render();
        } else if (currentWorld == 3) {
            tmr3.setView(b2dCam);
            tmr3.render();
        }

        spriteBatch.setProjectionMatrix(b2dCam.combined);
        spriteBatch.begin();
        for (int x = 0; x < b2dPlayerBodies.size(); x++) {
            b2dSprites.get(x).draw(spriteBatch, b2dPlayerBodies.get(x));
        }
        spriteBatch.end();

        handler.setCombinedMatrix(b2dCam);
        handler.render();
        ui.draw();
    }

    public void handleChangeWorldEvent(ChangeWorldEvent e) {
        if (e.getWorld() == 1) {
            colourWheel.setDrawable(new SpriteDrawable(new Sprite((Texture) assetManager.get("images/colourwheelr.png"))));
            backgroundImage.setDrawable(new SpriteDrawable(new Sprite((Texture) assetManager.get("images/backgroundr.png"))));
            currentWorld = 1;
            if (level != 5) {
                sun1.remove();
                sun2.remove();
                sun1 = new DirectionalLight(handler, 400, new Color(1, 0, 0, 0.6f), -75);
                sun2 = new DirectionalLight(handler, 400, new Color(1, 0, 0, 0.6f), 105);
            }
            PointLight.setGlobalContactFilter((short) 19, (short) 0, (short) 19);
        } else if (e.getWorld() == 2) {
            colourWheel.setDrawable(new SpriteDrawable(new Sprite((Texture) assetManager.get("images/colourwheely.png"))));
            backgroundImage.setDrawable(new SpriteDrawable(new Sprite((Texture) assetManager.get("images/backgroundy.png"))));
            currentWorld = 2;
            if (level != 5) {
                sun1.remove();
                sun2.remove();
                sun1 = new DirectionalLight(handler, 400, new Color(1, 1, 1, 0.4f), 105);
                sun2 = new DirectionalLight(handler, 400, new Color(1, 1, 1, 0.4f), -75);
            }
            PointLight.setGlobalContactFilter((short) 21, (short) 0, (short) 21);

        } else if (e.getWorld() == 3) {
            colourWheel.setDrawable(new SpriteDrawable(new Sprite((Texture) assetManager.get("images/colourwheelb.png"))));
            backgroundImage.setDrawable(new SpriteDrawable(new Sprite((Texture) assetManager.get("images/backgroundb.png"))));
            currentWorld = 3;
            if (level != 5) {
                sun1.remove();
                sun2.remove();
                sun1 = new DirectionalLight(handler, 400, new Color(0, 0, 1, 0.6f), -75);
                sun2 = new DirectionalLight(handler, 400, new Color(0, 0, 1, 0.6f), 105);
            }
            PointLight.setGlobalContactFilter((short) 25, (short) 0, (short) 25);
        }

    }

    public void handlePlayerDiedEvent(PlayerDiedEvent e) {
        reset = true;
    }

    public void handleNextLevelEvent(NextLevelEvent e) {
        if (levelCooldown < 0) {
            level++;
            levelCooldown = 60;
        }
        reset = true;
    }
}
