package com.todolist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class TaskApp {
    private JFrame frame;
    private JTextField taskField;
    private JList<Task> taskList;
    private DefaultListModel<Task> listModel;
    private TaskDAO taskDAO;

    public TaskApp() {
        taskDAO = new TaskDAOImpl();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        taskField = new JTextField();
        panel.add(taskField, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(taskList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add");
        addButton.addActionListener(this::addTask);
        buttonPanel.add(addButton);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(this::updateTask);
        buttonPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this::deleteTask);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        loadTasks();
    }

    private void loadTasks() {
        listModel.clear();
        List<Task> tasks = taskDAO.getAllTasks();
        for (Task task : tasks) {
            listModel.addElement(task);
        }
    }

    private void addTask(ActionEvent e) {
        String description = taskField.getText();
        if (!description.isEmpty()) {
            Task task = new Task(0, description, false);
            taskDAO.addTask(task);
            loadTasks();
            taskField.setText("");
        }
    }

    private void updateTask(ActionEvent e) {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask != null) {
            String newDescription = taskField.getText();
            if (!newDescription.isEmpty()) {
                selectedTask.setDescription(newDescription);
                taskDAO.updateTask(selectedTask);
                loadTasks();
                taskField.setText("");
            }
        }
    }

    private void deleteTask(ActionEvent e) {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask != null) {
            taskDAO.deleteTask(selectedTask.getId());
            loadTasks();
        }
    }
}
