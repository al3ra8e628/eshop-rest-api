package com.example.eshop.service.controllers;


import com.example.contract.repositories.ItemsRepository;
import com.example.eshop.service.controllers.resources.DocumentResponseResource;
import com.example.eshop.service.exceptions.ResourceNotFoundException;
import com.example.eshop.service.resourcemappers.ItemResourceMapper;
import com.example.modals.Item;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/items/{itemId}/pictures")
public class ItemPicturesController {
    private final ItemsRepository itemsRepository;
    private final ItemResourceMapper itemResourceMapper;

    public ItemPicturesController(ItemsRepository itemsRepository,
                                  ItemResourceMapper itemResourceMapper) {
        this.itemsRepository = itemsRepository;
        this.itemResourceMapper = itemResourceMapper;
    }

    @GetMapping()
    public List<DocumentResponseResource> listItemPictures(@PathVariable Long itemId) {
        final Item item = itemsRepository.findById(itemId);

        return item.getPictures().stream()
                .map(itemResourceMapper::toDocumentResponseResource)
                .peek(it -> this.addDownloadLink(itemId, it))
                .collect(Collectors.toList());
    }



    @GetMapping(value = "/{reference}/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] downloadPicture(@PathVariable Long itemId,
                                  @PathVariable String reference) {
        return getDocumentContent(itemId, reference);
    }

    @GetMapping(value = "/{reference}/download", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> downloadPictureEncoded(@PathVariable Long itemId,
                                                      @PathVariable String reference) {
        byte[] content = getDocumentContent(itemId, reference);

        return Map.of("content", new String(Base64.getEncoder().encode(content)));
    }

    private byte[] getDocumentContent(Long itemId, String reference) {
        return itemsRepository.findById(itemId)
                .getPictures()
                .stream()
                .filter(it -> Objects.equals(it.getReference(), reference)).findFirst()
                .orElseThrow(ResourceNotFoundException::new)
                .getContent();
    }

    private void addDownloadLink(Long itemId, DocumentResponseResource documentResource) {
        String url = "api/v1/items/%s/pictures/%s/download";

        final Link downloadPictureLink = Link.of(String.format(url, itemId, documentResource.getReference()))
                .withType("GET")
                .withRel("download");

        documentResource.add(downloadPictureLink);
    }

}
