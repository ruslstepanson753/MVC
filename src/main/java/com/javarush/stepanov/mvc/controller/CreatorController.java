package com.javarush.stepanov.mvc.controller;

import com.javarush.stepanov.mvc.model.creator.Creator;
import com.javarush.stepanov.mvc.service.CreatorService;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//swagger http://localhost:24110/swagger-ui/index.html#/
//swagger json http://localhost:24110/v3/api-docs
@RestController
@RequestMapping("/api/v2/creators")
public class CreatorController {

    private final CreatorService creatorService;
    private final PagedResourcesAssembler<Creator.Out> pagedResourcesAssembler;

    public CreatorController(CreatorService creatorService,
                             PagedResourcesAssembler<Creator.Out> pagedResourcesAssembler) {
        this.creatorService = creatorService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/{id}")
    public EntityModel<Creator.Out> getCreatorById(@PathVariable Long id) {
        Creator.Out creator = creatorService.get(id);

        return EntityModel.of(creator,
                linkTo(methodOn(CreatorController.class).getCreatorById(id)).withSelfRel(),
                linkTo(methodOn(CreatorController.class).getAllCreators(0, 10, "id::asc")).withRel("creators"),
                linkTo(methodOn(CreatorController.class).getCreatorsByFirstname(creator.getFirstname(), 0, 10)).withRel("same-firstname"),
                linkTo(methodOn(CreatorController.class).updateCreator(id, null)).withRel("update").withType("PUT"),
                linkTo(methodOn(CreatorController.class).deleteCreator(id)).withRel("delete").withType("DELETE")
        );
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Creator.Out>>> getAllCreators(
            @Min(0) @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id::asc") String sortBy) {

        Page<Creator.Out> pageResult = creatorService.getAllPaged(page, size, sortBy);

        PagedModel<EntityModel<Creator.Out>> model = pagedResourcesAssembler.toModel(pageResult,
                creator -> EntityModel.of(creator,
                        linkTo(methodOn(CreatorController.class).getCreatorById(creator.getId())).withSelfRel(),
                        linkTo(methodOn(CreatorController.class).getCreatorsByFirstname(creator.getFirstname(), 0, 10)).withRel("same-firstname")
                ));

        // Добавляем дополнительные ссылки
        model.add(linkTo(methodOn(CreatorController.class).createCreator(null)).withRel("create").withType("POST"));

        return ResponseEntity.ok(model);
    }

    @GetMapping("/firstnames/{firstname}")
    public ResponseEntity<PagedModel<EntityModel<Creator.Out>>> getCreatorsByFirstname(
            @PathVariable String firstname,
            @Min(0) @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Здесь используем ту же логику, что и в getAllPaged, но с фильтрацией по имени
        Page<Creator.Out> pageResult = (Page<Creator.Out>) creatorService.getAllPaged(page, size, "firstname::asc")
                .filter(creator -> creator.getFirstname().equals(firstname));

        PagedModel<EntityModel<Creator.Out>> model = pagedResourcesAssembler.toModel(pageResult,
                creator -> EntityModel.of(creator,
                        linkTo(methodOn(CreatorController.class).getCreatorById(creator.getId())).withSelfRel()
                ));

        model.add(linkTo(methodOn(CreatorController.class).getAllCreators(0, 10, "id::asc")).withRel("all-creators"));

        return ResponseEntity.ok(model);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Creator.Out>> createCreator(@RequestBody Creator.In input) {
        Creator.Out created = creatorService.create(input);

        return ResponseEntity
                .created(linkTo(methodOn(CreatorController.class).getCreatorById(created.getId())).toUri())
                .body(EntityModel.of(created,
                        linkTo(methodOn(CreatorController.class).getCreatorById(created.getId())).withSelfRel(),
                        linkTo(methodOn(CreatorController.class).getAllCreators(0, 10, "id::asc")).withRel("creators")
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Creator.Out>> updateCreator(
            @PathVariable Long id,
            @RequestBody Creator.In input) {
        input.setId(id);
        Creator.Out updated = creatorService.update(input);

        return ResponseEntity.ok(EntityModel.of(updated,
                linkTo(methodOn(CreatorController.class).getCreatorById(id)).withSelfRel(),
                linkTo(methodOn(CreatorController.class).getAllCreators(0, 10, "id::asc")).withRel("creators")
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCreator(@PathVariable Long id) {
        creatorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}