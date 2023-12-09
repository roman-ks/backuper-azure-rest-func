package com.mashkario.backuper.azure.rest.service;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.SqlParameter;
import com.azure.cosmos.models.SqlQuerySpec;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.mashkario.backuper.azure.rest.dto.DriveDto;
import com.mashkario.backuper.azure.rest.dto.FileDto;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class SyncPersistenceService {

    private final CosmosContainer cosmosContainer;

    @Inject
    public SyncPersistenceService(CosmosContainer cosmosContainer) {
        this.cosmosContainer = cosmosContainer;
    }

    public List<DriveDto> getDrives() {
        String query = "SELECT distinct c.driveName as name, c.driveId as id FROM c";
        CosmosPagedIterable<DriveDto> items = cosmosContainer.queryItems(query,
                new CosmosQueryRequestOptions(),
                DriveDto.class);

        return items.stream().collect(Collectors.toList());
    }

    public List<FileDto> getFiles(String driveId){
        String query = "SELECT c.id, c.fileName, c.path, c.lastModified from c where c.driveId = @driveId";
        SqlQuerySpec querySpec = new SqlQuerySpec(query)
                .setParameters(List.of(new SqlParameter("@driveId", driveId)));
        CosmosPagedIterable<FileDto> items = cosmosContainer.queryItems(querySpec,
                new CosmosQueryRequestOptions(),
                FileDto.class);

        return items.stream().collect(Collectors.toList());
    }
}
