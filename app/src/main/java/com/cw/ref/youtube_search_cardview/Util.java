/*
 * Copyright (C) 2018 CW Chiu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cw.ref.youtube_search_cardview;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

class Util{
	static boolean isLandscapeOrientation(Activity act)
	{
		int currentOrientation = act.getResources().getConfiguration().orientation;

		if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE)
			return true;
		else
			return false;
	}

	// set full screen
	static void setFullScreen(Activity act)
	{
//		System.out.println("Util / _setFullScreen");
		Window win = act.getWindow();

		if (Build.VERSION.SDK_INT < 16)
		{
			win.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		else
		{
			//ref: https://stackoverflow.com/questions/28983621/detect-soft-navigation-bar-availability-in-android-device-progmatically
			Resources res = act.getResources();
			int id = res.getIdentifier("config_showNavigationBar", "bool", "android");
			boolean hasNavBar = ( id > 0 && res.getBoolean(id));

//			System.out.println("Util / _setFullScreen / hasNavBar = " + hasNavBar);

			// flags
			int uiOptions = //View.SYSTEM_UI_FLAG_LAYOUT_STABLE | //??? why this flag will add bottom offset
					View.SYSTEM_UI_FLAG_FULLSCREEN |
							View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

			if (Build.VERSION.SDK_INT >= 19)
				uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

			//has navigation bar
			if(hasNavBar)
			{
				uiOptions = uiOptions
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
			}

			View decorView = act.getWindow().getDecorView();
			decorView.setSystemUiVisibility(uiOptions);
		}
	}
}
