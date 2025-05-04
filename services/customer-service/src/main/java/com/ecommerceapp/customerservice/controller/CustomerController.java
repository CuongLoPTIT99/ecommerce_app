package com.ecommerceapp.customerservice.controller;

import com.ecommerceapp.commonmodule.base.dto.NotificationMessageDTO;
import com.ecommerceapp.commonmodule.base.service.NotificationService;
import com.ecommerceapp.commonmodule.constant.Enums;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.AcceptEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class CustomerController {
//    private final CustomerService customerService;

//    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
//        return ResponseEntity.ok(customerService.createCustomer(customerRequestDTO));
//    }
    private final NotificationService notificationService;
    private final JdbcTemplate jdbcTemplate;

    @PostMapping("/traCuuHoSo")
    public ResponseEntity<?> getCustomerById(@RequestBody String body) throws Exception {
        String rs = "";
        ObjectMapper objectMapper = new ObjectMapper();
        String soHS = Optional.ofNullable(objectMapper.readTree(body).get("SoHoSo")).map(JsonNode::asText).orElse("");
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select data from LPTB_OWNER.TEST_TDTT_BCA where so_hs = :soHS", soHS);
        if (Objects.nonNull(result) && result.size() > 0) {
            rs = (String) result.get(0).get("data");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8));
        return new ResponseEntity<>(rs, headers, HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<?> token(@RequestBody String token) {
        System.out.println(token);
//        return ResponseEntity.ok(customerService.getCustomerById(id));
        String rs = "{\n" +
                "\"statusCode\": \"00\",\n" +
                "\"accessToken\": \"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lk\",\n" +
                "\"errorDetail\": \"\"\n" +
                "\n" +
                "}\n";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(rs, headers, HttpStatus.OK);
    }

    @PostMapping("/notification")
    public ResponseEntity<?> testNotification(@RequestBody String token) {
        notificationService.sendPushNotification(NotificationMessageDTO.builder()
                .title("Test Notification")
                .content("This is a test notification")
                .recipientId("12345")
                .status(Enums.NotificationStatus.PENDING)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>("ok", headers, HttpStatus.OK);
    }

//    public ResponseEntity<?> updateCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
//        return ResponseEntity.ok(customerService.updateCustomer(customerRequestDTO));
//    }

}
