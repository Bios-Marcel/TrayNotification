package com.github.plushaze.traynotification;

import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.notification.Notification;
import com.github.plushaze.traynotification.notification.Notifications;
import com.github.plushaze.traynotification.notification.TrayNotification;
import com.github.plushaze.traynotification.notification.TrayNotificationBuilder;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.util.Duration;

public final class ReadMeTest
{

	private volatile TrayNotification tray;

	@BeforeClass
	public static void initializeJavaFX() throws InterruptedException
	{
		final CountDownLatch latch = new CountDownLatch(1);
		SwingUtilities.invokeLater(() ->
		{
			@SuppressWarnings("unused")
			final JFXPanel jfxPanel = new JFXPanel(); // initializes JavaFX environment
			latch.countDown();
		});

		latch.await();
	}

	@AfterClass
	public static void shutdownJavaFX()
	{
		Platform.exit();
	}

	@Before
	public void initializeTray()
	{
		Platform.runLater(() -> tray = new TrayNotificationBuilder().build());
	}

	@Test
	public void creatingANewTrayNotification()
	{
		final String title = "Congratulations sir";
		final String message = "You've successfully created your first Tray Notification";

		Platform.runLater(() -> new TrayNotificationBuilder().title(title).message(message).type(Notifications.SUCCESS).build().showAndWait());
	}

	@Test
	public void usingDifferentAnimationsAndNotifications()
	{
		final String title = "Download quota reached";
		final String message = "Your download quota has been reached. Panic.";
		final Notification notification = Notifications.NOTICE;

		Platform.runLater(() ->
		{
			tray.setTitle(title);
			tray.setMessage(message);
			tray.setNotification(notification);
			tray.setAnimation(Animations.FADE);
			tray.showAndWait();
		});
	}

	@Test
	public void creatingACustomTrayNotification()
	{
		final Image whatsAppImg = new Image("https://cdn4.iconfinder.com/data/icons/iconsimple-logotypes/512/whatsapp-128.png");

		Platform.runLater(() ->
		{
			tray.setTitle("New WhatsApp Message");
			tray.setMessage("Github - I like your new notification release. Nice one.");
			tray.setRectangleFill("#2A9A84");
			tray.setAnimation(Animations.POPUP);
			tray.setImage(whatsAppImg);
			tray.showAndDismiss(Duration.seconds(2));
		});
	}

}
