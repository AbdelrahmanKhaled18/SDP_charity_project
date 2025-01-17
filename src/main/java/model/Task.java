package model;

import java.sql.*;
import java.util.ArrayList;

public class Task extends Entity{
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task(int id, String name, String description) {
        this(name, description);
        setId(id);
    }


    public static boolean createTask(Task task) {
        String command = "INSERT INTO task (`name`, `description`) VALUES(?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            boolean success = false;
            if (rs.next()) {
                task.setId(rs.getInt(1));
                success = true;
            }
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateTask(Task task) {
        String command = "UPDATE task SET name=?, description= ? WHERE id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.setInt(3, task.getId());
            boolean success = statement.executeUpdate() > 0;
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean deleteTask(int taskId) {
        String command = "DELETE FROM task WHERE id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, taskId);
            boolean success = statement.executeUpdate() > 0;
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static Task retrieveTask(int taskId) {
        String command = "SELECT * FROM task WHERE id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, taskId);
            ResultSet rs = statement.executeQuery();
            Task task = null;
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                task = new Task(id, name, description);
            }
            statement.close();
            return task;
        } catch (SQLException e) {
            return null;
        }
    }

    public static ArrayList<Task> retrievePersonTasks(int personId) {
        String command = "SELECT task.id AS id, task.name AS name, task.description AS description " +
                "FROM assigned_tasks INNER JOIN task ON assigned_tasks.task_id = task.id " +
                "WHERE assigned_tasks.person_id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, personId);
            ResultSet rs = statement.executeQuery();
            ArrayList<Task> tasks = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                tasks.add(new Task(id, name, description));
            }
            statement.close();
            return tasks;
        } catch (SQLException e) {
            return null;
        }
    }

    public static ArrayList<Task> retrieveAllTasks() {
        String command = "SELECT * FROM task";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet rs = statement.executeQuery();
            ArrayList<Task> tasks = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                tasks.add(new Task(id, name, description));
            }
            statement.close();
            return tasks;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
