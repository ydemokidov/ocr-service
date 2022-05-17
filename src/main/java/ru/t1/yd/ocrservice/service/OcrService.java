package ru.t1.yd.ocrservice.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.t1.yd.ocrservice.configuration.TessdataConfiguration;
import ru.t1.yd.ocrservice.utils.CommonUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

@Service
public final class OcrService {

    private static final String FAIL_STRING = "***OCR-SERVICE FAILURE!***";

    private final ITesseract tesseractInstance;

    @Autowired
    public OcrService(@NotNull final TessdataConfiguration tessdataConfiguration) {
        this.tesseractInstance = new Tesseract();
        tesseractInstance.setLanguage(tessdataConfiguration.getLang());

        String datapath = CommonUtil.getResourceDirectoryPath() + "\\" + tessdataConfiguration.getPath();
        tesseractInstance.setDatapath(datapath);
    }

    public Mono<String> getFullStringFromImage(@NotNull final FilePart filePart){
        return getCharactersFromFilePart(filePart);
    }

    public Mono<String> getIntegerFromImage(@NotNull final FilePart filePart){
        return getCharactersFromFilePart(filePart).map(s -> s.replaceAll("[^\\d]", ""));
    }

    public Mono<String> textFromImageContains(@NotNull final FilePart filePart, @NotNull final String patternToSearch){
        return getCharactersFromFilePart(filePart).map(s -> {
            if(s.contains(patternToSearch)) return "true";
            return "false";
        });
    }

    private synchronized String getCharactersFromImage(@NotNull BufferedImage image) throws TesseractException {
        return tesseractInstance.doOCR(image);
    }

    private String getCharactersFromImageBytes(byte[] imageBytes){
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(inputStream);
            return getCharactersFromImage(image);
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL_STRING;
        }
    }

    private Mono<String> getCharactersFromFilePart(@NotNull final FilePart filePart) {
        return Mono.from(filePart.content().map(dataBuffer -> dataBuffer.asByteBuffer().array())
                .reduce(CommonUtil::concatByteArrays)
                .map(this::getCharactersFromImageBytes));
    }

}
