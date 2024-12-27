package application;

import application.strategy.ReportStrategy;
import application.strategy.SafeDecrease;
import application.strategy.SafeIncrease;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Application {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Application application = new Application();

    public static void main(String[] args) throws JsonProcessingException {
        int safeReports = application.evaluateLists(application.createArray());
        System.out.println(safeReports);
    }

    private int evaluateLists(ArrayList<ArrayList<Integer>> listOfLists) {
        AtomicInteger safeReports = new AtomicInteger(0);

        listOfLists.stream().forEach(list -> {
            boolean passedConditions = runStrategies(list);
            if(passedConditions) {
                safeReports.addAndGet(1);
            }
        });

        return safeReports.get();
    }

    private boolean runStrategies(ArrayList<Integer> list) {
        ReportStrategyProcessor processor = createStrategies();
        return processor.executeStrategy(list);
    }

    private ReportStrategyProcessor createStrategies() {
        final SafeDecrease decrease = new SafeDecrease();
        final SafeIncrease increase = new SafeIncrease();

        ArrayList<ReportStrategy> strategies = new ArrayList<>();
        strategies.add(decrease);
        strategies.add(increase);

        return new ReportStrategyProcessor(strategies);
    }

    private ArrayList<ArrayList<Integer>> createArray() throws JsonProcessingException {
        ArrayList<ArrayList<Integer>> listOfLists = new ArrayList<>();

        try {
            // Load the JSON array from the resources folder
            InputStream inputStream = Application.class.getClassLoader().getResourceAsStream("reports.json");

            if (inputStream == null) {
                System.out.println("Resource not found!");
                return listOfLists;
            }

            listOfLists = objectMapper.readValue(inputStream, new TypeReference<ArrayList<ArrayList<Integer>>>() {});

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listOfLists;
    }
}
