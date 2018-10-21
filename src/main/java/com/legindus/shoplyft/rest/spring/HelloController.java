package com.legindus.shoplyft.rest.spring;

import com.google.firebase.database.FirebaseDatabase;
import com.legindus.shoplyft.Main;
import com.legindus.shoplyft.firebase.FirebaseRegistry;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from dayyyyyyyyyyvid!";
    }

    @PostMapping("/createQuery")
    public String createQuery(@RequestParam("description") String description, @RequestParam("token") String token) {

        FirebaseDatabase db = Main.registry.getFirebase().getFDatabase();

        System.out.printf("%s, %s%n", description, token);

        return "query created";

    }

    @PostMapping("/claimQuery")
    public String claimQuery(@RequestParam("queryId") String queryIdString, @RequestParam("token") String token) {

        FirebaseDatabase db = Main.registry.getFirebase().getFDatabase();
        int queryId = Integer.decode(queryIdString);

        System.out.printf("%d, %s%n", queryId, token);

        return "query claimed";

    }

}