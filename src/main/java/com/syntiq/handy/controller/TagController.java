package com.syntiq.handy.controller;

import com.syntiq.handy.model.entity.Tag;
import com.syntiq.handy.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/api/tag")
public class TagController  {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public ResponseEntity<Collection<Tag>> getTags(){
        return ResponseEntity.ok(this.tagRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody String name) {
        Tag tag = new Tag();
        tag.setName(name);
        System.out.println(name);
        this.tagRepository.save(tag);
        this.tagRepository.flush();
        return ResponseEntity.status(201).body(tag);
    }
}
