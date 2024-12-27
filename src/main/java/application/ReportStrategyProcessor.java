package application;

import application.strategy.ReportStrategy;

import java.util.ArrayList;

public class ReportStrategyProcessor {

    private ArrayList<ReportStrategy> reportStrategies;

    public ReportStrategyProcessor(ArrayList<ReportStrategy> reportStrategies) {
        this.reportStrategies = reportStrategies;
    }

    public boolean executeStrategy(ArrayList<Integer> list) {
        for (ReportStrategy strategy : reportStrategies) {
            boolean result = strategy.execute(list);
            if (result) {
                return true;
            }
        }
        return false;
    }
}
