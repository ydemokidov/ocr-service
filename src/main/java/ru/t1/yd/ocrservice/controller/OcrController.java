package ru.t1.yd.ocrservice.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.t1.yd.ocrservice.service.OcrService;

import java.nio.file.Path;

@RestController
@RequestMapping("ocr")
public class OcrController {

    private final OcrService ocrService;

    @Autowired
    public OcrController(@NotNull final OcrService ocrService) {
        this.ocrService = ocrService;
    }

    @PostMapping(value = "text", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> processImageForText(@RequestPart("file") final FilePart filePart) {
        try {
            Path fileToCopy = Path.of("tstFile.bmp");
            System.out.println(fileToCopy.toFile().getAbsolutePath());
            return Mono.from(filePart.transferTo(fileToCopy)).thenReturn(ocrService.getFullStringFromImage(fileToCopy));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

}
