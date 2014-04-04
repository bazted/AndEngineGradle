package com.bazted.wsiz.physics;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.DrawMode;
import org.andengine.entity.primitive.Mesh;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import org.androidannotations.annotations.EActivity;

@EActivity
public class MeshActivity extends SimpleBaseGameActivity {
    // ===========================================================
    // Constants
    // ===========================================================

    private static final int CAMERA_WIDTH = 480;
    private static final int CAMERA_HEIGHT = 800;

    private static final int UNUSED = 0;

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
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.PORTRAIT_SENSOR,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    public void onCreateResources() {

    }

    @Override
    public Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        final Scene scene = new Scene();

        scene.setBackground(new Background(Color.BLUE));

        final Entity rectangleGroup = new Entity(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2);


        float BASE_HEIGHT = 300;
        float BASE_WIDTH = 200;

        final float centerX = 0;
        final float centerY = 0;

        final Rectangle rectangle = new Rectangle(-25, -25, 50, 50,
                getVertexBufferObjectManager(), DrawType.STATIC);
        rectangle.setColor(Color.YELLOW);
        rectangleGroup.attachChild(rectangle);

        final float[] mMeshVertices = new float[]{
                0, -200, Color.WHITE_ABGR_PACKED_FLOAT,
                100, -100, Color.RED_ABGR_PACKED_FLOAT,
                0, 0, Color.RED_ABGR_PACKED_FLOAT,
                -50, -100, Color.RED_ABGR_PACKED_FLOAT,

        };
        final int mMeshVertexCount = 4;


        Mesh mMesh = new Mesh(centerX, centerY,
                mMeshVertices, mMeshVertexCount, DrawMode.LINE_LOOP,
                this.getVertexBufferObjectManager(), DrawType.STATIC);
        rectangleGroup.attachChild(mMesh);

        scene.attachChild(rectangleGroup);

        return scene;
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}


