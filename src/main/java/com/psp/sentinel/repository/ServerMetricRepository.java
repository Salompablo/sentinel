package com.psp.sentinel.repository;

import com.psp.sentinel.model.document.ServerMetric;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ServerMetricRepository extends MongoRepository<ServerMetric, String> {
    List<ServerMetric> findTop10ByServerIdOrderByTimestampDesc(Long serverId);
}
