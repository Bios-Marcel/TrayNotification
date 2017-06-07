package com.github.plushaze.traynotification.notification;

import java.util.Optional;

public interface Notification
{

	String getSVG();

	String getPaintHex();

	Optional<Integer> getCustomWidth();

	Optional<Integer> getCustomHeight();
}
