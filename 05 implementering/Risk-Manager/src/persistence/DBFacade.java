package persistence;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ui.*;

import java.sql.*;


public class DBFacade {

    public static boolean conneced = false;
    public static Statement stmt;

    public static void initializeDB() throws SQLException {

        String password = LoginWindowController.pass;
        String username = LoginWindowController.user;

        try {
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/risk-manager?serverTimezone=UTC", username, password);
        System.out.println("Database connected.");

        conneced = true;
        // Create a statement
        stmt = connection.createStatement();
        } catch (Exception e) {
            System.out.println("Kan ikke logge ind.");
        }

    }

    public void linkStrategy(Integer strategyID, String last) {
        String query = "UPDATE risk set strategy = '"+strategyID+"' where description = '"+last+"'";
        executeQuery(query);
    }

    public void updateRisk(String description, Double probability, Double consequence, Double exposure, String last) {
        String query = "UPDATE risk set description = '"+description+"', " +
                "probability = '"+probability+"', " +
                "consequence = '"+consequence+"', " +
                "exposure = '"+exposure+"' where description = '"+last+"'";
        executeQuery(query);
    }

    public void updateStrategy(String name, String category, String description, String last) {
        String query = "UPDATE strategy set name = '"+name+"', " +
                "category = '"+category+"', " +
                "description = '"+description+"' where name = '"+last+"'";
        executeQuery(query);
    }

    public void deleteRisk(String selected) {
        String query = "DELETE FROM risk WHERE description like '"+selected+"'";
        executeQuery(query);
    }

    public void deleteStrategy(String selected) {
        String query = "DELETE FROM strategy WHERE name like '"+selected+"'";
        executeQuery(query);
    }

    public void insertRisk(String description, Double probability, Double consequence, Double exposure, Boolean strategy) {
        String query = "insert into risk (description, probability, consequence, exposure) values ('"+description+"','"+probability+"','"+consequence+"','"+exposure+"')";

        executeQuery(query);
    }

    public void insertStrategy(String name, String description, String category) {
        String query = "insert into strategy (name, description, category, strategyID) values ('"+name+"','"+description+"','"+category+"','"+ StrategyWindowController.numStrategies+"')";

        executeQuery(query);
    }

    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/risk-manager?serverTimezone=UTC",LoginWindowController.user,LoginWindowController.pass);
            return conn;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<RiskUI> getRisksList(){
        ObservableList<RiskUI> riskList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM risk";
        Statement st;
        ResultSet rs;

        try {
            boolean hasStrategy;
            st = connection.createStatement();
            rs = st.executeQuery(query);
            RiskUI risks;

            while(rs.next()) {
                if (rs.getInt("strategy") != 0)
                    hasStrategy = true;
                else
                    hasStrategy = false;
                risks = new RiskUI(MainWindowController.numRisks,rs.getString("description"),rs.getDouble("probability"),rs.getDouble("consequence"),rs.getDouble("exposure"), hasStrategy, rs.getInt("strategy"));
                riskList.add(risks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return riskList;
    }

    public ObservableList<StrategyUI> getStrategyList(){
        ObservableList<StrategyUI> strategyList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM strategy";
        Statement st;
        ResultSet rs;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            StrategyUI strategies;
            while(rs.next()) {
                strategies = new StrategyUI(rs.getString("name"),rs.getString("description"),rs.getString("category"),rs.getInt("strategyID"));
                strategyList.add(strategies);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strategyList;
    }

    public String getSelectedRiskStrategy(Integer selected){
        Connection connection = getConnection();
        String query = "SELECT description FROM strategy where strategyID ="+selected;
        Statement st;
        ResultSet rs;
        String description = "";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()) {

                description = rs.getString("description");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return description;
    }
}
