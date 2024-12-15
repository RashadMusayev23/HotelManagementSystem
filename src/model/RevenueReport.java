package model;

public class RevenueReport {
    private String category;
    private double totalRevenue;

    public RevenueReport(String category, double totalRevenue) {
        this.category = category;
        this.totalRevenue = totalRevenue;
    }

    public String getCategory() { return category; }
    public double getTotalRevenue() { return totalRevenue; }
}