package org.sayar.net.sendNotification;

import lombok.Getter;
import lombok.Setter;
import org.sayar.net.Model.newModel.UnitOfMeasurement;
import org.sayar.net.Tools.Print;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class ActivitySampleNotification {
    RestTemplate restTemplate = new RestTemplate();


    public void sendActivitySampleNotification(String userId) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setNotifType("plus");
        String fooResourceUrl = "http://localhost:3000/send";
        System.out.println("userId " + userId);
        System.out.println("fooResourceUrl " + fooResourceUrl);

        restTemplate.postForEntity(fooResourceUrl, notification, String.class);
//        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    public void sendMinusActivitySampleNotification(String userId) {
//        Notification notification = new Notification();
//        notification.setUserId(userId);
//        notification.setNotifType("minus");
//        String fooResourceUrl = "http://localhost:3000/send";
//        Print.print("notification",notification);
//        restTemplate.postForEntity(fooResourceUrl, notification, String.class);
//        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

//        public void send(String userId) throws URISyntaxException {
//        Notification notification=new Notification();
//        notification.setUserId(userId);
//        String fooResourceUrl = "http://127.0.0.1:3000/send";
//        System.out.println("userId " + userId);
//
//
//
//        String Url = "http://localhost:3000/send";
//        URI uri = new URI(Url);
//
//
//            ResponseEntity<UnitOfMeasurement> responseExchangeURI = testRestTemplate.exchange(uri,
//                HttpMethod.POST,
//                UnitOfMeasurement.class);
//        restTemplate.postForEntity(fooResourceUrl,notification , String.class);
////        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
//    }
    @Getter
    @Setter
    public class Notification {
        private String userId;
        private String notifType;
    }
}
