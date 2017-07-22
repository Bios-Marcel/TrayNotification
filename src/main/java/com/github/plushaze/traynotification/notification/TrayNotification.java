package com.github.plushaze.traynotification.notification;

import java.io.IOException;
import java.util.Objects;

import com.github.plushaze.traynotification.animations.Animation;
import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.models.CustomStage;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public final class TrayNotification
{
	private Stage	ownerStage;
	@FXML
	private Pane	trayNotificationRootNode;

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
	private NotificationType			notification;
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
	TrayNotification(final Stage owner, final String title, final String body, final NotificationType notification, final String styleSheetLocation)
	{
		initTrayNotification(owner, title, body, notification, styleSheetLocation);
	}

	private void initTrayNotification(final Stage owner, final String title, final String message, final NotificationType type, final String styleSheetLocation)
	{
		ownerStage = owner;
		final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/TrayNotification.fxml"));
		fxmlLoader.setController(this);

		try
		{
			fxmlLoader.load();
		}
		catch (final IOException cantLoadFXML)
		{// if this occurs, there is something fundamentally wrong with this library.
			throw new RuntimeException("Unexpected Exception", cantLoadFXML);
		}

		initStage(styleSheetLocation);
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

	/**
	 * @return the {@link EventHandler} that will be executed if the {@link TrayNotification} is
	 *         clicked
	 */
	public final EventHandler<MouseEvent> getOnMouseClicked()
	{
		return onMouseClickedProperty().get();
	}

	private void initStage(final String styleSheetLocation)
	{
		stage = new CustomStage(trayNotificationRootNode);
		stage.setLocation(stage.getBottomRight());
		stage.getScene().getStylesheets().add(getClass().getResource("/styles/defaultStyle.css").toExternalForm());

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
	 *            The {@link NotificationType} Enum containing the svg, color and size info
	 */
	@SuppressWarnings("null")
	public void setNotification(final NotificationType nType)
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

		width = Objects.nonNull(width) && width >= 1 ? width : null;
		height = Objects.nonNull(height) && height >= 1 ? height : null;

		setImage(nType.getSVG(), nType.getPaintHex(), width, height);
		setRectangleFill(nType.getPaintHex());
	}

	/**
	 * @return the {@link NotificationType} used for this {@link TrayNotification}
	 */
	public NotificationType getNotification()
	{
		return notification;
	}

	/**
	 * @return true if the tray is showing and false otherwise
	 */
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
			stage.show(ownerStage);

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
			stage.show(ownerStage);

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
	 * Sets a title to the tray
	 *
	 * @param txt
	 *            The text to assign to the tray icon
	 */
	public void setTitle(final String txt)
	{
		Platform.runLater(() -> titleLabel.setText(txt));
	}

	/**
	 * @return the title that will be displayed inside of the {@link TrayNotification}
	 */
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

	/**
	 * @return the Message that will be displayed inside of the {@link TrayNotification}
	 */
	public String getMessage()
	{
		return messageLabel.getText();
	}

	void setImage(final String svg, final String imageColor)
	{
		setImage(svg, imageColor, null, null);
	}

	void setImageWithWidth(final String svg, final String imageColor, final Integer width)
	{
		setImage(svg, imageColor, width, null);
	}

	void setImageWithHeight(final String svg, final String imageColor, final Integer height)
	{
		setImage(svg, imageColor, null, height);
	}

	void setImage(final String svg, final String imageColor, final Integer width, final Integer height)
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

	/**
	 * Sets the color of the rectangle on the left side of the TrayNotification.
	 *
	 * @param color
	 *            the color that will be set
	 */
	public void setRectangleFill(final String color)
	{
		rectangleColor.setStyle("-fx-background-color: " + color + ';');
	}

	/**
	 * Sets the {@link Animation} that the stage will use when showing / hiding.
	 *
	 * @param animation
	 *            {@link Animation} to set
	 */
	public void setAnimation(final Animation animation)
	{
		this.animation = animation;
	}

	/**
	 * Sets the {@link Animation} that the stage will use when showing / hiding.
	 *
	 * @param animation
	 *            the enum value to create a default {@link Animation} with
	 */
	public void setAnimation(final Animations animation)
	{
		setAnimation(animation.newInstance(stage));
	}

	/**
	 * @return the animation, that this instance will use when showing / hiding
	 */
	public Animation getAnimation()
	{
		return animation;
	}

}
