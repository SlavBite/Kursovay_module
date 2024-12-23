package com.example.AppIronLotus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class LoginWindow {

    public static void createAndShowLoginGUI(JFrame parentFrame) {
        // Создание окна авторизации
        JFrame frame = new JFrame("Авторизация");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 620);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Получаем размер экрана
        int x = (screenSize.width - frame.getWidth()) / 2; // Вычисляем координату X для центра
        int y = (screenSize.height - frame.getHeight()) / 2; // Вычисляем координату Y для центра
        frame.setLocation(x, y);


        // Настройка GridBagLayout для центровки
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Отступы

        ImageIcon icon = new ImageIcon("images/logo.png");
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("Ошибка загрузки изображения");
        } else {
            frame.setIconImage(icon.getImage());
        }
        Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);

        frame.setIconImage(scaledIcon.getImage());

        // Создание главной панели с вертикальной компоновкой (для центровки элементов)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Добавление отступов

        JPanel TitleAndLogo = new JPanel(new GridBagLayout());
        // Создание лого
        JLabel logo = new JLabel(scaledIcon);

        // Создание заголовка
        JLabel titleLabel = new JLabel("IronLotus", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        TitleAndLogo.add(titleLabel, gbc);
        gbc.gridx++; // Переход к следующей строке
        //TitleAndLogo.add(logo, gbc);

        // Панель для ввода данных
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);



        // Поле для ввода имени пользователя
        JTextField userField = new JTextField("Enter Username", 15);
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        userField.setForeground(Color.GRAY);

        // Слушатель фокуса для поля ввода имени пользователя
        userField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (userField.getText().equals("Enter Username")) {
                    userField.setText("");
                    userField.setForeground(Color.BLACK); // Цвет текста при вводе
                }
            }

            public void focusLost(FocusEvent e) {
                if (userField.getText().isEmpty()) {
                    userField.setText("Enter Username");
                    userField.setForeground(Color.GRAY); // Цвет подсказки
                }
            }
        });

        // Поле для ввода пароля
        JPasswordField passField = new JPasswordField("Enter Password", 15);
        passField.setFont(new Font("Arial", Font.PLAIN, 14));
        passField.setForeground(Color.GRAY);

        // Слушатель фокуса для поля ввода пароля
        passField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (new String(passField.getPassword()).equals("Enter Password")) {
                    passField.setText("");
                    passField.setForeground(Color.BLACK); // Цвет текста при вводе
                }
            }

            public void focusLost(FocusEvent e) {
                if (new String(passField.getPassword()).isEmpty()) {
                    passField.setText("Enter Password");
                    passField.setForeground(Color.GRAY); // Цвет подсказки
                }
            }
        });

        // Размещение полей ввода на панели
        inputPanel.add(userField, gbc);
        gbc.gridy++; // Переход к следующей строке
        inputPanel.add(passField, gbc);

        // Кнопка входа
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 123, 255)); // Голубой цвет кнопки
        loginButton.setForeground(Color.WHITE);

        // Обработчик события для кнопки входа
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (DatabaseHelper.authenticateUser(username, password)) {
                int userId = DatabaseHelper.getUserId(username);
                MainMenuWindow.createAndShowMainMenu(userId); // Вызов метода из MainMenu после успешной авторизации
                frame.dispose(); // Закрываем окно авторизации
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Добавление кнопки на панель
        gbc.gridy++; // Переход к следующей строке
        inputPanel.add(loginButton, gbc);






        mainPanel.add(TitleAndLogo);
        mainPanel.add(Box.createVerticalStrut(30)); // Отступ между заголовком и формой
        mainPanel.add(inputPanel);

        // Добавление главной панели в основное окно
        frame.add(mainPanel);

        // Отображение окна
        frame.setVisible(true);
    }
}
