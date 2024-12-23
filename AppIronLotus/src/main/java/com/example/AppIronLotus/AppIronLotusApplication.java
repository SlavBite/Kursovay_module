package com.example.AppIronLotus;

import javax.swing.*;
import java.awt.*;

public class AppIronLotusApplication {

	public static void main(String[] args) {
		// Проверка на доступность графического интерфейса
		if (GraphicsEnvironment.isHeadless()) {
			System.out.println("Графический интерфейс не доступен. Приложение работает в headless режиме.");
		} else {
			SwingUtilities.invokeLater(() -> LoginWindow.createAndShowLoginGUI(null));
		}
	}
}
