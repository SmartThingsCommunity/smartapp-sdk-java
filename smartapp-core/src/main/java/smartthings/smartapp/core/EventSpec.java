package smartthings.smartapp.core;

import v1.smartapps.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface EventSpec {
    EventSpec whenMode(String modeId, Action<Event> action);
    EventSpec whenSchedule(String scheduleName, Action<Event> action);
    EventSpec whenSubscription(String subscriptionName, Action<Event> action);
    EventSpec when(Predicate<Event> predicate, Action<Event> action);
    EventSpec failOnError(Predicate<Throwable> failOnError);
    EventSpec onError(BiFunction<ExecutionRequest, Throwable, ExecutionResponse> onError);
    EventSpec onSuccess(Function<ExecutionRequest, ExecutionResponse> onSuccess);
}
