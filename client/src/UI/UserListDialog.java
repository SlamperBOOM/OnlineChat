package client.UI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class UserListDialog {
    private JDialog dialog;
    private JPanel table;

    public UserListDialog(JFrame window){
        dialog = new JDialog(window, false);
        dialog.setBounds(window.getX() + 50, window.getY() + 50, 200, 300);
        dialog.setTitle("Users");
    }

    public void createDialogView(List<String> users){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        table = new JPanel(new GridLayout(users.size(), 2));
        for(int i=0; i< users.size(); ++i){
            JLabel title = new JLabel("User " + (i+1) + " nickname");
            title.setVisible(true);
            title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            table.add(title);

            JLabel name = new JLabel(users.get(i));
            name.setVisible(true);
            name.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            table.add(name);
        }
        table.setVisible(true);
        panel.add(table);

        JButton button = new JButton("Ok");
        button.addActionListener(e -> dialog.dispose());
        panel.add(button);
        dialog.setContentPane(panel);
    }

    public void showDialog(){
        dialog.pack();
        dialog.setBounds(dialog.getX(), dialog.getY(), dialog.getWidth()+50, dialog.getHeight() + 50);
        dialog.setVisible(true);
    }
}
