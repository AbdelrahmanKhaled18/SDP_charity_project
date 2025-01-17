package model;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;


public class Aid {
    private String type;
    private double value;
    private Date date;
    private String campaignId;
    private String beneficiaryId;
    private String status;

    public Aid(String type, double value, Date date, String campaignId, String beneficiaryId, String status) {
        this.type = type;
        this.value = value;
        this.date = date;
        this.campaignId = campaignId;
        this.beneficiaryId = beneficiaryId;
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static boolean addAid(Aid aid) {
        String query = "INSERT INTO aids (type, value, date, campaignId, beneficiaryId, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, aid.getType());
            stmt.setDouble(2, aid.getValue());
            stmt.setDate(3, new java.sql.Date(aid.getDate().getTime()));
            stmt.setString(4, aid.getCampaignId());
            stmt.setString(5, aid.getBeneficiaryId());
            stmt.setString(6, aid.getStatus());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Aid> getAidByCampaign(String campaignId) {
        List<Aid> aids = new ArrayList<>();
        String query = "SELECT * FROM aids WHERE campaignId = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, campaignId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Aid aid = new Aid(
                        rs.getString("type"),
                        rs.getDouble("value"),
                        rs.getDate("date"),
                        rs.getString("campaignId"),
                        rs.getString("beneficiaryId"),
                        rs.getString("status")
                );
                aids.add(aid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aids;
    }

    public static List<Aid> getAidByBeneficiary(String beneficiaryId) {
        List<Aid> aids = new ArrayList<>();
        String query = "SELECT * FROM aids WHERE beneficiaryId = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, beneficiaryId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Aid aid = new Aid(
                        rs.getString("type"),
                        rs.getDouble("value"),
                        rs.getDate("date"),
                        rs.getString("campaignId"),
                        rs.getString("beneficiaryId"),
                        rs.getString("status")
                );
                aids.add(aid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aids;
    }
}
