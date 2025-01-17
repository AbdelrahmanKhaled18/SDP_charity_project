package model;

import model.DesignPatterns.adapter.EmailAdapter;
import model.DesignPatterns.adapter.EmailInterface;
import model.DesignPatterns.observer.IObserver;
import model.DesignPatterns.observer.ISubject;
import model.DesignPatterns.state.*;

import java.util.concurrent.*;
import java.util.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;


public class Campaign extends Entity implements ISubject {
    private String title;
    private String description;
    private double goalAmount;
    private double collectedAmount;
    private Date startDate;
    private Date endDate;

    private CampaignState campaignState;
    private ArrayList<IObserver> observers;
    private Staff creator;

    private ScheduledExecutorService scheduler;

    public Campaign(Staff creator, String title, String description, double goalAmount, Date startDate, Date endDate) {
        observers = new ArrayList<>();
        this.creator = creator;
        this.title = title;
        this.description = description;
        this.goalAmount = goalAmount;
        this.collectedAmount = 0;
        this.startDate = startDate;
        this.endDate = endDate;
        this.campaignState = new InitiateCampaignState();
        scheduler = Executors.newScheduledThreadPool(1);
    }

    public Campaign(int id, Staff creator, String title, String description, double goalAmount, Date startDate, Date endDate) {
        this(creator, title, description, goalAmount, startDate, endDate);
        setId(id);
    }

    public Campaign(int id, Staff creator, String title, String description, double goalAmount, Date startDate, Date endDate,
                    double collectedAmount, String status) {
        this(id, creator, title, description, goalAmount, startDate, endDate);
        this.collectedAmount = collectedAmount;
        this.campaignState = map(status);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(double goalAmount) {
        this.goalAmount = goalAmount;
    }

    public double getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(double collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCampaignState() {
        return map(campaignState);
    }

    public void setCampaignState(CampaignState campaignState) {
        this.campaignState = campaignState;
        Campaign.updateCampaign(this);
    }

    public ArrayList<IObserver> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<IObserver> observers) {
        this.observers = observers;
    }

    public Staff getCreator() {
        return creator;
    }

    public void setCreator(Staff creator) {
        this.creator = creator;
    }

    public void registerObservers(IObserver observer) {
        observers.add(observer);
    }

    public void removeObservers(IObserver observer) {
        int index = observers.indexOf(observer);
        if (index >= 0) {
            observers.remove(observer);
        }
    }

    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(collectedAmount);
        }
    }


    public void addDonation(double amount) {
        this.collectedAmount += amount;
        Campaign.updateCampaign(this);
    }

    public void executeHandleState() {
        campaignState.NextState(this);
    }

    public CampaignState map(String state) {
        return switch (state) {
            case "InitiateCampaignState" -> new InitiateCampaignState();
            case "PendingAcceptanceState" -> new PendingAcceptanceState();
            case "PlannedState" -> new PlannedState();
            case "ActivateState" -> new ActivateState();
            case "CompletedState" -> new CompletedState();
            default -> null;
        };
    }

    public String map(CampaignState state) {
        return switch (state) {
            case InitiateCampaignState initiateCampaignState -> "InitiateCampaignState";
            case PendingAcceptanceState pendingAcceptanceState -> "PendingAcceptanceState";
            case PlannedState plannedState -> "PlannedState";
            case ActivateState activateState -> "ActivateState";
            case CompletedState completedState -> "CompletedState";
            case null, default -> null;
        };
    }


    public void start() {
        scheduler.scheduleAtFixedRate(this::run, 0, 1, TimeUnit.SECONDS);
        this.executeHandleState();
    }

    public void stop() {
        scheduler.shutdown();
    }

    // This function continuously updates the collectedAmount variable from the database
    // Then it updates observers with the collected amount
    public void run() {
        String command = "SELECT collected_amount FROM campaign WHERE id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, this.getId());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                this.collectedAmount = rs.getDouble("collected_amount");
                notifyObservers();
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean setNotificationSent(int campaignId) {
        String command = "UPDATE campaign SET notification_sent=? WHERE id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setBoolean(1, true);
            statement.setInt(2, campaignId);
            boolean success = statement.executeUpdate() > 0;
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    private static boolean sendMailToVolunteers(int campaignId) {
        String command = "SELECT email, title, goal_amount FROM campaign_volunteers " +
                "INNER JOIN person ON person.id = volunteer_id " +
                "INNER JOIN campaign ON campaign.id = campaign_id " +
                "WHERE campaign_id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, campaignId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                double goalAmount = rs.getDouble("goal_amount");

                String emailTo = rs.getString("email");
                String subject = "Campaign Target Reached";
                String body = "We would like to notify you that campaign " + title +
                        " has reached its target amount of " + goalAmount +
                        ". Thank you for your contributions.";

                EmailInterface emailInterface = new EmailAdapter();
                emailInterface.sendEmail(emailTo, subject, body);
            }
            statement.close();
            return setNotificationSent(campaignId);
        } catch (SQLException e) {
            return false;
        }
    }


    private static void checkCampaignGoalReachedAndNotify(int campaignId) {
        String command = "SELECT collected_amount >= goal_amount AS target_reached, notification_sent FROM campaign WHERE id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, campaignId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                boolean notificationSent = rs.getBoolean("notification_sent");
                boolean targetReached = rs.getBoolean("target_reached");
                if (targetReached && !notificationSent) {
                    Campaign.sendMailToVolunteers(campaignId);
                }
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static boolean modifyCampaignCollectedAmountAndNotify(int campaignId, double amount) {
        String command = "UPDATE campaign SET collected_amount= collected_amount + ? WHERE id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setDouble(1, amount);
            statement.setInt(2, campaignId);
            boolean success = statement.executeUpdate() > 0;
            if (success) {
                Campaign.checkCampaignGoalReachedAndNotify(campaignId);
            }
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }


    public static boolean addCampaignVolunteer(int campaignId, int personId) {
        String command = "INSERT INTO campaign_volunteers (campaign_id, volunteer_id) VALUES (?, ?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, campaignId);
            statement.setInt(2, personId);
            boolean success = statement.executeUpdate() > 0;
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean createCampaign(Campaign campaign) {
        String command = "INSERT INTO campaign (`title`, `description`, `goal_amount`, " +
                "`collected_amount`, `start_date`, `end_date`, `status`, `creator`) VALUES(?,?,?,?,?,?,?,?)";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, campaign.getTitle());
            statement.setString(2, campaign.getDescription());
            statement.setDouble(3, campaign.getGoalAmount());
            statement.setDouble(4, campaign.getCollectedAmount());
            statement.setDate(5, new java.sql.Date(campaign.getStartDate().getTime()));
            statement.setDate(6, new java.sql.Date(campaign.getEndDate().getTime()));
            statement.setString(7, campaign.getCampaignState());
            statement.setInt(8, campaign.getCreator().getId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            boolean success = false;
            if (rs.next()) {
                campaign.setId(rs.getInt(1));
                success = true;
            }
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateCampaign(Campaign campaign) {
        String command = "UPDATE campaign SET title=?, description=?, goal_amount=?," +
                "collected_amount=?, start_date=?, end_date=?, status=?, creator=? WHERE id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setString(1, campaign.getTitle());
            statement.setString(2, campaign.getDescription());
            statement.setDouble(3, campaign.getGoalAmount());
            statement.setDouble(4, campaign.getCollectedAmount());
            statement.setDate(5, new java.sql.Date(campaign.getStartDate().getTime()));
            statement.setDate(6, new java.sql.Date(campaign.getEndDate().getTime()));
            statement.setString(7, campaign.getCampaignState());
            statement.setInt(8, campaign.getCreator().getId());
            statement.setInt(9, campaign.getId());
            boolean success = statement.executeUpdate() > 0;
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean deleteCampaign(int campaignId) {
        String command = "DELETE FROM campaign WHERE id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, campaignId);
            boolean success = statement.executeUpdate() > 0;
            statement.close();
            return success;
        } catch (SQLException e) {
            return false;
        }
    }

    public static Campaign retrieveCampaign(int campaignId) {
        String command = "SELECT * FROM campaign WHERE id = ?";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            statement.setInt(1, campaignId);
            ResultSet rs = statement.executeQuery();
            ArrayList<Campaign> campaignList = getCampaignFromRS(rs);
            Campaign campaign = null;
            if (!campaignList.isEmpty())
                campaign = campaignList.getFirst();
            statement.close();
            return campaign;
        } catch (SQLException e) {
            return null;
        }
    }

    public static ArrayList<Campaign> retrieveAllCampaigns() {
        String command = "SELECT * FROM campaign";
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(command);
            ResultSet rs = statement.executeQuery();
            ArrayList<Campaign> campaigns = getCampaignFromRS(rs);
            statement.close();
            return campaigns;
        } catch (SQLException e) {
            return null;
        }
    }


    private static ArrayList<Campaign> getCampaignFromRS(ResultSet rs) throws SQLException {
        ArrayList<Campaign> campaigns = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            double goalAmount = rs.getDouble("goal_amount");
            double collectedAmount = rs.getDouble("collected_amount");
            Date startDate = new Date(rs.getDate("start_date").getTime());
            Date endDate = new Date(rs.getDate("end_date").getTime());
            String status = rs.getString("status");

            int creatorId = rs.getInt("creator");
            Staff creator = Staff.retrieveStaff(creatorId);
            campaigns.add(new Campaign(id, creator, title, description, goalAmount, startDate, endDate, collectedAmount, status));
        }
        return campaigns;
    }
}
