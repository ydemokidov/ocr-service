package ru.t1.yd.ocrservice;

import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.t1.yd.ocrservice.service.OcrService;
import ru.t1.yd.ocrservice.utils.CommonUtil;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OcrServiceApplicationTests {

    @Autowired
    OcrService ocrService;

    //параметры теста чтения с изображения
    final String priceImageFileName = "price.bmp";
    final String priceImageText = "Текущая цена 420 000 000";
    final Integer priceImageIntegerValue = 420000000;
    final String[] priceImageTextContainsStr = new String[]{"екущая ц","ку","тек"};
    final String[] priceImageTextNotContainsStr = new String[]{"кащ","тик"," цина"};
    final String[] getPriceImageTextContainsDigits = new String[]{"42","420 000 000","20"};
    final String[] getPriceImageTextNotContainsDigits = new String[]{"51","430","220"};

    @Test
    void contextLoads() {

    }

    @Test
    void textFromPriceImageTest() throws TesseractException {
        String result = ocrService.getFullStringFromImage(getPriceImageFullPath());
        assertEquals(priceImageText,result);
    }

    @Test
    void numberFromPriceImageTest() throws TesseractException,NumberFormatException {
        Integer result = ocrService.getIntegerFromImage(getPriceImageFullPath());
        assertEquals(priceImageIntegerValue,result);
    }

    @Test
    void imageContainsTextPatternTest() throws TesseractException {
        for(final String pattern : priceImageTextContainsStr){
            boolean result = ocrService.textFromImageContains(getPriceImageFullPath(),pattern);
            assertTrue(result);
        }
    }

    @Test
    void imageNotContainsTextPatternTest() throws TesseractException {
        for(final String pattern : priceImageTextNotContainsStr){
            boolean result = ocrService.textFromImageContains(getPriceImageFullPath(),pattern);
            assertFalse(result);
        }
    }

    @Test
    void imageContainsDigitsPatternTest() throws TesseractException {
        for(final String pattern : getPriceImageTextContainsDigits){
            boolean result = ocrService.textFromImageContains(getPriceImageFullPath(),pattern);
            assertTrue(result);
        }
    }

    @Test
    void imageNotContainsDigitsPatternTest() throws TesseractException {
        for(final String pattern : getPriceImageTextNotContainsDigits){
            boolean result = ocrService.textFromImageContains(getPriceImageFullPath(),pattern);
            assertFalse(result);
        }
    }

    private Path getPriceImageFullPath(){
        String resourcesPathString = CommonUtil.getResourceDirectoryPath();
        return Path.of(resourcesPathString+"/"+priceImageFileName);
    }

}
