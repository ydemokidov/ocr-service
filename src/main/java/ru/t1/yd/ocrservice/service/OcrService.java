package ru.t1.yd.ocrservice.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.t1.yd.ocrservice.configuration.TessdataConfiguration;
import ru.t1.yd.ocrservice.utils.CommonUtil;

import java.nio.file.Path;
import java.util.Locale;

@Service
public final class OcrService {

    private final ITesseract tesseractInstance;

    @Autowired
    public OcrService(@NotNull final TessdataConfiguration tessdataConfiguration) {
        this.tesseractInstance = new Tesseract();
        tesseractInstance.setLanguage(tessdataConfiguration.getLang());

        String datapath = CommonUtil.getResourceDirectoryPath()+"\\"+tessdataConfiguration.getPath();
        tesseractInstance.setDatapath(datapath);
    }

    private synchronized String getCharactersFromImage(@NotNull final Path filepath) throws TesseractException{
        return tesseractInstance.doOCR(filepath.toFile()).replaceAll("\n","");
    }

    public String getFullStringFromImage(@NotNull final Path filepath) throws TesseractException{
        return getCharactersFromImage(filepath);
    }

    public Integer getIntegerFromImage(@NotNull final Path filepath) throws TesseractException, NumberFormatException{
        String fullResultString = getCharactersFromImage(filepath);
        String numString = fullResultString.replaceAll("[^\\d]","");
        return Integer.parseInt(numString);
    }

    public boolean textFromImageContains(@NotNull final Path filepath, @NotNull final String pattern) throws TesseractException {
        boolean result = false;
        String fullResultString = getCharactersFromImage(filepath);
        if(fullResultString.toLowerCase(Locale.ROOT).contains(pattern.toLowerCase(Locale.ROOT))) result = true;
        return result;
    }

}
