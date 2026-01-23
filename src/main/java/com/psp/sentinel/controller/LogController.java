package com.psp.sentinel.controller;

import com.psp.sentinel.model.document.ServerLog;
import com.psp.sentinel.repository.ServerLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class LogController {

    private final ServerLogRepository serverLogRepository;

    @GetMapping
    public Page<ServerLog> getAlllogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        return serverLogRepository.findAll(pageable);
    }

}
