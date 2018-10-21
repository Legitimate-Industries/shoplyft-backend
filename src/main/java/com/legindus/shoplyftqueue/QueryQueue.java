package com.legindus.shoplyftqueue;

import com.legindus.shoplyft.Main;
import com.legindus.shoplyft.firebase.models.CategoryDocument;
import com.legindus.shoplyft.firebase.models.Query;
import com.legindus.shoplyft.firebase.utils.Status;
import com.legindus.shoplyftqueue.entities.Employee;
import com.legindus.shoplyftsearch.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class QueryQueue {
    private Logger LOG = LoggerFactory.getLogger(QueryQueue.class);
    private Map<String, Queue<Query>> queueMap;
    private Index index;

    public QueryQueue(List<CategoryDocument> documents) throws Exception {
        index = new Index(documents);
        queueMap = new HashMap<>();

        for (CategoryDocument x : documents)
            queueMap.put(x.name, new ArrayDeque<>());
    }

    public void addQuery(Query q) {
        queueMap.get(index.search(q.first).get(0)).add(q);
    }

    public void claimQuery(long id, Employee employee) throws Exception {
        updateQuery(id, employee);
        for (Queue<Query> queue: queueMap.values())
            for (Query query: queue)
                if (query.id == id) {
                    queue.remove(query);
                    break;
                }
    }

    public void claimQuery(String category, long id, Employee employee) throws Exception{
        updateQuery(id, employee);
        for (Query q: queueMap.get(category))
            if (q.id == id) {
                queueMap.get(category).remove(q);
                break;
            }
    }

    private void updateQuery(long id, Employee employee) throws Exception {
        Query old = Main.registry.getQuery(id);

        Query q = new Query.Builder()
                .setId(id)
                .setTimestamp(old.timestamp)
                .setStatus(Status.IN_PROGRESS)
                .setChat(old.chat.toArray(new String[old.chat.size()]))
                .setFirst(old.first)
                .setEmployeeName(employee.name)
                .build();

        Main.registry.updateQuery(q, id);
    }
}
