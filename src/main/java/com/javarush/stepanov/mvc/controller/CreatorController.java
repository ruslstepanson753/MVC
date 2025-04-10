package com.javarush.stepanov.mvc.controller;

import com.javarush.stepanov.mvc.model.creator.Creator;
import com.javarush.stepanov.mvc.service.CreatorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1.0/creators")
public class CreatorController {

    private final CreatorService creatorService;

    public CreatorController(CreatorService creatorService) {
        this.creatorService = creatorService;
    }

    @GetMapping
    public Collection<Creator.Out> getAll() {
        return creatorService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Creator.Out create(@RequestBody @Valid Creator.In inputDto) {
        return creatorService.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Creator.Out update(@RequestBody @Valid Creator.In inputDto) {
        try {
            return creatorService.update(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public Creator.Out read(@PathVariable long id) {
        return creatorService.get(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean delete = creatorService.delete(id);
        if (!delete) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/paged")
    public ResponseEntity<PagedModel<EntityModel<Creator.Out>>> getAllPaged(
            @Min(1) @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort_by", required = false, defaultValue = "id::asc") String sortBy,
            PagedResourcesAssembler<Creator.Out> assembler) {

        Page<Creator.Out> creatorPage = creatorService.getAllPaged(page - 1, size, sortBy);

        PagedModel<EntityModel<Creator.Out>> pagedModel =
                assembler.toModel(creatorPage, linkTo(methodOn(CreatorController.class).getAllPaged(page, size, sortBy, assembler)).withSelfRel());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Link", createLinkHeader(pagedModel));

        return new ResponseEntity<>(pagedModel, responseHeaders, HttpStatus.OK);
    }

    private String createLinkHeader(PagedModel<EntityModel<Creator.Out>> pagedModel) {
        final StringBuilder linkHeader = new StringBuilder();

        if (pagedModel.getLinks().hasLink("first")) {
            linkHeader.append(buildLinkHeader(pagedModel.getLink("first").get().getHref(), "first"));
            linkHeader.append(", ");
        }

        if (pagedModel.getLinks().hasLink("next")) {
            linkHeader.append(buildLinkHeader(pagedModel.getLink("next").get().getHref(), "next"));
        }

        return linkHeader.toString();
    }

    public static String buildLinkHeader(final String uri, final String rel) {
        return "<" + uri + ">; rel=\"" + rel + "\"";
    }

    @GetMapping("/firstnames/{firstname}")
    public Collection<Creator.Out> getCreatorsByFirstname(@PathVariable (value = "firstname", required = false) String firstname)
    {
        // Simulate retrieving product from a specific category
        if (firstname == null) {
            return creatorService.getAll();
        }

        Collection<Creator.Out> result = creatorService.getCreatorsByFirstname(firstname);
        System.out.println();
        return result;
    }

}
