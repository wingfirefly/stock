package vip.linhs.stock.trategy.handle;

public abstract class BaseStrategyHandler<Input, Result> implements StrategyHandler {

    @Override
    public void handle() {
        Input input = queryInput();
        Result result = handle(input);
        handleResult(input, result);
    }

    public abstract Input queryInput();

    public abstract Result handle(Input input);

    public abstract void handleResult(Input input, Result result);

}
