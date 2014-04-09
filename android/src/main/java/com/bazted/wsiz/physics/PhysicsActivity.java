package com.bazted.wsiz.physics;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.androidannotations.annotations.EActivity;

@EActivity
public class PhysicsActivity extends SimpleBaseGameActivity {


    private static final int CAMERA_WIDTH = 480;
    private static final int CAMERA_HEIGHT = 800;

    private ITextureRegion textureRegion;

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    public void onCreateResources() {
        BuildableBitmapTextureAtlas bitmapTextureAtlas =
                new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), 1024, 1024,
                        TextureOptions.BILINEAR);

        SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        this.textureRegion = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, this, "nexus_optimized.svg", 480, 800);
        try {
            bitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            bitmapTextureAtlas.load();
        } catch (final ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Debug.e(e);
        }

    }

    @Override
    public Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        final Scene scene = new Scene();
        scene.setBackground(new Background(Color.BLUE));

        scene.attachChild(new Sprite(0, 0, 480, 800, this.textureRegion, getVertexBufferObjectManager()));
        return scene;
    }
}
