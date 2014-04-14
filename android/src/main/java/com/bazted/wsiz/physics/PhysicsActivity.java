package com.bazted.wsiz.physics;

import android.widget.Toast;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

@EActivity
public class PhysicsActivity extends SimpleBaseGameActivity {


    private static final int CAMERA_WIDTH = 480;
    private static final int CAMERA_HEIGHT = 800;

    private ITextureRegion koloBg;
    private ITextureRegion arrowSvg;
    private ITextureRegion switcher;
    private ITextureRegion compass;
    private ITextureRegion turn_on_btn;

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
        this.koloBg = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, this, "nexus_optimized.svg", 480, 800);
        this.arrowSvg = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, this, "compass_arrow.svg", 155, 155);
        this.switcher = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, this, "switcher.svg", 74, 74);
        this.compass = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, this, "compass.svg", 155, 155);
        this.turn_on_btn = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, this, "turn_on_btn.svg", 114, 38);

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
        scene.setBackground(new Background(Color.WHITE));

        final VertexBufferObjectManager vertexBufferObjectManager = getVertexBufferObjectManager();
        final Sprite backEntity = new Sprite(0, 0, 480, 800, this.koloBg, vertexBufferObjectManager);

        final Sprite compassEntity = new Sprite(305, 215, 155, 155, this.compass, vertexBufferObjectManager);
        backEntity.attachChild(compassEntity);

        final Sprite compassArrow = new Sprite(0, 0, 155, 155, this.arrowSvg, vertexBufferObjectManager);
        compassEntity.attachChild(compassArrow);

        final Sprite switcher = new Sprite(49, 577, 74, 74, this.switcher, vertexBufferObjectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                showToast("Switcher clicked");
                return true;
            }
        };
        backEntity.attachChild(switcher);

        final Sprite turn_on_btn = new Sprite(218, 742, 114, 38, this.turn_on_btn, vertexBufferObjectManager) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                showToast("turn_on_btn clicked");

                return true;
            }
        };
        backEntity.attachChild(turn_on_btn);
        scene.attachChild(backEntity);
        return scene;
    }


    @UiThread
    void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
