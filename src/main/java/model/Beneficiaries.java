package model;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class Beneficiaries {

    private int id;
    private String name;
    private String contactInfo;
    private String address;
    private List<String> needs;
    private List<Aid> receivedAid;

    public Beneficiaries(String name, String contactInfo, String address) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.address = address;
        this.needs = new ArrayList<>();
        this.receivedAid = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNeeds(List<String> needs) {
        this.needs = needs;
    }

    public void setReceivedAid(List<Aid> receivedAid) {
        this.receivedAid = receivedAid;
    }

    public void addNeed(String need) {
        String query = "INSERT INTO needs (beneficiary_id, need) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.id);
            stmt.setString(2, need);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addReceivedAid(Aid aid) {
        String query = "INSERT INTO aids (beneficiary_id, type, value, date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.id);
            stmt.setString(2, aid.getType());
            stmt.setDouble(3, aid.getValue());
            stmt.setDate(4, new java.sql.Date(aid.getDate().getTime()));
            stmt.setString(5, aid.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getNeeds() {
        List<String> needsList = new ArrayList<>();
        String query = "SELECT need FROM needs WHERE beneficiary_id = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                needsList.add(rs.getString("need"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return needsList;
    }

    public List<Aid> getReceivedAids() {
        List<Aid> aids = Aid.getAidByBeneficiary(String.valueOf(this.id));
        return aids;
    }

}
