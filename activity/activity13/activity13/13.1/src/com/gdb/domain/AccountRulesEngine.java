package com.gdb.domain;

import java.util.HashMap;
import java.util.Map;

public class AccountRulesEngine {
    private static final Map<String, Double> savingsMinBalanceMap = new HashMap<>();
    private static final Map<String, Double> savingsInterestRateMap = new HashMap<>();

    static {
        savingsMinBalanceMap.put("NEW", 10000.0);
        savingsMinBalanceMap.put("STANDARD", 7500.0);
        savingsMinBalanceMap.put("PREMIUM", 5000.0);
        savingsMinBalanceMap.put("PRIVILEGE", 2500.0);

        savingsInterestRateMap.put("NEW", 2.70);
        savingsInterestRateMap.put("STANDARD", 3.00);
        savingsInterestRateMap.put("PREMIUM", 3.50);
        savingsInterestRateMap.put("PRIVILEGE", 4.00);
    }

    public static String getSavingsBucket(int tenureYears) {
        if (tenureYears >= 5) {
            return "PRIVILEGE";
        } else if (tenureYears >= 3) {
            return "PREMIUM";
        } else if (tenureYears >= 1) {
            return "STANDARD";
        } else {
            return "NEW";
        }
    }

    public static double getSavingsMinBalance(int tenureYears) {
        String bucket = getSavingsBucket(tenureYears);
        return savingsMinBalanceMap.getOrDefault(bucket, 10000.0);
    }

    public static double getSavingsInterestRate(int tenureYears) {
        String bucket = getSavingsBucket(tenureYears);
        return savingsInterestRateMap.getOrDefault(bucket, 2.70);
    }

    public static double getCurrentOverdraftLimit(double monthlyTurnover) {
        return Math.max(25000.0, monthlyTurnover * 2.5);
    }

    public static double getFDInterestRate(int months) {
        if (months >= 36) {
            return 7.50;
        } else if (months >= 12) {
            return 6.50;
        } else {
            return 5.00;
        }
    }
}
