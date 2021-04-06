package word_editor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static word_editor.api.WordEditorConstants.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WordEditorApplicationTest {

    String LOCALHOST = "http://localhost:";

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    String text = "Its a text for test!";

    @BeforeEach
    void before() {
        URI homeURI = UriComponentsBuilder.fromHttpUrl(LOCALHOST + port)
                .path(WORD_EDITOR_ENDPOINT).build().toUri();
        String response = this.restTemplate.getForObject(homeURI, String.class);
        if(response != null) {
            URI removeTextOfPositionURI = UriComponentsBuilder.fromUri(homeURI)
                    .path(REMOVE_PATH)
                    .queryParam("from", 0)
                    .queryParam("to", response.length())
                    .build().toUri();
            this.restTemplate.delete(removeTextOfPositionURI);
        }
    }

    @Test
    public void getTextTest() {
        URI homeURI = UriComponentsBuilder.fromHttpUrl(LOCALHOST + port)
                .path(WORD_EDITOR_ENDPOINT).build().toUri();
        URI addTextURI = UriComponentsBuilder.fromUri(homeURI)
                .queryParam("text", text)
                .build().toUri();
        this.restTemplate.postForObject(addTextURI, null, String.class);

        String response = this.restTemplate.getForObject(homeURI, String.class);
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(text);
    }

    @Test
    public void addTextTest() {
        URI homeURI = UriComponentsBuilder.fromHttpUrl(LOCALHOST + port)
                .path(WORD_EDITOR_ENDPOINT).build().toUri();
        URI addTextURI = UriComponentsBuilder.fromUri(homeURI)
                .queryParam("text", text)
                .build().toUri();

        this.restTemplate.postForObject(addTextURI, null, String.class);
        String response = this.restTemplate.getForObject(homeURI, String.class);
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(text);
    }

    @Test
    public void addTextOfPositionTest() {
        URI homeURI = UriComponentsBuilder.fromHttpUrl(LOCALHOST + port)
                .path(WORD_EDITOR_ENDPOINT).build().toUri();
        URI addTextWithPositionURI = UriComponentsBuilder.fromUri(homeURI)
                .queryParam("text", text)
                .queryParam("position", text.length())
                .build().toUri();
        URI addTextURI = UriComponentsBuilder.fromUri(homeURI)
                .queryParam("text", text)
                .build().toUri();
        this.restTemplate.postForObject(addTextURI, null, String.class);

        this.restTemplate.postForObject(addTextWithPositionURI, null, String.class);
        String response = this.restTemplate.getForObject(homeURI, String.class);
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(text + text);
    }

    @Test
    public void removeTextOfPositionTest() {
        URI homeURI = UriComponentsBuilder.fromHttpUrl(LOCALHOST + port)
                .path(WORD_EDITOR_ENDPOINT).build().toUri();
        URI removeTextOfPositionURI = UriComponentsBuilder.fromUri(homeURI)
                .path(REMOVE_PATH)
                .queryParam("from", 1)
                .queryParam("to", text.length() - 1)
                .build().toUri();
        URI addTextURI = UriComponentsBuilder.fromUri(homeURI)
                .queryParam("text", text)
                .build().toUri();
        this.restTemplate.postForObject(addTextURI, null, String.class);

        this.restTemplate.delete(removeTextOfPositionURI);
        String response = this.restTemplate.getForObject(homeURI, String.class);
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo("I!");
    }

    @Test
    public void italicStyleTest() {
        URI homeURI = UriComponentsBuilder.fromHttpUrl(LOCALHOST + port)
                .path(WORD_EDITOR_ENDPOINT).build().toUri();
        URI italicTextOfPositionURI = UriComponentsBuilder.fromUri(homeURI)
                .path(MODIFY_PATH)
                .queryParam("style", "i")
                .queryParam("from", 0)
                .queryParam("to", text.length())
                .build().toUri();
        URI addTextURI = UriComponentsBuilder.fromUri(homeURI)
                .queryParam("text", text)
                .build().toUri();
        this.restTemplate.postForObject(addTextURI, null, String.class);

        this.restTemplate.postForObject(italicTextOfPositionURI, null, String.class);
        String response = this.restTemplate.getForObject(homeURI, String.class);
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo("<i>" + text + "</i>");
    }

    @Test
    public void boldStyleTest() {
        URI homeURI = UriComponentsBuilder.fromHttpUrl(LOCALHOST + port)
                .path(WORD_EDITOR_ENDPOINT).build().toUri();
        URI italicTextOfPositionURI = UriComponentsBuilder.fromUri(homeURI)
                .path(MODIFY_PATH)
                .queryParam("style", "b")
                .queryParam("from", 0)
                .queryParam("to", text.length())
                .build().toUri();
        URI addTextURI = UriComponentsBuilder.fromUri(homeURI)
                .queryParam("text", text)
                .build().toUri();
        this.restTemplate.postForObject(addTextURI, null, String.class);

        this.restTemplate.postForObject(italicTextOfPositionURI, null, String.class);
        String response = this.restTemplate.getForObject(homeURI, String.class);
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo("<b>" + text + "</b>");
    }

    @Test
    public void underlineStyleTest() {
        URI homeURI = UriComponentsBuilder.fromHttpUrl(LOCALHOST + port)
                .path(WORD_EDITOR_ENDPOINT).build().toUri();
        URI italicTextOfPositionURI = UriComponentsBuilder.fromUri(homeURI)
                .path(MODIFY_PATH)
                .queryParam("style", "u")
                .queryParam("from", 0)
                .queryParam("to", text.length())
                .build().toUri();
        URI addTextURI = UriComponentsBuilder.fromUri(homeURI)
                .queryParam("text", text)
                .build().toUri();
        this.restTemplate.postForObject(addTextURI, null, String.class);

        this.restTemplate.postForObject(italicTextOfPositionURI, null, String.class);
        String response = this.restTemplate.getForObject(homeURI, String.class);
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo("<u>" + text + "</u>");
    }

    @Test
    public void redoUndoStyleTest() {
        URI homeURI = UriComponentsBuilder.fromHttpUrl(LOCALHOST + port)
                .path(WORD_EDITOR_ENDPOINT).build().toUri();
        URI redoTextOfPositionURI = UriComponentsBuilder.fromUri(homeURI)
                .path(REDO_PATH).build().toUri();
        URI undoTextOfPositionURI = UriComponentsBuilder.fromUri(homeURI)
                .path(UNDO_PATH).build().toUri();
        URI addTextURI = UriComponentsBuilder.fromUri(homeURI)
                .queryParam("text", text)
                .build().toUri();
        this.restTemplate.postForObject(addTextURI, null, String.class);

        String response = this.restTemplate.getForObject(homeURI, String.class);
        assertThat(response).isEqualTo(text);

        this.restTemplate.postForObject(undoTextOfPositionURI, null, String.class);
        response = this.restTemplate.getForObject(homeURI, String.class);
        assertThat(response).isNull();

        this.restTemplate.postForObject(redoTextOfPositionURI, null, String.class);
        response = this.restTemplate.getForObject(homeURI, String.class);
        assertThat(response).isEqualTo(text);
    }

}
