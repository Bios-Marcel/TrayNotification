package com.github.plushaze.traynotification.notification;

import java.util.Optional;

public enum Notifications implements Notification
{

	INFORMATION("M11 17h2v-6h-2v6zm1-15C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8zM11 9h2V7h-2v2z", "#2C54AB"),
	// SUCCESS("M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z", "#009961"),
	SUCCESS("M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z", "#009961", -1, 50),
	WARNING("M1 21h22L12 2 1 21zm12-3h-2v-2h2v2zm0-4h-2v-4h2v4z", "#E23E0A"),
	ERROR("M11 15h2v2h-2zm0-8h2v6h-2zm.99-5C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8z", "#CC0033");

	private final String	svg;
	private final String	paintHex;

	private final int[] widthAndHeight;

	Notifications(final String svg, final String paintHex, final int... widthAndHeight)
	{
		this.svg = svg;
		this.paintHex = paintHex;
		this.widthAndHeight = widthAndHeight;
	}

	@Override
	public String getSVG()
	{
		return svg;
	}

	@Override
	public String getPaintHex()
	{
		return paintHex;
	}

	@Override
	public Optional<Integer> getCustomWidth()
	{
		if (widthAndHeight != null && widthAndHeight.length >= 1)
		{
			return Optional.of(widthAndHeight[0]);
		}

		return Optional.empty();
	}

	@Override
	public Optional<Integer> getCustomHeight()
	{
		if (widthAndHeight != null && widthAndHeight.length >= 2)
		{
			return Optional.of(widthAndHeight[1]);
		}

		return Optional.empty();
	}

}
