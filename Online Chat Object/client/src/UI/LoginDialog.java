package client.UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDialog implements ActionListener {
    private JDialog dialog;
    private JTextField nicknameField;
    private JTextField portField;
    private UserInterface ui;

    public LoginDialog(JFrame window, UserInterface ui){
        this.ui = ui;
        dialog = new JDialog(window, true);
        dialog.setTitle("Log in");
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));

        JPanel parameterPane = new JPanel();
        parameterPane.setLayout(new BoxLayout(parameterPane, BoxLayout.X_AXIS));

        JTextField nickname = new JTextField("Nickname:");
        nickname.setEditable(false);
        nicknameField = new JTextField(15);
        parameterPane.add(nickname);
        parameterPane.add(nicknameField);

        JTextField port = new JTextField("Port:");
        port.setEditable(false);
        portField = new JTextField(5);
        parameterPane.add(port);
        parameterPane.add(portField);

        JButton button = new JButton("Log in");
        button.setActionCommand("Login");
        button.addActionListener(this);

        mainPane.add(parameterPane);
        mainPane.add(button);
        dialog.setContentPane(mainPane);
        dialog.pack();
        dialog.setBounds(window.getX() + 20, window.getY() + 20, dialog.getWidth(), 100);
    }

    public void showDialog(){
        dialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nickname = nicknameField.getText();
        int port;
        try {
            port = Integer.parseInt(portField.getText());
        }catch (NumberFormatException e1){
            JOptionPane.showMessageDialog(dialog, "Wrong port format", "Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        SwingUtilities.invokeLater(()->ui.logIn(nickname, port));
        dialog.dispose();
    }
}
