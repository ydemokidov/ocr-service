package ru.t1.yd.ocrservice;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import ru.t1.yd.ocrservice.controller.OcrController;
import ru.t1.yd.ocrservice.service.OcrService;
import ru.t1.yd.ocrservice.utils.CommonUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebFluxTest(controllers = OcrController.class)
@ContextConfiguration(classes = OcrServiceApplication.class)
@Import(OcrService.class)
class OcrControllerTest {

    final String priceImageFileName = "price.bmp";
    final String priceImageText = "Текущая цена 420 000 000";
    final Integer priceImageIntegerValue = 420000000;
    final String[] priceImageTextContainsStr = new String[]{"екущая ц", "ку", "тек"};
    final String[] priceImageTextNotContainsStr = new String[]{"кащ", "тик", " цина"};
    final String[] priceImageTextContainsDigits = new String[]{"42", "420 000 000", "20"};
    final String[] priceImageTextNotContainsDigits = new String[]{"51", "430", "220"};

    final String textEndpointUrl = "/ocr/text";
    final String numEndpointUrl = "/ocr/num";
    final String searchPatEndpointUrl = "/ocr/searchpat";

    @Autowired
    private WebTestClient webClient;

    @Test
    void fullTextEndpointTest() throws IOException {
        String responseString = sendPriceImageToEndpoint(textEndpointUrl,null);
        Assertions.assertEquals(priceImageText.toUpperCase(),responseString);
    }

    @Test
    void numEndpointTest() throws IOException {
        String responseString = sendPriceImageToEndpoint(numEndpointUrl,null);
        Assertions.assertEquals(priceImageIntegerValue,Integer.parseInt(responseString));
    }

    @Test
    void searchPatEndpointPositiveTextTest() throws IOException {
        for(final String pattern : priceImageTextContainsStr){
            String responseString = sendPriceImageToEndpoint(searchPatEndpointUrl,pattern);
            Assertions.assertEquals("true",responseString);
        }
    }

    @Test
    void searchPatEndpointNegativeTextTest() throws IOException {
        for(final String pattern : priceImageTextNotContainsStr){
            String responseString = sendPriceImageToEndpoint(searchPatEndpointUrl,pattern);
            Assertions.assertEquals("false",responseString);
        }
    }

    @Test
    void searchPatEndpointPositiveDigitsTest() throws IOException {
        for(final String pattern : priceImageTextContainsDigits){
            String responseString = sendPriceImageToEndpoint(searchPatEndpointUrl,pattern);
            Assertions.assertEquals("true",responseString);
        }
    }

    @Test
    void searchPatEndpointNegativeDigitsTest() throws IOException {
        for(final String pattern : priceImageTextNotContainsDigits){
            String responseString = sendPriceImageToEndpoint(searchPatEndpointUrl,pattern);
            Assertions.assertEquals("false",responseString);
        }
    }


    private String sendPriceImageToEndpoint(@NotNull final String url, @Nullable String pattern) throws IOException {
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("file", Files.readAllBytes(getPriceImageFullPath()))
                .header("Content-Disposition", "form-data; name=file; filename="+priceImageFileName);

        if(pattern!=null) multipartBodyBuilder.part("pattern", pattern, MediaType.TEXT_PLAIN)
                .header("Content-Disposition", "form-data; name=pattern")
                .header("Content-type", "text/plain");

        byte[] responseBytes = webClient.post().uri(url)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .exchange()
                .expectStatus().isOk().expectBody().returnResult().getResponseBody();

        return new String(responseBytes);
    }

    private Path getPriceImageFullPath() {
        String resourcesPathString = CommonUtil.getResourceDirectoryPath();
        return Path.of(resourcesPathString + "/" + priceImageFileName);
    }

}