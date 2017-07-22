package com.github.plushaze.traynotification.notification;

import java.util.Objects;

import com.github.plushaze.traynotification.animations.Animation;
import com.github.plushaze.traynotification.animations.Animations;

import javafx.stage.Stage;

/**
 * Helps creating {@link TrayNotification TrayNotifications}.
 *
 * @author Marcel
 */
public class TrayNotificationBuilder
{
	private static String	defaultStylesheet;
	private static Stage	defaultOwner;

	private String				title	= "";
	private String				message	= "";
	private String				stylesheetLocation;
	private NotificationType	type	= NotificationTypeImplementations.INFORMATION;
	private Stage				owner	= null;

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
	 * Sets the default owner stage for every created {@link TrayNotification} in this applications
	 * context.
	 *
	 * @param defaultOwnerStage
	 *            the Stage to set as the default owner
	 */
	public static void setDefaultOwner(final Stage defaultOwnerStage)
	{
		defaultOwner = defaultOwnerStage;
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
	 *            type to be set (default is {@link NotificationTypeImplementations#INFORMATION})
	 * @return own {@link TrayNotificationBuilder} instance
	 */
	public TrayNotificationBuilder type(final NotificationType typeToSet)
	{
		type = elseGet(typeToSet, NotificationTypeImplementations.INFORMATION);
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
	 * @param animationToSet
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
		final TrayNotification newTrayNotification = new TrayNotification(elseGet(owner, defaultOwner), title, message, type, elseGet(stylesheetLocation, defaultStylesheet));
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
