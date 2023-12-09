package com.mashkario.backuper.azure.rest.module;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.identity.ChainedTokenCredential;
import com.azure.identity.ChainedTokenCredentialBuilder;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class SyncModule {

    @Provides
    @Singleton
    public ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

    @Provides
    @Singleton
    public CosmosClient provideCosmosClient() {
        ChainedTokenCredential tokenCredential = new ChainedTokenCredentialBuilder()
                .addFirst(new ManagedIdentityCredentialBuilder().build())
                .build();

        String endpoint = System.getenv("COSMOSDB_ENDPOINT");
        return new CosmosClientBuilder()
                .credential(tokenCredential)
                .endpoint(endpoint)
                .buildClient();
    }

    @Provides
    @Singleton
    public CosmosContainer providesPersistenceContainer(CosmosClient client) {
        String databaseId = System.getenv("SYNC_DATABASE");
        String containerId = System.getenv("SYNC_DATABASE_CONTAINER");
        return client.getDatabase(databaseId).getContainer(containerId);
    }

}
