package com.github.plushaze.traynotification.animations;

import com.github.plushaze.traynotification.models.CustomStage;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

final class PopupAnimation extends AbstractAnimation
{

	PopupAnimation(final CustomStage stage)
	{
		super(stage);
	}

	@Override
	protected Timeline setupDismissAnimation()
	{
		final Timeline timeline = new Timeline();

		final KeyValue keyValueLocation = new KeyValue(stage.yLocationProperty(), stage.getY() + stage.getWidth());
		final KeyFrame keyFrameLocation = new KeyFrame(Duration.millis(1000), keyValueLocation);

		final KeyValue keyValueOpacity = new KeyValue(stage.opacityProperty(), 0.0);
		final KeyFrame keyFrameOpacity = new KeyFrame(Duration.millis(300), keyValueOpacity);

		timeline.getKeyFrames().addAll(keyFrameLocation, keyFrameOpacity);

		timeline.setOnFinished(e ->
		{
			trayIsShowing = false;
			stage.close();
			stage.setLocation(stage.getBottomRight());
		});

		return timeline;
	}

	@Override
	protected Timeline setupShowAnimation()
	{
		final Timeline timeline = new Timeline();

		final KeyValue keyValuePositionInitial = new KeyValue(stage.yLocationProperty(), stage.getBottomRight().getY() + stage.getWidth());
		final KeyFrame keyFramePositionInitial = new KeyFrame(Duration.ZERO, keyValuePositionInitial);

		final KeyValue keyValuePositionFinal = new KeyValue(stage.yLocationProperty(), stage.getBottomRight().getY());
		final KeyFrame keyFramePositionFinal = new KeyFrame(Duration.millis(600), keyValuePositionFinal);

		final KeyValue keyValueOpacityZero = new KeyValue(stage.opacityProperty(), 0.0);
		final KeyFrame keyFrameOpacityZero = new KeyFrame(Duration.ZERO, keyValueOpacityZero);

		final KeyValue keyValueOpacityOne = new KeyValue(stage.opacityProperty(), 1.0);
		final KeyFrame keyFrameOpacityOne = new KeyFrame(Duration.millis(750), keyValueOpacityOne);

		timeline.getKeyFrames().addAll(keyFramePositionInitial, keyFramePositionFinal, keyFrameOpacityZero, keyFrameOpacityOne);

		timeline.setOnFinished(e -> trayIsShowing = true);

		return timeline;
	}

}
