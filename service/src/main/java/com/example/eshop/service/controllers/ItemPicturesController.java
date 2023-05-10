package com.example.eshop.service.controllers;

import com.example.contract.repositories.ItemsRepository;
import com.example.eshop.service.controllers.resources.DocumentResponseResource;
import com.example.eshop.service.exceptions.ResourceNotFoundException;
import com.example.eshop.service.resourcemappers.ItemResourceMapper;
import com.example.modals.Document;
import com.example.modals.Item;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "/{reference}/download")
    public ResponseEntity<byte[]> downloadPicture(@PathVariable Long itemId,
                                                  @PathVariable String reference) {
        final Document documentItemDocument = getDocumentItemDocument(itemId, reference);
        byte[] content = documentItemDocument.getContent();
        final HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.valueOf(
                documentItemDocument.getContentType()));

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/{reference}/download", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> downloadPictureEncoded(@PathVariable Long itemId,
                                                      @PathVariable String reference) {
        Document document = getDocumentItemDocument(itemId, reference);
        byte[] content = document.getContent();

        final String base64Prefix = String.format("data:%s;base64,", document.getContentType());

        return Map.of("content", base64Prefix.concat(Base64.getEncoder().encodeToString(content)));
    }

    private Document getDocumentItemDocument(Long itemId, String reference) {
        return itemsRepository.findById(itemId)
                .getPictures()
                .stream()
                .filter(it -> Objects.equals(it.getReference(), reference)).findFirst()
                .orElseThrow(ResourceNotFoundException::new);
    }

    private void addDownloadLink(Long itemId, DocumentResponseResource documentResource) {
        String url = "api/v1/items/%s/pictures/%s/download";

        final Link downloadPictureLink = Link.of(String.format(url, itemId, documentResource.getReference()))
                .withType("GET")
                .withRel("download");

        documentResource.add(downloadPictureLink);
    }

}
