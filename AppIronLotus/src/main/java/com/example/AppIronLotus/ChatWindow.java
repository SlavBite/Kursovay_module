package com.example.AppIronLotus;

import com.example.AppIronLotus.DatabaseHelper;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChatWindow {

    public static void openChatWindow(int currentUserId, int receiverId) {
        JFrame frame = new JFrame("Chat с " + DatabaseHelper.getUserName(receiverId));
        frame.setSize(350, 620);

        ImageIcon icon = new ImageIcon("images/logo.png");
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("Ошибка загрузки изображения");
        } else {
            frame.setIconImage(icon.getImage());
        }
        Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);

        frame.setIconImage(scaledIcon.getImage());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Столбец 0
        gbc.gridy = 0; // Строка 0
        gbc.anchor = GridBagConstraints.CENTER; // Центрируем по обеим осям
        gbc.insets = new Insets(10, 10, 10, 10); // Отступы от краев панели

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

        // Панель для чата

        JPanel MessageBox = new JPanel(new BorderLayout());
        MessageBox.setPreferredSize(new Dimension(frame.getWidth(), (int) (frame.getHeight() * 0.8))); // 70% от высоты окна



        JPanel SendBox = new JPanel();
        SendBox.setLayout(new BoxLayout(SendBox, BoxLayout.X_AXIS));
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Центрируем кнопку
        backButtonPanel.setOpaque(false);

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        // Загружаем сообщения из базы данных
        List<String> messages = DatabaseHelper.getMessagesBetweenUsers(currentUserId, receiverId);
        for (String message : messages) {
            chatArea.append(message + "\n");
        }

        JTextField messageField = new JTextField(20); // Указываем желаемую ширину для поля ввода
        messageField.setPreferredSize(new Dimension(200, 30));


        JButton sendButton = new JButton("Send");
        sendButton.setPreferredSize(new Dimension(80, 40));
        sendButton.setOpaque(true);
        sendButton.setFont(customFont);
        sendButton.setBorder(new LineBorder(new Color(0, 0, 0), 2, false    ));
        sendButton.setBackground(new Color(207, 180, 150));
        sendButton.setForeground(new Color(22, 17, 14));
        sendButton.setFocusPainted(false);


        sendButton.addActionListener(e -> {
            String message = messageField.getText();
            sendMessage(currentUserId, receiverId, message);
            messageField.setText("");
            chatArea.append("You: " + message + "\n");
        });

        MessageBox.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        SendBox.add(messageField);
        SendBox.add(Box.createHorizontalStrut(10)); // Добавляем небольшой отступ между полем ввода и кнопкой
        SendBox.add(sendButton);

        // Кнопка "Назад"


        JButton backToUserListButton = new JButton("Вернуться");
        backToUserListButton.setOpaque(true);
        backToUserListButton.setPreferredSize(new Dimension(100, 50));
        backToUserListButton.setFont(customFont);
        backToUserListButton.setBorder(new LineBorder(new Color(0, 0, 0), 3, false    ));
        backToUserListButton.setBackground(new Color(207, 180, 150));
        backToUserListButton.setForeground(new Color(22, 17, 14));
        backToUserListButton.setFocusPainted(false);
        backToUserListButton.addActionListener(e -> {
            // Закрыть текущее окно чата
            frame.dispose();
            // Вернуться в окно со списком пользователей
            FriendsListWindow.showFriendsListWindow(currentUserId); // Открытие списка пользователей
        });

        backButtonPanel.add(backToUserListButton);



        frame.add(MessageBox, BorderLayout.NORTH);
        frame.add(SendBox, BorderLayout.CENTER);
        frame.add(backButtonPanel, BorderLayout.SOUTH);
        frame.setBackground(new Color(36, 33, 33));
        frame.setVisible(true);

        // Добавляем таймер для обновления сообщений
        javax.swing.Timer timer = new javax.swing.Timer(3000, e -> updateChatArea(chatArea, currentUserId, receiverId));
        timer.start(); // Запуск таймера
    }

    private static void updateChatArea(JTextArea chatArea, int currentUserId, int receiverId) {
        List<String> messages = DatabaseHelper.getMessagesBetweenUsers(currentUserId, receiverId);
        chatArea.setText(""); // Очистить чат перед добавлением новых сообщений
        for (String message : messages) {
            chatArea.append(message + "\n");
        }
    }

    // Метод для отправки сообщения в базу данных
    private static void sendMessage(int senderId, int receiverId, String message) {
        String query = "INSERT INTO messages (sender_id, receiver_id, message) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, senderId);
            statement.setInt(2, receiverId);
            statement.setString(3, message);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
