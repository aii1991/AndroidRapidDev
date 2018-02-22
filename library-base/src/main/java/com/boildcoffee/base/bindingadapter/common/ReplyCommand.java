package com.boildcoffee.base.bindingadapter.common;


import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by kelin on 15-8-4.
 *
 */
public class ReplyCommand<T> {

    private Action execute0;
    private Consumer<T> execute1;

    private Function<Boolean, Boolean> canExecute0;

    public ReplyCommand(Action execute) {
        this.execute0 = execute;
    }


    public ReplyCommand(Consumer<T> execute) {
        this.execute1 = execute;
    }

    /**
     * @param execute     callback for event
     * @param canExecute0 if this function return true the action execute would be invoked! otherwise would't invoked!
     */
    public ReplyCommand(Action execute, Function<Boolean, Boolean> canExecute0) {
        this.execute0 = execute;
        this.canExecute0 = canExecute0;
    }

    /**
     * @param execute     callback for event,this callback need a params
     * @param canExecute0 if this function return true the action execute would be invoked! otherwise would't invoked!
     */
    public ReplyCommand(Consumer<T> execute, Function<Boolean, Boolean> canExecute0) {
        this.execute1 = execute;
        this.canExecute0 = canExecute0;
    }


    public void execute() {
        try {
            if (execute0 != null && canExecute0()) {
                execute0.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean canExecute0() throws Exception {
        if (canExecute0 == null) {
            return true;
        }
        return canExecute0.apply(false);
    }


    public void execute(T parameter) {
        try {
            if (execute1 != null && canExecute0()) {
                execute1.accept(parameter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
