package com.psp.sentinel.repository;

import com.psp.sentinel.model.document.ServerLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServerLogRepository extends MongoRepository<ServerLog, String> {
}
