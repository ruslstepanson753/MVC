package com.javarush.stepanov.mvc.repository.impl;

import com.javarush.stepanov.mvc.model.creator.Creator;
import com.javarush.stepanov.mvc.repository.Repo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
public class CreatorRepoImpl implements Repo<Creator> {

    Map<Long, Creator> memoryDatabase = new ConcurrentHashMap<>();

    @Override
    public Stream<Creator> getAll() {
        return memoryDatabase.values().stream();
    }

    @Override
    public Optional<Creator> get(Long id) {
        return Optional.ofNullable(memoryDatabase.get(id));
    }

    @Override
    public Optional<Creator> create(Creator input) {
        Long id = idGenerator.incrementAndGet();
        input.setId(id);
        memoryDatabase.put(id,input);
        return Optional.of(input);
    }

    @Override
    public Optional<Creator> update(Creator input) {
        memoryDatabase.put(input.getId(),input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(Long id) {
        return memoryDatabase.remove(id) != null;
    }

    public Page<Creator> findAll(Pageable pageable) {
        // Получаем все записи из мапы
        List<Creator> allCreators = new ArrayList<>(memoryDatabase.values());

        // Общее количество записей
        long total = allCreators.size();

        // Применяем сортировку, если она задана
        if (pageable.getSort().isSorted()) {
            allCreators.sort((creator1, creator2) -> {
                for (Sort.Order order : pageable.getSort()) {
                    int result = 0;
                    String property = order.getProperty();

                    // Сравниваем по указанному свойству
                    switch (property) {
                        case "id":
                            result = creator1.getId().compareTo(creator2.getId());
                            break;
                        case "firstName":
                            result = creator1.getFirstname().compareTo(creator2.getFirstname());
                            break;
                        // Добавьте другие поля для сортировки по необходимости
                        default:
                            result = 0;
                            break;
                    }

                    // Если порядок по убыванию, инвертируем результат
                    if (order.isDescending()) {
                        result = -result;
                    }

                    // Если нашли различие, возвращаем результат
                    if (result != 0) {
                        return result;
                    }
                }
                return 0;
            });
        }

        // Применяем пагинацию
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allCreators.size());

        // Выбираем подмножество для текущей страницы
        List<Creator> pageContent = start < end ?
                allCreators.subList(start, end) :
                Collections.emptyList();

        // Создаем и возвращаем объект Page
        return new PageImpl<>(pageContent, pageable, total);
    }

    public Stream<Creator> getCreatorsByName(String firstname) {
        System.out.println("Repository: searching for creators with firstname: " + firstname);
        // Сначала выведем все имена, чтобы увидеть, что есть в базе
        memoryDatabase.values().forEach(creator ->
                System.out.println("Creator in DB: id=" + creator.getId() + ", firstname=" + creator.getFirstname()));

        Stream<Creator> result = memoryDatabase
                .values()
                .stream()
                .filter(x -> {
                    boolean matches = x.getFirstname().equals(firstname);
                    System.out.println("Checking creator: " + x.getFirstname() + " against " + firstname + " - Match: " + matches);
                    return matches;
                });
        return result;
    }
}
