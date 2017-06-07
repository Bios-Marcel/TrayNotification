package com.github.plushaze.traynotification.animations;

import com.github.plushaze.traynotification.models.CustomStage;

import javafx.animation.Timeline;

/**
 * Animation that returns an empty timeline, for both showing and dismissing.
 *
 * @author Marcel
 * @since 07.06.2017
 */
final class NoneAnimation extends AbstractAnimation
{
	NoneAnimation(final CustomStage stage)
	{
		super(stage);
	}

	@Override
	protected Timeline setupShowAnimation()
	{
		final Timeline tl = new Timeline();

		tl.setOnFinished(e -> trayIsShowing = true);

		return tl;
	}

	@Override
	protected Timeline setupDismissAnimation()
	{
		final Timeline tl = new Timeline();

		tl.setOnFinished(e ->
		{
			trayIsShowing = false;
			stage.close();
			stage.setLocation(stage.getBottomRight());
		});

		return tl;
	}

}
