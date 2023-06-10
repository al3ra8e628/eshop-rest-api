package com.example.eshop.service.controllers;

import com.example.contract.repositories.ItemsRepository;
import com.example.contract.services.DocumentsService;
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
    public static final String ESHOP_ITEM_PICTURE = "ESHOP_ITEM_PICTURE";
    private final ItemsRepository itemsRepository;
    private final ItemResourceMapper itemResourceMapper;
    private final DocumentsService documentsService;

    public ItemPicturesController(ItemsRepository itemsRepository,
                                  ItemResourceMapper itemResourceMapper,
                                  DocumentsService documentsService) {
        this.itemsRepository = itemsRepository;
        this.itemResourceMapper = itemResourceMapper;
        this.documentsService = documentsService;
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
        final Document itemPicture = getDocumentItemDocument(itemId, reference);

        itemPicture.setContent(documentsService.getDocument(
                itemPicture.getReference(),
                ESHOP_ITEM_PICTURE,
                itemId + ""
        ));

        final byte[] content = itemPicture.getContent();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(itemPicture.getContentType()));

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/{reference}/download", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> downloadPictureEncoded(@PathVariable Long itemId,
                                                      @PathVariable String reference) {
        final Document document = getDocumentItemDocument(itemId, reference);

        document.setContent(documentsService.getDocument(
                document.getReference(),
                "ESHOP_ITEM_PICTURE",
                itemId + ""
        ));

        final String base64Prefix = String.format("data:%s;base64,", document.getContentType());

        return Map.of("content", base64Prefix.concat(
                Base64.getEncoder().encodeToString(document.getContent())));
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
