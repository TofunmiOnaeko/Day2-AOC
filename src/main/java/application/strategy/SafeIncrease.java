package application.strategy;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SafeIncrease implements ReportStrategy {

    public boolean execute(ArrayList<Integer> list) {
        ArrayList<Integer> passed = new ArrayList<>();
        AtomicBoolean conditionMet = new AtomicBoolean(false);
        AtomicInteger increment = new AtomicInteger(1);

        list.stream().forEach(value -> {
            if (increment.get() < list.size()) {
                int nextValue = list.get(increment.get());
                int difference = value - nextValue;

                if (difference > 0 && difference < 4) {
                    passed.add(difference);
                }

                increment.getAndIncrement();
            };
        });

        if (passed.size() == (list.size() - 1)) {
            conditionMet.set(true);
        }

        return conditionMet.get();
    }
}
