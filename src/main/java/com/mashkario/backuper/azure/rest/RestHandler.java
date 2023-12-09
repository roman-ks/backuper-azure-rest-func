package com.mashkario.backuper.azure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashkario.backuper.azure.rest.dto.DriveDto;
import com.mashkario.backuper.azure.rest.dto.FileDto;
import com.mashkario.backuper.azure.rest.service.SyncPersistenceService;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class RestHandler {

    private final SyncPersistenceService persistenceService;
    private final ObjectMapper mapper;

    @Inject
    public RestHandler(SyncPersistenceService persistenceService, ObjectMapper mapper) {
        this.persistenceService = persistenceService;
        this.mapper = mapper;
    }

    @FunctionName("drives")
    public HttpResponseMessage getDrives(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.FUNCTION,
                    route = "drives"
            )
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("invoked: GET drives");

        List<DriveDto> drives = persistenceService.getDrives();

        return request.createResponseBuilder(HttpStatus.OK)
                .body(drives)
                .build();
    }

    @FunctionName("files")
    public HttpResponseMessage getFiles(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.FUNCTION,
                    route = "drive/{id}"
            )
            HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id,
            final ExecutionContext context) {
        context.getLogger().info("invoked: GET drive/" + id);

        List<FileDto> drives = persistenceService.getFiles(id);

        return request.createResponseBuilder(HttpStatus.OK)
                .body(drives)
                .build();
    }

}
