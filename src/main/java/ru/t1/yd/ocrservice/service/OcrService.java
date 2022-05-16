package ru.t1.yd.ocrservice.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.t1.yd.ocrservice.configuration.TessdataConfiguration;

import java.nio.file.Path;

@Service
public final class OcrService {

    private final ITesseract tesseractInstance;

    @Autowired
    public OcrService(@NotNull TessdataConfiguration tessdataConfiguration) {
        this.tesseractInstance = new Tesseract();
        tesseractInstance.setLanguage(tessdataConfiguration.getLang());
        tesseractInstance.setDatapath(tessdataConfiguration.getPath());
    }

    public synchronized String getCharactersFromImage(@NotNull final Path filepath){
        try {
            return tesseractInstance.doOCR(filepath.toFile());
        }catch (TesseractException e){
            e.printStackTrace();
        }
        return "";
    }

}
