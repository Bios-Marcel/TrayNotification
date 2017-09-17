package com.github.plushaze.traynotification.animations;

import java.util.function.Function;

import com.github.plushaze.traynotification.models.TrayPopup;

public enum Animations
{
	NONE(NoneAnimation::new),
	SLIDE(SlideAnimation::new),
	FADE(FadeAnimation::new),
	POPUP(PopupAnimation::new);

	private final Function<TrayPopup, Animation> newInstance;

	private Animations(final Function<TrayPopup, Animation> newInstance)
	{
		this.newInstance = newInstance;
	}

	public Animation newInstance(final TrayPopup stage)
	{
		return newInstance.apply(stage);
	}

}
