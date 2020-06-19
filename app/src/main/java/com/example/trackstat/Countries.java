package com.example.trackstat;

public class Countries {
    public String countryName;
    public String totalCases;
    public String deaths;
    public String recovered;

    public Countries(String countryName, String totalCases, String deaths, String recovered) {
        this.countryName = countryName;
        this.totalCases = totalCases;
        this.deaths = deaths;
        this.recovered = recovered;
    }
}
