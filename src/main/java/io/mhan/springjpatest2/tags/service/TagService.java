package io.mhan.springjpatest2.tags.service;

import io.mhan.springjpatest2.tags.entity.Tag;
import io.mhan.springjpatest2.tags.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Tag createAndSave(String name) {

        Tag tag = Tag.create(name);

        return tagRepository.save(tag);
    }

    public Set<Tag> findOrCreateAndSaveTagsByStringNames(Set<String> tagNames) {

        Set<Tag> existingTags = tagRepository.findAllByNameIn(tagNames);

        Set<String> existingTagNames = existingTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        Set<Tag> newTags = tagNames.stream()
                .filter(tagName -> !existingTagNames.contains(tagName))
                .map(this::createAndSave)
                .collect(Collectors.toSet());

        existingTags.addAll(newTags);

        return Collections.unmodifiableSet(newTags);
    }
}
