package com.github.plushaze.traynotification.animations;

import com.github.plushaze.traynotification.models.TrayPopup;

import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class AbstractAnimation implements Animation
{
	protected final TrayPopup stage;

	private final Timeline				showAnimation;
	private final Timeline				dismissAnimation;
	private final SequentialTransition	sq;

	protected volatile boolean trayIsShowing;

	protected AbstractAnimation(final TrayPopup stage)
	{
		this.stage = stage;

		showAnimation = setupShowAnimation();
		dismissAnimation = setupDismissAnimation();

		sq = new SequentialTransition(setupShowAnimation(), setupDismissAnimation());
	}

	protected abstract Timeline setupShowAnimation();

	protected abstract Timeline setupDismissAnimation();

	@Override
	public final TrayPopup getStage()
	{
		return stage;
	}

	@Override
	public final void playSequential(final Duration dismissDelay)
	{
		sq.getChildren().get(1).setDelay(dismissDelay);
		sq.play();
	}

	@Override
	public final void playShowAnimation()
	{
		showAnimation.play();
	}

	@Override
	public final void playDismissAnimation()
	{
		dismissAnimation.play();
	}

	@Override
	public final boolean isShowing()
	{
		return trayIsShowing;
	}

}
