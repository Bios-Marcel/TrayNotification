package com.github.plushaze.traynotification.animations;

import com.github.plushaze.traynotification.models.TrayPopup;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

final class SlideAnimation extends AbstractAnimation
{

	SlideAnimation(final TrayPopup customStage)
	{
		super(customStage);
	}

	@Override
	protected Timeline setupShowAnimation()
	{
		final Timeline tl = new Timeline();

		// Sets the x location of the tray off the screen
		final double offScreenX = stage.getOffScreenBounds().getX();
		final KeyValue kvX = new KeyValue(stage.xLocationProperty(), offScreenX);
		final KeyFrame frame1 = new KeyFrame(Duration.ZERO, kvX);

		// Animates the Tray onto the screen and interpolates at a tangent for 300 millis
		final Interpolator interpolator = Interpolator.TANGENT(Duration.millis(500), 50);
		final KeyValue kvInter = new KeyValue(stage.xLocationProperty(), stage.getBottomRight().getX(), interpolator);
		final KeyFrame frame2 = new KeyFrame(Duration.millis(500), kvInter);

		// Sets opacity to 0 instantly
		final KeyValue kvOpacity = new KeyValue(stage.opacityProperty(), 0.0);
		final KeyFrame frame3 = new KeyFrame(Duration.ZERO, kvOpacity);

		// Increases the opacity to fully visible whilst moving in the space of 1000 millis
		final KeyValue kvOpacity2 = new KeyValue(stage.opacityProperty(), 1.0);
		final KeyFrame frame4 = new KeyFrame(Duration.millis(500), kvOpacity2);

		tl.getKeyFrames().addAll(frame1, frame2, frame3, frame4);

		tl.setOnFinished(e -> trayIsShowing = true);

		return tl;
	}

	@Override
	protected Timeline setupDismissAnimation()
	{
		final Timeline tl = new Timeline();

		final double offScreenX = stage.getOffScreenBounds().getX();
		final Interpolator interpolator = Interpolator.TANGENT(Duration.millis(300), 50);
		final double trayPadding = 3;

		// The destination X location for the stage. Which is off the users screen
		// Since the tray has some padding, we want to hide that too
		final KeyValue kvX = new KeyValue(stage.xLocationProperty(), offScreenX + trayPadding, interpolator);
		final KeyFrame frame1 = new KeyFrame(Duration.millis(300), kvX);

		// Change the opacity level to 0.4 over the duration of 2000 millis
		final KeyValue kvOpacity = new KeyValue(stage.opacityProperty(), 0.4);
		final KeyFrame frame2 = new KeyFrame(Duration.millis(200), kvOpacity);

		tl.getKeyFrames().addAll(frame1, frame2);

		tl.setOnFinished(e ->
		{
			trayIsShowing = false;
			stage.hide();
			stage.setLocation(stage.getBottomRight());
		});

		return tl;
	}

}
