package com.psp.sentinel.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final ChatClient chatClient;

    public AIService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String analyzeLog(String logContent) {
        return chatClient.prompt()
                .system("""
                        You are a senior Site Reliability Engineer (SRE).
                    
                        Analyze the following server error log.
                    
                        Your response must follow this strict format:
                   
                        1. **Root Cause:** (Brief and simple explanation).
                    
                        2. **Suggested Solution:** (1 or 2 specific commands or actions).
                    
                        Be concise. Do not use introductions like "Hello" or "Sure."
                    """)
                .user(logContent)
                .call()
                .content();
    }

}
