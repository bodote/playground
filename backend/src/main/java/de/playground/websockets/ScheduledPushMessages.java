package de.playground.websockets;


import lombok.extern.java.Log;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Log
public class ScheduledPushMessages {

    private final SimpMessagingTemplate simpMessagingTemplate;
    

    
    public ScheduledPushMessages(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;

    }

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        log.info("sendMessage fixed rate");
        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        simpMessagingTemplate.convertAndSend("/topic/messages",
            new OutputMessage("Chuck Norris", "faker.chuckNorris().fact()", time));
    }
    
}
