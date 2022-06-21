package core.backend.task5;

import core.backend.task5.dao.ExchangeArguments;

import java.util.function.Consumer;

public class Task implements Runnable {

    private final Consumer<ExchangeArguments> consumer;
    private final ExchangeArguments arguments;

    public Task(Consumer<ExchangeArguments> consumer, ExchangeArguments arguments) {
        this.consumer = consumer;
        this.arguments = arguments;
    }

    @Override
    public void run() {
        consumer.accept(arguments);
    }
}
