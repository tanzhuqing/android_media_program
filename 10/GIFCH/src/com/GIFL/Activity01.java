package com.GIFL;

import android.app.Activity;
import android.os.Bundle;

public class Activity01 extends Activity
{
	private GameView	mGameView	= null;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mGameView = new GameView(this);

		setContentView(mGameView);
	}
}
