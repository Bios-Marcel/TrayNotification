package com.github.plushaze.traynotification.notification;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import com.github.plushaze.traynotification.animations.Animation;
import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.models.CustomStage;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public final class TrayNotification
{

	@FXML
	private Label		lblTitle, lblMessage, lblClose;
	@FXML
	private ImageView	imageIcon;
	@FXML
	private Rectangle	rectangleColor;
	@FXML
	private AnchorPane	rootNode;

	private CustomStage					stage;
	private Notification				notification;
	private Animation					animation;
	private EventHandler<ActionEvent>	onDismissedCallBack, onShownCallback;

	/**
	 * Initializes an instance of the tray notification object
	 *
	 * @param title
	 *            The title text to assign to the tray
	 * @param body
	 *            The body text to assign to the tray
	 * @param notification
	 *            The notification type to assign to the tray
	 * @param styleSheetLocation
	 *            Path of the Stylesheet that should be used
	 */
	public TrayNotification(final String title, final String body, final Notification notification, final String styleSheetLocation)
	{
		initTrayNotification(title, body, notification, styleSheetLocation);
	}

	private void initTrayNotification(final String title, final String message, final Notification type, final String styleSheetLocation)
	{
		final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/TrayNotification.fxml"));

		fxmlLoader.setController(this);
		try
		{
			fxmlLoader.load();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
			return;
		}

		initStage(styleSheetLocation);
		initAnimations();

		setTray(title, message, type);
	}

	private void initAnimations()
	{
		setAnimation(Animations.FADE); // Default animation type
	}

	private void initStage(final String styleSheetLocation)
	{
		stage = new CustomStage(rootNode, StageStyle.UNDECORATED);
		stage.setScene(new Scene(rootNode));
		stage.setAlwaysOnTop(true);
		stage.setLocation(stage.getBottomRight());
		if (Objects.nonNull(styleSheetLocation))
		{
			System.out.println("Setting style");
			stage.getScene().getStylesheets().add(styleSheetLocation);
		}

		lblClose.setOnMouseClicked(e -> dismiss());
	}

	public void setNotification(final Notification nType)
	{
		notification = nType;

		final URL imageLocation = getClass().getClassLoader().getResource(nType.getURLResource());
		setRectangleFill(Paint.valueOf(nType.getPaintHex()));
		setImage(new Image(imageLocation.toString()));
		setTrayIcon(imageIcon.getImage());
	}

	public Notification getNotification()
	{
		return notification;
	}

	public void setTray(final String title, final String message, final Notification type)
	{
		setTitle(title);
		setMessage(message);
		setNotification(type);
	}

	public void setTray(final String title, final String message, final Image img, final Paint rectangleFill, final Animation animation)
	{
		setTitle(title);
		setMessage(message);
		setImage(img);
		setRectangleFill(rectangleFill);
		setAnimation(animation);
	}

	public boolean isTrayShowing()
	{
		return animation.isShowing();
	}

	/**
	 * Shows and dismisses the tray notification
	 *
	 * @param dismissDelay
	 *            How long to delay the start of the dismiss animation
	 */
	public void showAndDismiss(final Duration dismissDelay)
	{
		if (!isTrayShowing())
		{
			stage.show();

			onShown();
			animation.playSequential(dismissDelay);
		}
		else
		{
			dismiss();
		}

		onDismissed();
	}

	/**
	 * Displays the notification tray
	 */
	public void showAndWait()
	{
		if (!isTrayShowing())
		{
			stage.show();

			animation.playShowAnimation();

			onShown();
		}
	}

	/**
	 * Dismisses the notifcation tray
	 */
	public void dismiss()
	{
		if (isTrayShowing())
		{
			animation.playDismissAnimation();
			onDismissed();
		}
	}

	private void onShown()
	{
		if (onShownCallback != null)
		{
			onShownCallback.handle(new ActionEvent());
		}
	}

	private void onDismissed()
	{
		if (onDismissedCallBack != null)
		{
			onDismissedCallBack.handle(new ActionEvent());
		}
	}

	/**
	 * Sets an action event for when the tray has been dismissed
	 *
	 * @param event
	 *            The event to occur when the tray has been dismissed
	 */
	public void setOnDismiss(final EventHandler<ActionEvent> event)
	{
		onDismissedCallBack = event;
	}

	/**
	 * Sets an action event for when the tray has been shown
	 *
	 * @param event
	 *            The event to occur after the tray has been shown
	 */
	public void setOnShown(final EventHandler<ActionEvent> event)
	{
		onShownCallback = event;
	}

	/**
	 * Sets a new task bar image for the tray
	 *
	 * @param img
	 *            The image to assign
	 */
	public void setTrayIcon(final Image img)
	{
		stage.getIcons().clear();
		stage.getIcons().add(img);
	}

	public Image getTrayIcon()
	{
		return stage.getIcons().get(0);
	}

	/**
	 * Sets a title to the tray
	 *
	 * @param txt
	 *            The text to assign to the tray icon
	 */
	public void setTitle(final String txt)
	{
		Platform.runLater(() -> lblTitle.setText(txt));
	}

	public String getTitle()
	{
		return lblTitle.getText();
	}

	/**
	 * Sets the message for the tray notification
	 *
	 * @param txt
	 *            The text to assign to the body of the tray notification
	 */
	public void setMessage(final String txt)
	{
		lblMessage.setText(txt);
	}

	public String getMessage()
	{
		return lblMessage.getText();
	}

	public void setImage(final Image img)
	{
		imageIcon.setImage(img);

		setTrayIcon(img);
	}

	public Image getImage()
	{
		return imageIcon.getImage();
	}

	public void setRectangleFill(final Paint value)
	{
		rectangleColor.setFill(value);
	}

	public Paint getRectangleFill()
	{
		return rectangleColor.getFill();
	}

	public void setAnimation(final Animation animation)
	{
		this.animation = animation;
	}

	public void setAnimation(final Animations animation)
	{
		setAnimation(animation.newInstance(stage));
	}

	public Animation getAnimation()
	{
		return animation;
	}

}
