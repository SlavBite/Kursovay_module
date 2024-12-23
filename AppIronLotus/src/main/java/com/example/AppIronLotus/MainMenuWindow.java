package com.example.AppIronLotus;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainMenuWindow {

    // Метод для отображения главного меню
    public static void createAndShowMainMenu(int userId) {
        // Создание окна главного меню

        JFrame mainMenuFrame = new JFrame("Main Menu");
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setSize(350, 620);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Получаем размер экрана
        int x = (screenSize.width - mainMenuFrame.getWidth()) / 2; // Вычисляем координату X для центра
        int y = (screenSize.height - mainMenuFrame.getHeight()) / 2; // Вычисляем координату Y для центра
        mainMenuFrame.setLocation(x, y);

        // Устанавливаем иконку окна
        ImageIcon icon = new ImageIcon("images/logo.png");
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("Ошибка загрузки изображения");
        } else {
            mainMenuFrame.setIconImage(icon.getImage());
        }

        Font customFont = null;
        // Загружаем шрифт
        try {
            // Указываем путь до шрифта
            File fontFile = new File("fonts/JihoSoft.otf");  // Путь до шрифта в папке
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(14f);  // Устанавливаем размер шрифта
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);  // Регистрируем шрифт в системе
        } catch (FontFormatException | IOException e) {
            e.printStackTrace(); // Обработка ошибок при загрузке шрифта
        }


        // Создание основной панели с фоном
        JPanel backgroundPanel = new JPanel();

        backgroundPanel.setLayout(new BorderLayout()); // Используем BorderLayout для размещения элементов

        // Изменяем цвет фона для панели
        backgroundPanel.setBackground(new Color(36, 36, 33)); // Легкий голубой цвет (RGB)

        // Панель для содержимого главного меню (центрируем элементы)
        JPanel mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel, BoxLayout.Y_AXIS));
        mainMenuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuPanel.setBackground(new Color(36, 36, 33));

        // Кнопка для старта чата
        JButton chatButton = new JButton("Друзья");
        chatButton.setPreferredSize(new Dimension(100, 50));
        chatButton.setFont(customFont);
        chatButton.setBorder(new LineBorder(new Color(138, 67, 7), 3, true));
        chatButton.setBackground(new Color(106, 0, 0));
        chatButton.setForeground(new Color(252, 199, 0));
        chatButton.setFocusPainted(false);
        chatButton.addActionListener(e -> {
            // Логика для запуска чата
            FriendsListWindow.showFriendsListWindow(userId);
            mainMenuFrame.dispose(); // Закрываем главное меню
        });


        // Панель для нижней части с кнопкой
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Центрируем кнопку
        bottomPanel.setBackground(new Color(62, 0, 0)); // Цвет фона панели снизу

        // Добавляем кнопку на панель
        bottomPanel.add(chatButton);
        // Добавляем нижнюю панель с кнопкой на главную
        backgroundPanel.add(mainMenuPanel, BorderLayout.CENTER); // Центрируем содержимое
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH); // Нижняя панель с кнопкой

        // Устанавливаем фоновую панель в окно
        mainMenuFrame.setContentPane(backgroundPanel);

        // Устанавливаем видимость окна
        mainMenuFrame.setVisible(true);
    }
}
