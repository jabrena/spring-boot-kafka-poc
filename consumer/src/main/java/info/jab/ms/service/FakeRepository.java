package info.jab.ms.service;

import java.util.List;

public interface FakeRepository {

    String save(String message);
    List<String> findAll();
}
