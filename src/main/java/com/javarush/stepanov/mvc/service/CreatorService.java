package com.javarush.stepanov.mvc.service;

import com.javarush.stepanov.mvc.mapper.CreatorDto;
import com.javarush.stepanov.mvc.model.creator.Creator;
import com.javarush.stepanov.mvc.repository.impl.CreatorRepoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class CreatorService {

    private final CreatorRepoImpl repoImpl;
    private final CreatorDto mapper;

    public List<Creator.Out> getAll() {
        return repoImpl
                .getAll()
                .map(mapper::out)
                .toList();
    }

    public Creator.Out get(Long id) {
        return repoImpl
                .get(id)
                .map(mapper::out)
                .orElseThrow();
    }

    public Creator.Out create(Creator.In input) {
        return repoImpl
                .create(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public Creator.Out update(Creator.In input) {
        return repoImpl
                .update(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public boolean delete(Long id) {
        return repoImpl.delete(id);
    }

    public Page<Creator.Out> getAllPaged(int page, int size, String sortBy) {
        // Parse the sort string (format: "property::direction")
        String[] sortParams = sortBy.split("::");
        String property = sortParams[0];
        String direction = sortParams.length > 1 ? sortParams[1] : "asc";

        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(property).descending() :
                Sort.by(property).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repoImpl.findAll(pageable).map((x)->mapper.out(x));
    }

    public Collection<Creator.Out> getCreatorsByFirstname(String firstname) {
        System.out.println("Searching for creators with firstname: " + firstname);
        List<Creator.Out> result = repoImpl
                .getCreatorsByName(firstname)
                .map(mapper::out)
                .toList();
        System.out.println("Found " + result.size() + " creators");
        return result;
    }
}
