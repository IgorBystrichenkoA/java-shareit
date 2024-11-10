package ru.practicum.shareit.booking.model.StateStrategy;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.BookingState;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class StateStrategyFactory {

    private Map<BookingState, StateStrategy> strategies;

    public StateStrategyFactory(Set<StateStrategy> strategySet) {
        createStrategy(strategySet);
    }

    public StateStrategy findStrategy(BookingState strategyName) {
        return strategies.get(strategyName);
    }

    private void createStrategy(Set<StateStrategy> strategySet) {
        strategies = new HashMap<>();
        strategySet.forEach(strategy -> strategies.put(strategy.getStrategyName(), strategy));
    }
}
