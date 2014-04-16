package com.bazted.wsiz.physics.oersted;


import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * ToggleButtonSprite class that creates the ToogelButton which will
 * change the state and sprite tile when clicked by the user.
 *
 * @author Yogesh Meshram
 */
public class SwitchButton extends ButtonSprite {


    /**
     * Enum to store different ON and OFF state of the button
     */
    public static enum ToggleState {
        OFF(0), ON(1);
        private final int mState;

        private ToggleState(final int pSate) {
            mState = pSate;
        }

        public int get() {
            return mState;
        }

    }


    private boolean mToogleState;

    private ToggleState mState;

    public static interface OnStateChangeListener {

        public void onOffClick(SwitchButton pButtonSprite,
                               float pTouchAreaLocalX, float pTouchAreaLocalY);


        public void onOnClick(SwitchButton pButtonSprite,
                              float pTouchAreaLocalX, float pTouchAreaLocalY);


    }

    private OnStateChangeListener mOnStateChangeListener;


    /**
     * Constructor to create ToggleButtonSprite
     *
     * @param pX
     * @param pY
     * @param pNormalTextureRegion
     * @param pVertexBufferObjectManager
     */
    public SwitchButton(final float pX, final float pY, final TiledTextureRegion pNormalTextureRegion,
                        final VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pNormalTextureRegion, pVertexBufferObjectManager);

        mState = ToggleState.OFF;
        setCurrentTileIndex(mState.get());
    }

    /**
     * Method to setState of the ToggleButton
     *
     * @param pState
     */
    public void setState(final ToggleState pState) {
        mState = pState;
        setCurrentTileIndex(mState.get());
        mToogleState = ToggleState.ON.equals(pState);
    }


    /**
     * Method to change the state of the Toggle button
     *
     * @param pState
     */
    private void changeState(final ToggleState pState) {
        mState = pState;
    }

    @Override
    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX,
                                 final float pTouchAreaLocalY) {
        if (pSceneTouchEvent.isActionUp()) {

            if (mOnStateChangeListener != null) {

                if (mToogleState) {
                    setCurrentTileIndex(ToggleState.OFF.get());
                    changeState(ToggleState.ON);
                    mOnStateChangeListener.onOnClick(this,
                            pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
                } else {
                    setCurrentTileIndex(ToggleState.ON.get());
                    changeState(ToggleState.OFF);
                    mOnStateChangeListener.onOffClick(this,
                            pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
                }
                mToogleState = !mToogleState;
            }
        }

        return true;
    }


    public void setOnToggleClickListener(final OnStateChangeListener onStateChangeListener) {
        this.mOnStateChangeListener = onStateChangeListener;
    }


    public void setEnabled(final boolean pIsON) {
        mToogleState = false;
        mState = pIsON ? ToggleState.ON : ToggleState.OFF;
        setCurrentTileIndex(mState.get());
    }

}
