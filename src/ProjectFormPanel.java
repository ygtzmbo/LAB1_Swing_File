import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProjectFormPanel extends JPanel {
    private JTextField projectNameField;
    private JTextField teamLeaderField;
    private JComboBox<String> teamSizeCombo;
    private JComboBox<String> projectTypeCombo;
    private JTextField startDateField;
    private JButton saveButton;
    private JButton clearButton;
    

    public ProjectFormPanel() {
        setLayout(new GridLayout(6, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        
        add(new JLabel("Project Name:"));
        projectNameField = new JTextField();
        add(projectNameField);

        add(new JLabel("Team Leader:"));
        teamLeaderField = new JTextField();
        add(teamLeaderField);

        add(new JLabel("Team Size:"));
        String[] teamSizes = {"1-3", "4-6", "7-10", "10+"};
        teamSizeCombo = new JComboBox<>(teamSizes);
        add(teamSizeCombo);

        
        add(new JLabel("Project Type:"));
        String[] projectTypes = {"Web", "Mobile", "Desktop", "API"};
        projectTypeCombo = new JComboBox<>(projectTypes);
        add(projectTypeCombo);

        add(new JLabel("Start Date (DD/MM/YYYY):"));
        startDateField = new JTextField();
        add(startDateField);

        
        add(new JLabel()); 
        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Save");
        clearButton = new JButton("Clear");
        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);
        add(buttonPanel);

        
        saveButton.addActionListener(new SaveButtonListener());
        clearButton.addActionListener(new ClearButtonListener());
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String projectName = projectNameField.getText();
            String teamLeader = teamLeaderField.getText();
            String teamSize = (String) teamSizeCombo.getSelectedItem();
            String projectType = (String) projectTypeCombo.getSelectedItem();
            String startDate = startDateField.getText();

            
            if (projectName.isEmpty() || teamLeader.isEmpty() || startDate.isEmpty()) {
                JOptionPane.showMessageDialog(ProjectFormPanel.this,
                        "Please fill in all fields.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("projects.txt", true));
                
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String recordTime = now.format(formatter);

                writer.write("=== Project Entry=== \n");
                writer.write("Project Name : " + projectName + "\n");
                writer.write("Team Leader : " + teamLeader + "\n");
                writer.write("Team Size : " + teamSize + "\n");
                writer.write("Project Type : " + projectType + "\n");
                writer.write("Start Date : " + startDate + "\n");
                writer.write("Record Time : " + recordTime + "\n");
                writer.write("====================\n\n");
                writer.close();

                JOptionPane.showMessageDialog(ProjectFormPanel.this,
                        "Project saved ",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(ProjectFormPanel.this,
                        "Error saving to file: " + ex.getMessage(),
                        "File Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            projectNameField.setText("");
            teamLeaderField.setText("");
            teamSizeCombo.setSelectedIndex(0);
            projectTypeCombo.setSelectedIndex(0);
            startDateField.setText("");
        }
    }
}
