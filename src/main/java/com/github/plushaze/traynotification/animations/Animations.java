package com.github.plushaze.traynotification.animations;

import java.util.function.Function;

import com.github.plushaze.traynotification.models.CustomStage;

public enum Animations
{

	SLIDE(SlideAnimation::new),
	FADE(FadeAnimation::new),
	POPUP(PopupAnimation::new);

	private final Function<CustomStage, Animation> newInstance;

	Animations(final Function<CustomStage, Animation> newInstance)
	{
		this.newInstance = newInstance;
	}

	public Animation newInstance(final CustomStage stage)
	{
		return newInstance.apply(stage);
	}

}
