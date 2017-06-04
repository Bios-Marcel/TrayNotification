package com.github.plushaze.traynotification.models;

public final class Location
{

	public static Location at(final double x, final double y)
	{
		return new Location(x, y);
	}

	private final double x, y;

	private Location(final double x, final double y)
	{
		this.x = x;
		this.y = y;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	@Override
	public String toString()
	{
		return String.format("(%f, %f)", x, y);
	}

	@Override
	public int hashCode()
	{
		return Double.valueOf(x).hashCode() ^ Double.valueOf(y).hashCode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		else if (obj == null || !(obj instanceof Location))
		{
			return false;
		}
		final Location loc = (Location) obj;
		return x == loc.x && y == loc.y;
	}

}
