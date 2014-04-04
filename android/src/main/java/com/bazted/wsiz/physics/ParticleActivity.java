package com.bazted.wsiz.physics;

import android.opengl.GLES20;
import android.widget.Toast;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.GravityParticleInitializer;
import org.andengine.entity.particle.modifier.OffCameraExpireParticleModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.androidannotations.annotations.EActivity;

@EActivity
public class ParticleActivity extends SimpleBaseGameActivity {
    // ===========================================================
    // Constants
    // ===========================================================

    private static final int CAMERA_WIDTH = 480;
    private static final int CAMERA_HEIGHT = 800;

    private Camera mCamera;
    private BitmapTextureAtlas mBitmapTextureAtlas;
    private ITextureRegion mParticleTextureRegion;


    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public EngineOptions onCreateEngineOptions() {
        Toast.makeText(this, "Touch the screen to move the particlesystem.", Toast.LENGTH_LONG).show();

        this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
    }

    @Override
    protected void onCreateResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mParticleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "particle_point.png", 0, 0);

        this.mBitmapTextureAtlas.load();
    }

    @Override
    protected Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        final Scene scene = new Scene();

        final PointParticleEmitter particleEmitter = new
                PointParticleEmitter(CAMERA_WIDTH * 0.5f, CAMERA_HEIGHT * 0.5f);

//        final SpriteParticleSystem particleSystem = new SpriteParticleSystem(particleEmitter, 60, 60, 360,
//                this.mParticleTextureRegion, this.getVertexBufferObjectManager());
        final SpriteParticleSystem particleSystem = new SpriteParticleSystem(
                particleEmitter, 60, 60, 360, this.mParticleTextureRegion, this.getVertexBufferObjectManager()
        );

        scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
            @Override
            public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
                particleEmitter.setCenter(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
                return true;
            }
        });

//        particleSystem.addParticleInitializer(new AccelerationParticleInitializer<Sprite>(0f, 5f));
//        particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
        particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
//        particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 0, 0));
//        particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(6));
        particleSystem.addParticleInitializer(new GravityParticleInitializer<Sprite>());

//        particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
//        particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(0));
//
//        particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 1.0f, 2.0f));
//        particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0, 3, 1, 1, 0, 0.5f, 0, 0));
//        particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(4, 6, 1, 1, 0.5f, 1, 0, 1));
//        particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 1, 0, 1));
//        particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(5, 6, 1, 0));
        particleSystem.addParticleModifier(new OffCameraExpireParticleModifier<Sprite>(mCamera));

        scene.attachChild(particleSystem);

        return scene;
    }
    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}


