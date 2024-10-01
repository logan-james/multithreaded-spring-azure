package edu.wgu.sample.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WelcomeController {

    @Autowired
    private MessageSource messageSource;

    // Part B1 Welcome
    @GetMapping("/welcome")
    public ResponseEntity<Map<String, String>> getWelcomeMessages() {
        // START OF CHANGES FOR PROBLEM 1
        // Using ExecutorService to run tasks in separate threads
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Map<String, Future<String>> futureMessages = new HashMap<>();

        // Submit tasks to retrieve messages in separate threads
        futureMessages.put("english", executorService.submit(() ->
                messageSource.getMessage("welcomeMessage", null, Locale.US)
        ));
        futureMessages.put("french", executorService.submit(() ->
                messageSource.getMessage("welcomeMessage", null, Locale.CANADA_FRENCH)
        ));

        Map<String, String> welcomeMessages = new HashMap<>();

        try {
            // Retrieve messages from futures
            welcomeMessages.put("english", futureMessages.get("english").get());
            welcomeMessages.put("french", futureMessages.get("french").get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        // END OF CHANGES FOR PROBLEM 1
        return new ResponseEntity<>(welcomeMessages, HttpStatus.OK);
    }
    // Part B2 Prices
    @GetMapping("/prices")
    public ResponseEntity<Map<String, String>> getPrices() {
        Map<String, String> prices = new HashMap<>();
        prices.put("USD", "$100");
        prices.put("CAD", "C$130");
        prices.put("EUR", "€85");
        return new ResponseEntity<>(prices, HttpStatus.OK);
    }
    // Part B3 Times
    @GetMapping("/presentation-time")
    public ResponseEntity<String> getPresentationTime() {
        ZonedDateTime etTime = ZonedDateTime.now(ZoneId.of("America/New_York"));
        ZonedDateTime mtTime = etTime.withZoneSameInstant(ZoneId.of("America/Denver"));
        ZonedDateTime utcTime = etTime.withZoneSameInstant(ZoneId.of("UTC"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String response = String.format("Presentation time - ET: %s, MT: %s, UTC: %s",
                etTime.format(formatter),
                mtTime.format(formatter),
                utcTime.format(formatter));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}