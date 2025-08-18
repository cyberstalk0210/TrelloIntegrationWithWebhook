package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TrelloWebhookDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TrelloService {

    private final RestTemplate restTemplate;
    private final String key;
    private final String token;
    private static final String BASE_URL =
        "https://api.trello.com/1/webhooks?callbackURL={callbackURL}&idModel={idModel}&key={key}&token={token}";

    private final String callBackUrl = "https://f025f22f3d03.ngrok-free.app/trello/webhook";

    private final String trelloId = "68a0df009a6305609f49eb78";

    public TrelloService(RestTemplate restTemplate, @Value("${trello.api.key}") String key, @Value("${trello.api.token}") String token) {
        this.restTemplate = restTemplate;
        this.key = key;
        this.token = token;
    }

    public ResponseEntity<TrelloWebhookDTO> response() {
        return restTemplate.postForEntity(BASE_URL, null, TrelloWebhookDTO.class, callBackUrl, trelloId, key, token);
    }
}
