package com.mashkario.backuper.azure.rest.service;

public record SyncMdPersistenceDto(String id,
                                   String fileName,
                                   String path,
                                   String driveId,
                                   String driveName,
                                   long lastModified) {
}
