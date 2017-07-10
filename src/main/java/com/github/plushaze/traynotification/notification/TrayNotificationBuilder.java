package com.github.plushaze.traynotification.notification;

import java.util.Objects;

import com.github.plushaze.traynotification.animations.Animation;
import com.github.plushaze.traynotification.animations.Animations;

import javafx.stage.Stage;

public class TrayNotificationBuilder
{
	private static String defaultStylesheet;

	private String			title	= "";
	private String			message	= "";
	private String			stylesheetLocation;
	private Notification	type	= Notifications.INFORMATION;
	private Stage			owner	= null;

	private Animations	animations	= Animations.NONE;
	private Animation	animation	= null;

	/**
	 * Sets the default stylesheet location that will be used if no other has been specified.
	 *
	 * @param stylesheet
	 *            the path for the default stylesheet
	 */
	public static void setDefaultStylesheet(final String stylesheet)
	{
		defaultStylesheet = stylesheet;
	}

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

	/**
	 * Sets the Animation for the {@link TrayNotification} object that will be created.
	 *
	 * @param animationsToSet
	 *            the {@link Animations ANimation} that is to set
	 * @return own {@link TrayNotificationBuilder} instance
	 */
	public TrayNotificationBuilder animation(final Animations animationsToSet)
	{
		animations = elseGet(animationsToSet, Animations.NONE);
		return this;
	}

	/**
	 * Sets the Animation for the {@link TrayNotification} object that will be created.
	 *
	 * @param animationsToSet
	 *            the {@link Animation ANimation} that is to set
	 * @return own {@link TrayNotificationBuilder} instance
	 */
	public TrayNotificationBuilder animation(final Animation animationToSet)
	{
		animation = animationToSet;
		return this;
	}

	/**
	 * @return Returns the built {@link TrayNotification}
	 */
	public TrayNotification build()
	{
		final TrayNotification newTrayNotification = new TrayNotification(owner, title, message, type, elseGet(stylesheetLocation, defaultStylesheet));
		if (Objects.nonNull(animation))
		{
			newTrayNotification.setAnimation(animation);
		}
		else if (Objects.nonNull(animations))
		{
			newTrayNotification.setAnimation(animations);
		}
		return newTrayNotification;
	}

	private static <T> T elseGet(final T obj, final T altObj)
	{
		return Objects.isNull(obj) ? altObj : obj;
	}
}
