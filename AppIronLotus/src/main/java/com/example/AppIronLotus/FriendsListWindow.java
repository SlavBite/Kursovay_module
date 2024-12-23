package com.example.AppIronLotus;
import com.example.AppIronLotus.ChatWindow;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FriendsListWindow {

    // Метод для отображения окна со списком друзей
    public static void showFriendsListWindow(int userId) {
        JFrame friendsListFrame = new JFrame("Friends List");
        friendsListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        friendsListFrame.setSize(350, 620);

        ImageIcon icon = new ImageIcon("images/logo.png");
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("Ошибка загрузки изображения");
        } else {
            friendsListFrame.setIconImage(icon.getImage());
        }
        Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);

        friendsListFrame.setIconImage(scaledIcon.getImage());

        // Рассчитываем позицию по центру экрана
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - friendsListFrame.getWidth()) / 2;
        int y = (screenSize.height - friendsListFrame.getHeight()) / 2;
        friendsListFrame.setLocation(x, y);

        Font FriendFont = null;
        // Загружаем шрифт
        try {
            // Указываем путь до шрифта
            File fontFile = new File("fonts/JihoSoft.otf");  // Путь до шрифта в папке
            FriendFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(32f);  // Устанавливаем размер шрифта
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(FriendFont);  // Регистрируем шрифт в системе
        } catch (FontFormatException | IOException e) {
            e.printStackTrace(); // Обработка ошибок при загрузке шрифта
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

        // Панель для списка друзей
        JPanel friendsListPanel = new JPanel();
        friendsListPanel.setLayout(new BoxLayout(friendsListPanel, BoxLayout.Y_AXIS));


        List<String> friends = DatabaseHelper.getAllUsers(userId); // Получаем список друзей из базы данных

        // Для каждого друга создаем элемент в списке
        for (String friend : friends) {
            JPanel friendPanel = new JPanel();
            friendPanel.setLayout(new BorderLayout());

            JLabel friendLabel = new JLabel(friend); // Отображаем имя друга
            friendLabel.setOpaque(true);
            friendLabel.setPreferredSize(new Dimension(100, 50));
            friendLabel.setFont(FriendFont);
            friendLabel.setBorder(new LineBorder(new Color(0, 0, 0), 3, false    ));
            friendLabel.setBackground(new Color(207, 180, 150));
            friendLabel.setForeground(new Color(22, 17, 14));

            friendLabel.setHorizontalAlignment(SwingConstants.CENTER);
            friendLabel.setVerticalAlignment(SwingConstants.CENTER);

            JButton startChatButton = new JButton("Начать чат");
            startChatButton.setOpaque(true);
            startChatButton.setPreferredSize(new Dimension(100, 50));
            startChatButton.setFont(customFont);
            startChatButton.setBorder(new LineBorder(new Color(0, 0, 0), 3, false    ));
            startChatButton.setBackground(new Color(207, 180, 150));
            startChatButton.setForeground(new Color(22, 17, 14));
            startChatButton.setFocusPainted(false);

            // При нажатии на кнопку открывается окно чата
            startChatButton.addActionListener(e -> {
                int receiverId = DatabaseHelper.getUserIdByName(friend);
                ChatWindow.openChatWindow(userId, receiverId); // Открыть чат с другом
                friendsListFrame.dispose(); // Закрыть окно списка друзей
            });

            friendPanel.add(friendLabel, BorderLayout.CENTER);
            friendPanel.add(startChatButton, BorderLayout.EAST);

            friendPanel.setPreferredSize(new Dimension(0, 50)); // Высота 50, ширина будет растягиваться


            friendsListPanel.add(friendPanel);
        }

        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Центрируем кнопку
        backButtonPanel.setOpaque(false);


        // Кнопка "Назад"
        JButton backButton = new JButton("Вернуться");
        backButton.setOpaque(true);
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.setFont(customFont);
        backButton.setBorder(new LineBorder(new Color(0, 0, 0), 3, false    ));
        backButton.setBackground(new Color(207, 180, 150));
        backButton.setForeground(new Color(22, 17, 14));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            friendsListFrame.dispose(); // Закрыть окно списка друзей
            MainMenuWindow.createAndShowMainMenu(userId); // Открыть главное меню
        });

        backButtonPanel.add(backButton);

        // Добавляем панель с друзьями в основное окно
        friendsListPanel.add(backButtonPanel);
        friendsListPanel.setBackground(new Color(36, 33, 33));
        friendsListFrame.add(new JScrollPane(friendsListPanel));
        friendsListFrame.setVisible(true);
    }


}
