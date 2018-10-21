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

    public void claimQuery(String id, Employee employee) throws Exception {
        updateQuery(id, employee);
        for (Queue<Query> queue: queueMap.values())
            for (Query query: queue)
                if (query.id.equals(id)) {
                    queue.remove(query);
                    break;
                }
    }

    public void claimQuery(String category, String id, Employee employee) throws Exception{
        updateQuery(id, employee);
        for (Query q: queueMap.get(category))
            if (q.id.equals(id)) {
                queueMap.get(category).remove(q);
                break;
            }
    }

    private void updateQuery(String id, Employee employee) throws Exception {
        Query q = Main.registry.getQuery(id);

        q.status = Status.IN_PROGRESS;
        q.employeeName = employee.name;

        Main.registry.updateQuery(q, id);
    }
}
