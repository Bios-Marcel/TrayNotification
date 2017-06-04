package com.github.plushaze.traynotification.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomStage extends Stage
{

	private final Location bottomRight;

	public CustomStage(final Pane ap, final StageStyle style)
	{
		initStyle(style);

		setSize(ap.getPrefWidth(), ap.getPrefHeight());

		final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		final double x = screenBounds.getMinX() + screenBounds.getWidth() - ap.getPrefWidth() - 2;
		final double y = screenBounds.getMinY() + screenBounds.getHeight() - ap.getPrefHeight() - 2;

		bottomRight = Location.at(x, y);
	}

	public Location getBottomRight()
	{
		return bottomRight;
	}

	public void setSize(final double width, final double height)
	{
		setWidth(width);
		setHeight(height);
	}

	public Location getOffScreenBounds()
	{
		final Location loc = getBottomRight();

		return Location.at(loc.getX() + this.getWidth(), loc.getY());
	}

	public void setLocation(final Location loc)
	{
		setX(loc.getX());
		setY(loc.getY());
	}

	private final SimpleDoubleProperty xLocationProperty = new SimpleDoubleProperty() {
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

	public SimpleDoubleProperty xLocationProperty()
	{
		return xLocationProperty;
	}

	private final SimpleDoubleProperty yLocationProperty = new SimpleDoubleProperty() {
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

	public SimpleDoubleProperty yLocationProperty()
	{
		return yLocationProperty;
	}

}
