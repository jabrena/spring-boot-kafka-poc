package info.jab.ms.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class FakeRepositoryImpl implements FakeRepository {

    private Queue<String> queue = new ConcurrentLinkedQueue<>();

    @Override
    public String save(String message) {
        queue.add(message);
        return message;
    }

    @Override
    public List<String> findAll() {
        return queue.stream().toList();
    }
}
