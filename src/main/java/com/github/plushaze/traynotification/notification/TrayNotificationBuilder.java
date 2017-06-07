package com.github.plushaze.traynotification.notification;

import java.util.Objects;

import javafx.stage.Stage;

public class TrayNotificationBuilder
{
	private String			title	= "";
	private String			message	= "";
	private String			stylesheetLocation;
	private Notification	type	= Notifications.INFORMATION;
	private Stage			owner	= null;

	/**
	 * Sets the title.
	 *
	 * @param titleToSet
	 *            title to be set (default is an empty string)
	 * @return own {@link TrayNotificationBuilder} instance
	 */
	public TrayNotificationBuilder title(final String titleToSet)
	{
		title = elseGet(titleToSet, "");
		return this;
	}

	/**
	 * Sets the message.
	 *
	 * @param messageToSet
	 *            message to be set (default is an empty string)
	 * @return own {@link TrayNotificationBuilder} instance
	 */
	public TrayNotificationBuilder message(final String messageToSet)
	{
		message = elseGet(messageToSet, "");
		return this;
	}

	/**
	 * Sets the Stage that owns this TrayNotification, this will cause the TrayNotification to apply
	 * the owner stages icons.
	 *
	 * @param ownerToSet
	 *            stage that owns this TrayNotification (default is null)
	 * @return own {@link TrayNotificationBuilder} instance
	 */
	public TrayNotificationBuilder owner(final Stage ownerToSet)
	{
		this.owner = ownerToSet;
		return this;
	}

	/**
	 * Sets the Notification (type).
	 *
	 * @param typeToSet
	 *            type to be set (default is {@link Notifications#INFORMATION})
	 * @return own {@link TrayNotificationBuilder} instance
	 */
	public TrayNotificationBuilder type(final Notification typeToSet)
	{
		type = elseGet(typeToSet, Notifications.INFORMATION);
		return this;
	}

	/**
	 * Sets the stylesheet location.
	 *
	 * @param stylesheetLocationToSet
	 *            stylesheet location to be set
	 * @return own {@link TrayNotificationBuilder} instance
	 */
	public TrayNotificationBuilder stylesheet(final String stylesheetLocationToSet)
	{
		stylesheetLocation = stylesheetLocationToSet;
		return this;
	}

	public TrayNotification build()
	{
		final TrayNotification newTrayNotification = new TrayNotification(owner, title, message, type, stylesheetLocation);
		return newTrayNotification;
	}

	private static <T> T elseGet(final T obj, final T altObj)
	{
		return Objects.isNull(obj) ? altObj : obj;
	}
}
