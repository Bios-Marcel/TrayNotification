package com.github.plushaze.traynotification.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CustomStage extends Popup
{

	private final Point2D bottomRight;

	public CustomStage(final Pane ap)
	{
		getContent().add(ap);
		setSize(ap.getPrefWidth(), ap.getPrefHeight());

		final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		final double x = screenBounds.getMinX() + screenBounds.getWidth() - ap.getPrefWidth() - 2;
		final double y = screenBounds.getMinY() + screenBounds.getHeight() - ap.getPrefHeight() - 2;

		bottomRight = new Point2D(x, y);
	}

	public void setSize(final double width, final double height)
	{
		setWidth(width);
		setHeight(height);
	}

	public Point2D getBottomRight()
	{
		return bottomRight;
	}

	public Point2D getOffScreenBounds()
	{
		final Point2D loc = getBottomRight();

		return new Point2D(loc.getX() + getWidth(), loc.getY());
	}

	public void setLocation(final Point2D loc)
	{
		xLocationProperty.set(loc.getX());
		yLocationProperty.set(loc.getY());
	}

	/**
	 * <code>x</code> Location Property for the overlaying {@link Stage}.
	 */
	private final DoubleProperty xLocationProperty = new SimpleDoubleProperty() {
		@Override
		public void set(final double newValue)
		{
			setX(newValue);
		}

		@Override
		public double get()
		{
			return getX();
		}
	};

	/**
	 * <code>y</code> Location Property for the overlaying {@link Stage}.
	 */
	private final DoubleProperty yLocationProperty = new SimpleDoubleProperty() {
		@Override
		public void set(final double newValue)
		{
			setY(newValue);
		}

		@Override
		public double get()
		{
			return getY();
		}
	};

	public DoubleProperty xLocationProperty()
	{
		return xLocationProperty;
	}

	public DoubleProperty yLocationProperty()
	{
		return yLocationProperty;
	}

}
