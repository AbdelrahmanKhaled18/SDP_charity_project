package model;

import java.sql.*;
import java.util.ArrayList;

public class Skill {
    private int id;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Skill(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Skill(int id, String name, String description) {
        this(name, description);
        this.id = id;
    }


    public static boolean createSkill(Skill skill) {
        String command = "INSERT INTO skill (`name`, `description`) VALUES(?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, skill.getName());
            statement.setString(2, skill.getDescription());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            boolean success = false;
            if (rs.next()) {
                skill.setId(rs.getInt(1));
            }
            rs.close();
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateSkill(Skill skill) {
        String command = "UPDATE skill SET name=?, description= ? WHERE id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setString(1, skill.getName());
            statement.setString(2, skill.getDescription());
            statement.setInt(3, skill.getId());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean deleteSkill(int skillId) {
        String command = "DELETE FROM skill WHERE id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, skillId);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static Skill retrieveSkill(int skillId) {
        String command = "SELECT * FROM skill WHERE id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, skillId);
            ResultSet rs = statement.executeQuery();
            Skill skill = null;
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                skill = new Skill(id, name, description);
            }
            rs.close();
            statement.close();
            return skill;
        } catch (SQLException e) {
            return null;
        }
    }

    public static ArrayList<Skill> retrieveAllSkills() {
        String command = "SELECT * FROM skill";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet rs = statement.executeQuery();
            ArrayList<Skill> skills = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                skills.add(new Skill(id, name, description));
            }
            rs.close();
            statement.close();
            return skills;
        } catch (SQLException e) {
            return null;
        }
    }
}
