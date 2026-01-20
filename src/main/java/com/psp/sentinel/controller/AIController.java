package com.psp.sentinel.controller;

import com.psp.sentinel.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")
public class AIController {

    private final AIService aiService;

    @PostMapping("/analyze")
    public Map<String, String> analyzeError(@RequestBody Map<String, String> request) {

        String logContent = request.get("logContent");

        String analysis = aiService.analyzeLog(logContent);

        return Map.of("analysis", analysis);

    }

}
