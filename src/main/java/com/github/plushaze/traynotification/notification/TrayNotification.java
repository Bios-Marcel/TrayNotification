package com.github.plushaze.traynotification.notification;

import java.io.IOException;
import java.util.Objects;

import com.github.plushaze.traynotification.animations.Animation;
import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.models.CustomStage;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public final class TrayNotification
{
	@FXML
	private Pane trayNotificationRootNode;

	@FXML
	private Pane	rectangleColor;
	@FXML
	private Region	imageIcon;
	@FXML
	private Label	titleLabel;
	@FXML
	private Label	messageLabel;

	@FXML
	private StackPane trayNotificationCloseButton;

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
	TrayNotification(final Stage owner, final String title, final String body, final Notification notification, final String styleSheetLocation)
	{
		initTrayNotification(owner, title, body, notification, styleSheetLocation);
	}

	private void initTrayNotification(final Stage owner, final String title, final String message, final Notification type, final String styleSheetLocation)
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

		initStage(owner == null ? FXCollections.emptyObservableList() : owner.getIcons(), styleSheetLocation);
		initAnimations();

		setTitle(title);
		setMessage(message);
		setNotification(type);
	}

	private void initAnimations()
	{
		setAnimation(Animations.NONE); // Default animation type
	}

	ObjectProperty<EventHandler<MouseEvent>> onMouseClicked = new SimpleObjectProperty<>();

	public final ObjectProperty<EventHandler<MouseEvent>> onMouseClickedProperty()
	{
		return onMouseClicked;
	}

	public final void setOnMouseClicked(final EventHandler<MouseEvent> value)
	{
		onMouseClickedProperty().set(value);
	}

	public final EventHandler<MouseEvent> getOnMouseClicked()
	{
		return onMouseClickedProperty().get();
	}

	private void initStage(final ObservableList<Image> icons, final String styleSheetLocation)
	{
		stage = new CustomStage(trayNotificationRootNode, StageStyle.UNDECORATED);
		stage.setScene(new Scene(trayNotificationRootNode));
		stage.setAlwaysOnTop(true);
		stage.setLocation(stage.getBottomRight());
		stage.getScene().getStylesheets().add(getClass().getResource("/styles/defaultStyle.css").toExternalForm());
		stage.getIcons().clear();
		stage.getIcons().addAll(icons);

		if (Objects.nonNull(styleSheetLocation))
		{
			stage.getScene().getStylesheets().add(styleSheetLocation);
		}

		trayNotificationCloseButton.setOnMouseClicked(e -> dismiss());

		rectangleColor.onMouseClickedProperty().bind(onMouseClicked);
		titleLabel.onMouseClickedProperty().bind(onMouseClicked);
		messageLabel.onMouseClickedProperty().bind(onMouseClicked);
		imageIcon.onMouseClickedProperty().bind(onMouseClicked);
	}

	/**
	 * Applies the Colors and icons.
	 *
	 * @param nType
	 *            The {@link Notification} Enum containing the svg, color and size info
	 */
	public void setNotification(final Notification nType)
	{
		notification = nType;

		Integer width = null;
		Integer height = null;
		if (nType.getCustomWidth().isPresent())
		{
			width = nType.getCustomWidth().get();
		}
		if (nType.getCustomHeight().isPresent())
		{
			height = nType.getCustomHeight().get();
		}
		setImage(nType.getSVG(), nType.getPaintHex(), width != null && width >= 1 ? width : null, height != null && height >= 1 ? height : null);
		setRectangleFill(nType.getPaintHex());
		// setTrayIcon(imageIcon.getImage());
	}

	public Notification getNotification()
	{
		return notification;
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
		Platform.runLater(() -> titleLabel.setText(txt));
	}

	public String getTitle()
	{
		return titleLabel.getText();
	}

	/**
	 * Sets the message for the tray notification
	 *
	 * @param txt
	 *            The text to assign to the body of the tray notification
	 */
	public void setMessage(final String txt)
	{
		messageLabel.setText(txt);
	}

	public String getMessage()
	{
		return messageLabel.getText();
	}

	public void setImage(final String svg, final String imageColor)
	{
		setImage(svg, imageColor, null, null);
	}

	public void setImageWithWidth(final String svg, final String imageColor, final Integer width)
	{
		setImage(svg, imageColor, width, null);
	}

	public void setImageWithHeight(final String svg, final String imageColor, final Integer height)
	{
		setImage(svg, imageColor, null, height);
	}

	public void setImage(final String svg, final String imageColor, final Integer width, final Integer height)
	{
		final StringBuilder style = new StringBuilder();
		if (width != null)
		{
			style.append("-fx-min-width: " + width + " !important; -fx-max-width: " + width + " !important;");
		}
		if (height != null)
		{
			style.append("-fx-min-height: " + height + " !important; -fx-max-height: " + height + " !important;");
		}
		style.append("-fx-background-color: " + imageColor + "; -fx-shape: '" + svg + "';");
		setImage(style.toString());
	}

	private void setImage(final String style)
	{
		imageIcon.setStyle(style);
	}

	// public Image getImage()
	// {
	// return imageIcon.getImage();
	// }

	public void setRectangleFill(final String color)
	{
		rectangleColor.setStyle("-fx-background-color: " + color + ';');
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
