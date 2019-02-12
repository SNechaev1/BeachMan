package men.snechaev.beachman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import men.snechaev.beachman.Screens.PlayScreen;

public class BeachMan extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public Preferences mPrefs;
	public int mGameSpeed = 4;
	public boolean mSoundOn;



	@Override
	public void create() {
		batch = new SpriteBatch();
		Texture mFontTexture = new Texture("my_font.png");
		mFontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		font = new BitmapFont(Gdx.files.internal("my_font.fnt"), new TextureRegion(mFontTexture), false);
		font.setColor(Color.BLACK);

		mPrefs = Gdx.app.getPreferences("men.snechaev.beachman_preferences");

		setScreen(new PlayScreen(this));
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
