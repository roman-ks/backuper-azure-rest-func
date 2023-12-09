package com.mashkario.backuper.azure.rest.dihook;


import com.mashkario.backuper.azure.rest.DaggerComponentFactory;
import com.microsoft.azure.functions.spi.inject.FunctionInstanceInjector;

public class HandlerInstanceInjector implements FunctionInstanceInjector {

    @Override
    public <T> T getInstance(Class<T> functionClass) {
        @SuppressWarnings("unchecked")
        T t = (T) DaggerComponentFactory.create().createHttpTriggerHandler();
        return t;
    }
}
