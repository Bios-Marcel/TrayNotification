package com.github.plushaze.traynotification.notification;

import java.util.Optional;

/**
 * Interface for NotificationTypes
 *
 * @author Marcel
 */
public interface NotificationType
{
	/**
	 * @return the String that was used to create the SVG
	 */
	String getSVG();

	/**
	 * @return the color, that was used for the svg and the rectangle
	 */
	String getPaintHex();

	/**
	 * @return an {@link Optional} containing the custom width or an {@link Optional#empty()}
	 */
	Optional<Integer> getCustomWidth();

	/**
	 * @return an {@link Optional} containing the custom height or an {@link Optional#empty()}
	 */
	Optional<Integer> getCustomHeight();
}
