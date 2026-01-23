package com.psp.sentinel.controller;

import com.psp.sentinel.model.document.ServerLog;
import com.psp.sentinel.repository.ServerLogRepository;
import com.psp.sentinel.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AIController {

    private final AIService aiService;
    private final ServerLogRepository serverLogRepository;

    @PostMapping("/analyze")
    public Map<String, String> analyzeError(@RequestBody Map<String, String> request) {

        String logContent = request.get("logContent");

        String analysis = aiService.analyzeLog(logContent);

        return Map.of("analysis", analysis);

    }

    @PatchMapping("/logs/{id}/save")
    public ServerLog updateAnalysis(@PathVariable String id, @RequestBody Map<String, String> body) {
        String analysisText = body.get("analysis");

        return serverLogRepository.findById(id)
                .map(log -> {
                    log.setAiAnalysis(analysisText);
                    return serverLogRepository.save(log);
                })
                .orElseThrow(() -> new RuntimeException("Log with ID " + id + " not found"));
    }

}
