package vip.linhs.stock.trategy.handle;

public abstract class BaseStrategyHandler<I, R> implements StrategyHandler {

    @Override
    public void handle() {
        I input = queryInput();
        R result = handle(input);
        handleResult(input, result);
    }

    public abstract I queryInput();

    public abstract R handle(I input);

    public abstract void handleResult(I input, R result);

}
