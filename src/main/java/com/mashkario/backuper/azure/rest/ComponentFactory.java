package com.mashkario.backuper.azure.rest;

import com.mashkario.backuper.azure.rest.module.SyncModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = SyncModule.class)
public interface ComponentFactory {

    RestHandler createHttpTriggerHandler();

}
