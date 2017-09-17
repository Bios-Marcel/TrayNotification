package com.github.plushaze.traynotification.animations;

import com.github.plushaze.traynotification.models.TrayPopup;

import javafx.animation.Timeline;

/**
 * Animation that returns an empty timeline, for both showing and dismissing.
 *
 * @author Marcel
 * @since 07.06.2017
 */
final class NoneAnimation extends AbstractAnimation
{
	NoneAnimation(final TrayPopup stage)
	{
		super(stage);
	}

	@Override
	protected Timeline setupShowAnimation()
	{
		// Left empty, since there should be no animations
		final Timeline timeline = new Timeline();

		timeline.setOnFinished(e -> trayIsShowing = true);

		return timeline;
	}

	@Override
	protected Timeline setupDismissAnimation()
	{
		// Left empty, since there should be no animations
		final Timeline timeline = new Timeline();

		timeline.setOnFinished(e ->
		{
			trayIsShowing = false;
			stage.hide();
			stage.setLocation(stage.getBottomRight());
		});

		return timeline;
	}

}
