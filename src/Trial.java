import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Trial {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        JPanel panel = new JPanel(new BorderLayout());
        JButton button1 = new JButton("But1");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hey1");
            }
        });
        JButton button2 = new JButton("But2");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hey2");
            }
        });
        panel.add(button1, BorderLayout.NORTH);
        panel.add(button2, BorderLayout.SOUTH);
        frame.setBounds(0, 0, 500, 500);

        String[] columns = {"a", "b", "c"};
        Object[][] data = {{"a", "b", "c"}, {"a", "b", "c"}, {"a", "b", "c"}, {"a", "b", "c"}, {"a", "b", "c"}, {"a", "b", "c"}, {"a", "b", "c"}, {"a", "b", "c"}};
        JTable table = new JTable(data, columns);
        table.setRowHeight(100);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        table.setFillsViewportHeight(true);

        frame.setContentPane(panel);
        frame.revalidate();
    }
}
