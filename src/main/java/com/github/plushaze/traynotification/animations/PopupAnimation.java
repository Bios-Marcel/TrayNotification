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
		final Timeline tl = new Timeline();

		final KeyValue kv1 = new KeyValue(stage.yLocationProperty(), stage.getY() + stage.getWidth());
		final KeyFrame kf1 = new KeyFrame(Duration.millis(2000), kv1);

		final KeyValue kv2 = new KeyValue(stage.opacityProperty(), 0.0);
		final KeyFrame kf2 = new KeyFrame(Duration.millis(2000), kv2);

		tl.getKeyFrames().addAll(kf1, kf2);

		tl.setOnFinished(e ->
		{
			trayIsShowing = false;
			stage.close();
			stage.setLocation(stage.getBottomRight());
		});

		return tl;
	}

	@Override
	protected Timeline setupShowAnimation()
	{
		final Timeline tl = new Timeline();

		final KeyValue kv1 = new KeyValue(stage.yLocationProperty(), stage.getBottomRight().getY() + stage.getWidth());
		final KeyFrame kf1 = new KeyFrame(Duration.ZERO, kv1);

		final KeyValue kv2 = new KeyValue(stage.yLocationProperty(), stage.getBottomRight().getY());
		final KeyFrame kf2 = new KeyFrame(Duration.millis(1000), kv2);

		final KeyValue kv3 = new KeyValue(stage.opacityProperty(), 0.0);
		final KeyFrame kf3 = new KeyFrame(Duration.ZERO, kv3);

		final KeyValue kv4 = new KeyValue(stage.opacityProperty(), 1.0);
		final KeyFrame kf4 = new KeyFrame(Duration.millis(2000), kv4);

		tl.getKeyFrames().addAll(kf1, kf2, kf3, kf4);

		tl.setOnFinished(e -> trayIsShowing = true);

		return tl;
	}

}
