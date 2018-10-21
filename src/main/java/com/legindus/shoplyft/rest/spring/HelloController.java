package com.legindus.shoplyft.rest.spring;

import com.google.firebase.database.FirebaseDatabase;
import com.legindus.shoplyft.Main;
import com.legindus.shoplyft.firebase.FirebaseRegistry;
import com.legindus.shoplyft.firebase.models.Query;
import com.legindus.shoplyft.firebase.utils.Status;
import com.legindus.shoplyftqueue.entities.Employee;
import org.apache.lucene.util.QueryBuilder;
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

        Query q = new Query.Builder()
                .addChat(description, "An employee will be with you shortly!")
                .setStatus(Status.PENDING).build();

        String id = Main.registry.newQuery(q);

        Main.queue.addQuery(q);

        return id;

    }

    @PostMapping("/claimQuery")
    public String claimQuery(@RequestParam("queryId") String queryId, @RequestParam("token") String token) {

        FirebaseDatabase db = Main.registry.getFirebase().getFDatabase();

        try {
            Main.queue.claimQuery(queryId, new Employee(token));
        } catch(Exception e) {
            return "query not claimed";
        }


        return "query claimed";

    }

}