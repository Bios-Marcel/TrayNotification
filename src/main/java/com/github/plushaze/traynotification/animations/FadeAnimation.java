package com.github.plushaze.traynotification.animations;

import com.github.plushaze.traynotification.models.CustomStage;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

final class FadeAnimation extends AbstractAnimation
{

	FadeAnimation(final CustomStage stage)
	{
		super(stage);
	}

	@Override
	protected Timeline setupShowAnimation()
	{
		final Timeline timeline = new Timeline();

		// Sets opacity to 0.0 instantly which is pretty much invisible
		final KeyValue keyValueOpacityZero = new KeyValue(stage.opacityProperty(), 0.0);
		final KeyFrame keyFrameOpacityZero = new KeyFrame(Duration.ZERO, keyValueOpacityZero);

		// Sets opacity to 1.0 (fully visible) over the time of 3000 milliseconds.
		final KeyValue keyValueOpacityOne = new KeyValue(stage.opacityProperty(), 1.0);
		final KeyFrame keyFrameOpacityOne = new KeyFrame(Duration.millis(450), keyValueOpacityOne);

		timeline.getKeyFrames().addAll(keyFrameOpacityZero, keyFrameOpacityOne);

		timeline.setOnFinished(e -> trayIsShowing = true);

		return timeline;
	}

	@Override
	protected Timeline setupDismissAnimation()
	{
		final Timeline timeline = new Timeline();

		// At this stage the opacity is already at 1.0

		// Lowers the opacity to 0.0 within 2000 milliseconds
		final KeyValue keyValueOpacityZero = new KeyValue(stage.opacityProperty(), 0.0);
		final KeyFrame keyFrameOpacityZero = new KeyFrame(Duration.millis(350), keyValueOpacityZero);

		timeline.getKeyFrames().addAll(keyFrameOpacityZero);

		// Action to be performed when the animation has finished
		timeline.setOnFinished(e ->
		{
			trayIsShowing = false;
			stage.close();
			stage.setLocation(stage.getBottomRight());
		});

		return timeline;
	}

}
