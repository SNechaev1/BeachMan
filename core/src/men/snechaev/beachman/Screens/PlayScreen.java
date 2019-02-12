package men.snechaev.beachman.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

import men.snechaev.beachman.BeachMan;
import men.snechaev.beachman.GameGestureDetector;

public class PlayScreen implements Screen {

    private final BeachMan game;
    private int mManState = 0;
    private int mManBodyDelay = 0;
    private int manY;
    private int score = 0;
    private int mHighScore;
    private int mGameState = 1;
    private int itemCount;
    private int bombCount;
    private final int mDifficulty;
    private float mFallingSpeed = 0;
    private final Random random = new Random();
    private static Music mMusic;
    private static Music mMusicGameOver;
    private final boolean mScreenOrientationLandscape;
    private final String mSoundtrackList;

    // Textures
    private Texture background;
    private Texture game_over;
    private Texture tap_to_start;
    private Texture item;
    private Texture bomb;
    private Texture mPauseButton;

    private Texture[] man;
    private Texture[] mAllItems;

    // Arrays
    private final ArrayList<Integer> itemXs = new ArrayList<>();
    private final ArrayList<Integer> itemYs = new ArrayList<>();
    private final ArrayList<Rectangle> itemRectangles = new ArrayList<>();

    private final ArrayList<Integer> bombXs = new ArrayList<>();
    private final ArrayList<Integer> bombYs = new ArrayList<>();
    private final ArrayList<Rectangle> bombRectangles = new ArrayList<>();


//    GestureDetector mGesture;

    public PlayScreen(BeachMan game) {
        this.game = game;

        mHighScore = game.mPrefs.getInteger("highScore", 0);
        mScreenOrientationLandscape = game.mPrefs.getBoolean("screenOrientationLandscape", true);
        mSoundtrackList = game.mPrefs.getString("soundtrackList","2");
        String difficultyList = game.mPrefs.getString("difficultyList", "2");
        game.mSoundOn = game.mPrefs.getBoolean("soundOn", false);
        game.mGameSpeed = game.mPrefs.getInteger("gameSpeed", 4);

        // mDifficulty means after how many cycles(how often) bomb created
        mDifficulty = 250 - Integer.decode(difficultyList) * 50 ;
        //Graphic fit to screen size
        if (Gdx.graphics.getHeight() <= 480) {
            createTexturesMdpi();
        } else if(Gdx.graphics.getHeight() <= 720) {
            createTexturesHdpi();
        } else {
            createTextures();
        }

        if (game.mSoundOn) {
            setSoundtrack();
            mMusicGameOver = Gdx.audio.newMusic(Gdx.files.internal("music/game_over.mp3"));
        }

        item = mAllItems[random.nextInt(9)];
        manY = Gdx.graphics.getHeight() / 2;

//        Input.Orientation nativeOrientation = Gdx.input.getNativeOrientation();
//        Gdx.app.log("nativeOrientation", String.valueOf(nativeOrientation));

        Gdx.input.setInputProcessor(new GameGestureDetector(new GameGestureDetector.DirectionListener() {

            @Override
            public void onUp() {
                mFallingSpeed = -20;
//                Gdx.app.log("getDeltaX", String.valueOf("OnUp"));
            }

            @Override
            public void onRight() { }

            @Override
            public void onLeft() { }

            @Override
            public void onDown() {
                mFallingSpeed = +30;
            }

            @Override
            public void onLongPressed() {
                mFallingSpeed = -20;
            }
        }));

    }

    private void createTextures() {
        if(mScreenOrientationLandscape){
            if (Gdx.graphics.getWidth() / Gdx.graphics.getHeight() > 1.5f) {
                background = new Texture("background.jpg");
            } else {
                background = new Texture("backgroundTablet.jpg");
            }
        } else {
            if (Gdx.graphics.getHeight() / Gdx.graphics.getWidth() > 1.5f) {
                background = new Texture("backgroundPortrait.jpg");
            } else {
                background = new Texture("backgroundTabletPortrait.jpg");
            }
        }
        tap_to_start = new Texture("tap_to_start.png");
        bomb = new Texture("item/bomb.png");
        game_over = new Texture("game_over.png");
        mPauseButton = new Texture("pause_button.png");

        man = new Texture[13];
        man[0] = new Texture("gentleman/run/0.png");
        man[1] = new Texture("gentleman/run/1.png");
        man[2] = new Texture("gentleman/run/2.png");
        man[3] = new Texture("gentleman/run/3.png");
        man[4] = new Texture("gentleman/run/4.png");
        man[5] = new Texture("gentleman/run/5.png");

        man[6] = new Texture("gentleman/down/0.png");
        man[7] = new Texture("gentleman/down/1.png");
        man[8] = new Texture("gentleman/down/2.png");
        man[9] = new Texture("gentleman/down/3.png");
        man[10] = new Texture("gentleman/down/4.png");
        man[11] = new Texture("gentleman/down/5.png");

        mAllItems = new Texture[9];
        mAllItems[0] = new Texture("item/coin.png");
        mAllItems[1] = new Texture("item/camera.png");
        mAllItems[2] = new Texture("item/coconut.png");
        mAllItems[3] = new Texture("item/coffee.png");
        mAllItems[4] = new Texture("item/headphones.png");
        mAllItems[5] = new Texture("item/ice_cream.png");
        mAllItems[6] = new Texture("item/juice.png");
        mAllItems[7] = new Texture("item/rum.png");
        mAllItems[8] = new Texture("item/treasure.png");
    }

    private void createTexturesHdpi() {
        if(mScreenOrientationLandscape){
            background = new Texture("background.jpg");
        } else {
            background = new Texture("backgroundPortrait.jpg");
        }
        tap_to_start = new Texture("tap_to_start.png");
        bomb = new Texture("item_hdpi/bomb.png");
        game_over = new Texture("game_over.png");
        mPauseButton = new Texture("pause_button.png");

        man = new Texture[13];
        man[0] = new Texture("gentleman_hdpi/run/0.png");
        man[1] = new Texture("gentleman_hdpi/run/1.png");
        man[2] = new Texture("gentleman_hdpi/run/2.png");
        man[3] = new Texture("gentleman_hdpi/run/3.png");
        man[4] = new Texture("gentleman_hdpi/run/4.png");
        man[5] = new Texture("gentleman_hdpi/run/5.png");

        man[6] = new Texture("gentleman_hdpi/down/0.png");
        man[7] = new Texture("gentleman_hdpi/down/1.png");
        man[8] = new Texture("gentleman_hdpi/down/2.png");
        man[9] = new Texture("gentleman_hdpi/down/3.png");
        man[10] = new Texture("gentleman_hdpi/down/4.png");
        man[11] = new Texture("gentleman_hdpi/down/5.png");

        mAllItems = new Texture[9];
        mAllItems[0] = new Texture("item_hdpi/coin.png");
        mAllItems[1] = new Texture("item_hdpi/camera.png");
        mAllItems[2] = new Texture("item_hdpi/coconut.png");
        mAllItems[3] = new Texture("item_hdpi/coffee.png");
        mAllItems[4] = new Texture("item_hdpi/headphones.png");
        mAllItems[5] = new Texture("item_hdpi/ice_cream.png");
        mAllItems[6] = new Texture("item_hdpi/juice.png");
        mAllItems[7] = new Texture("item_hdpi/rum.png");
        mAllItems[8] = new Texture("item_hdpi/treasure.png");
    }

    private void createTexturesMdpi() {
        if(mScreenOrientationLandscape){
            background = new Texture("background.jpg");
        } else {
            background = new Texture("backgroundPortrait.jpg");
        }
        tap_to_start = new Texture("tap_to_start.png");
        bomb = new Texture("item_mdpi/bomb.png");
        game_over = new Texture("game_over.png");
        mPauseButton = new Texture("pause_button.png");

        man = new Texture[13];
        man[0] = new Texture("gentleman_mdpi/run/0.png");
        man[1] = new Texture("gentleman_mdpi/run/1.png");
        man[2] = new Texture("gentleman_mdpi/run/2.png");
        man[3] = new Texture("gentleman_mdpi/run/3.png");
        man[4] = new Texture("gentleman_mdpi/run/4.png");
        man[5] = new Texture("gentleman_mdpi/run/5.png");

        man[6] = new Texture("gentleman_mdpi/down/0.png");
        man[7] = new Texture("gentleman_mdpi/down/1.png");
        man[8] = new Texture("gentleman_mdpi/down/2.png");
        man[9] = new Texture("gentleman_mdpi/down/3.png");
        man[10] = new Texture("gentleman_mdpi/down/4.png");
        man[11] = new Texture("gentleman_mdpi/down/5.png");

        mAllItems = new Texture[9];
        mAllItems[0] = new Texture("item_mdpi/coin.png");
        mAllItems[1] = new Texture("item_mdpi/camera.png");
        mAllItems[2] = new Texture("item_mdpi/coconut.png");
        mAllItems[3] = new Texture("item_mdpi/coffee.png");
        mAllItems[4] = new Texture("item_mdpi/headphones.png");
        mAllItems[5] = new Texture("item_mdpi/ice_cream.png");
        mAllItems[6] = new Texture("item_mdpi/juice.png");
        mAllItems[7] = new Texture("item_mdpi/rum.png");
        mAllItems[8] = new Texture("item_mdpi/treasure.png");
    }


    private void setRandomPosition(ArrayList<Integer> X, ArrayList<Integer> Y) {
        float height = random.nextFloat() * Gdx.graphics.getHeight();
        Y.add((int) height);
        X.add(Gdx.graphics.getWidth());
    }

    private void setManMotion(int manBodyDelay, int manState) {
        if (mManBodyDelay < manBodyDelay) {
            mManBodyDelay++;
        } else {
            mManBodyDelay = 0;
            if (mManState < manState) {
                mManState++;
            } else {
                mManState = 0;
                if (manState == 11) {
                    // if manState 11, what means man down, go to game_over state
                    mGameState = 3;
                }
            }
        }

        float gravity = 0.2f;
        mFallingSpeed += gravity;
        manY -= mFallingSpeed;
        // not to fall below or above screen
        if (manY <= 0) {
            manY = 0;
        }
        if (manY >= Gdx.graphics.getHeight() - man[mManState].getHeight() + 64) {
            manY = Gdx.graphics.getHeight() - man[mManState].getHeight() + 64;
        }

//        game.batch.draw(man[mManState], Gdx.graphics.getWidth() / 2.0f - man[mManState].getWidth() / 2.0f, manY);
        game.batch.draw(man[mManState], Gdx.graphics.getWidth() / 3.0f, manY);

    }

    private void resetGame() {
        mGameState = 1;
        mManState = 0;
        manY = Gdx.graphics.getHeight() / 2;
        score = 0;
        mFallingSpeed = 0;
        itemXs.clear();
        itemYs.clear();
        itemRectangles.clear();
        itemCount = 0;
        bombXs.clear();
        bombYs.clear();
        bombRectangles.clear();
        bombCount = 0;
        // random.nextInt - 0 (inclusive) and the specified value (exclusive)
        item = mAllItems[random.nextInt(9)];
    }

    private void setSoundtrack(){
        switch (mSoundtrackList) {
            case "1":
                mMusic = Gdx.audio.newMusic(Gdx.files.internal("music/techno1.mp3"));
                break;
            case "2":
                mMusic = Gdx.audio.newMusic(Gdx.files.internal("music/techno2.mp3"));
                break;
            case "3":
                mMusic = Gdx.audio.newMusic(Gdx.files.internal("music/dramatic.mp3"));
                break;
            case "4":
                mMusic = Gdx.audio.newMusic(Gdx.files.internal("music/ukulele.mp3"));
                break;
        }
        mMusic.setLooping(true);
    }

    private void pauseGame() {
        if (!(mMusicGameOver == null)) {
            mMusicGameOver.stop();
        }
        if (!(mMusic == null)) {
            mMusic.stop();
        }
        mGameState = 0;
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        switch (mGameState) {
//             waiting to start
            case 0:
                game.batch.draw(tap_to_start, Gdx.graphics.getWidth() / 2.0f - tap_to_start.getWidth() / 2.0f,
                        Gdx.graphics.getHeight() / 2.0f - tap_to_start.getHeight() / 2.0f);

                if (Gdx.input.justTouched() && Gdx.input.getX() > 148 && Gdx.input.getY() > 148) {
                    mGameState = 1;
                }
                break;
            // game is on
            case 1:
                if (!(mMusic == null) && game.mSoundOn) {
                    mMusic.play();
                }
                game.batch.draw(mPauseButton, 20, Gdx.graphics.getHeight() - mPauseButton.getHeight() - 20);
                // if pause button pressed pause game
                if (Gdx.input.justTouched() && Gdx.input.getX() < 148 && Gdx.input.getY() < 148) {
                    pauseGame();
                }

                game.font.getData().setScale(5);
                game.font.draw(game.batch, String.valueOf(score), 50, 150);
                // create bomb after every (mDifficulty) cycles
                if (bombCount < mDifficulty) {
                    bombCount++;
                } else {
                    bombCount = 0;
                    setRandomPosition(bombXs, bombYs);
                }
                bombRectangles.clear();

                for (int i = 0; i < bombXs.size(); i++) {
                    game.batch.draw(bomb, bombXs.get(i), bombYs.get(i));
                    bombXs.set(i, bombXs.get(i) - game.mGameSpeed * 2);
                    bombRectangles.add(new Rectangle(bombXs.get(i), bombYs.get(i), bomb.getWidth(), bomb.getHeight()));
                }

                // create item after every 100 cycles
                if (itemCount < 100) {
                    itemCount++;
                } else {
                    itemCount = 0;
                    setRandomPosition(itemXs, itemYs);
                }
                itemRectangles.clear();

                for (int i = 0; i < itemXs.size(); i++) {
                    game.batch.draw(item, itemXs.get(i), itemYs.get(i));
                    itemXs.set(i, itemXs.get(i) - game.mGameSpeed);
                    itemRectangles.add(new Rectangle(itemXs.get(i), itemYs.get(i), item.getWidth(),
                            item.getHeight()));
                }

                // tapping causing the jump
                if (Gdx.input.justTouched()) {
                    mFallingSpeed = -10;
                }

                setManMotion(8, 5);
                Rectangle manRectangle = new Rectangle(Gdx.graphics.getWidth() / 3.0f,
                        manY, man[mManState].getWidth(), man[mManState].getHeight());

                // if collision with item add score
                for (int i = 0; i < itemRectangles.size(); i++) {
                    if (Intersector.overlaps(manRectangle, itemRectangles.get(i))) {
                        score++;
                        itemRectangles.remove(i);
                        itemXs.remove(i);
                        itemYs.remove(i);
                        break;
                    }
                }

                // if collision with bomb set game state to man down
                for (int i = 0; i < bombRectangles.size(); i++) {
                    if (Intersector.overlaps(manRectangle, bombRectangles.get(i))) {
                        mGameState = 2;
                    }
                }
                break;

            // man down
            case 2:
                if (!(mMusic == null)) {
                    mMusic.stop();
                }
                if (game.mSoundOn && !(mMusicGameOver == null)) {
                    mMusicGameOver.play();
                }

                if(mHighScore < score) {
                    game.mPrefs.putInteger("highScore", score);
                    mHighScore = score;
                }
                game.mPrefs.flush();

                // state from 7 to 11 is falling
                setManMotion(15, 11);
                break;

            // game over
            case 3:
//                if (!(mMusicGameOver == null)) {
//                    mMusicGameOver.stop();
//                }

                // draw score
                game.font.getData().setScale(2);
                game.font.draw(game.batch, String.valueOf("SCORE: " + score), 50, 200);
                game.font.draw(game.batch, String.valueOf("BEST SCORE: " + mHighScore), 50, 100);
                // draw "just tap to start" and "game over"
                game.batch.draw(tap_to_start, Gdx.graphics.getWidth() / 2.0f - tap_to_start.getWidth() / 2.0f,
                        Gdx.graphics.getHeight() / 2.0f - tap_to_start.getHeight() / 2.0f);
                game.batch.draw(game_over, Gdx.graphics.getWidth() / 2.0f - game_over.getWidth() / 2.0f,
                        Gdx.graphics.getHeight() / 2.0f + tap_to_start.getHeight());

                // waiting for touch on screen (getX and getY - coordinates of last touch)
                if (Gdx.input.justTouched() && Gdx.input.getX() > 148 && Gdx.input.getY() > 148) {
                    resetGame();
                }
                break;
        }

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() {
        pauseGame();
    }

    @Override
    public void resume() {
        mGameState = 1;
    }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        mMusic.dispose();
        mMusicGameOver.dispose();
    }
}
