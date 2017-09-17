import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.notification.TrayNotificationBuilder;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Test extends Application
{
	public static void main(final String[] args)
	{
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception
	{
		primaryStage.setWidth(200);
		primaryStage.setHeight(200);
		primaryStage.centerOnScreen();
		primaryStage.show();

		TrayNotificationBuilder.setDefaultOwner(primaryStage);
		TrayNotificationBuilder.darkByDefault();

		new TrayNotificationBuilder().title("Test").message("Test").animation(Animations.SLIDE).build().showAndDismiss(Duration.seconds(3));

	}
}
