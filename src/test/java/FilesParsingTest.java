import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import model.JsonRoot;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class FilesParsingTest {
    ClassLoader cl = FilesParsingTest.class.getClassLoader();

    @Test
    void jsonParseGsonTest() throws IOException {
        Gson gson = new Gson();

        try (InputStream is = cl.getResourceAsStream("jsonArrayTestNoZip.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            JsonRoot jr = gson.fromJson(isr, JsonRoot.class);

            assertThat(jr.getMembers().get(0).getName()).startsWith("Mol");
            assertThat(jr.getMembers().get(1).getAge()).isEqualTo(39);
            assertThat(jr.getMembers().get(2).getPowers()).containsSequence("Inferno", "Teleportation");
        }
    }

    @Test
    void jsonParseJacksonTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream is = cl.getResourceAsStream("jsonArrayTestNoZip.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            JsonRoot jr = objectMapper.readValue(is, JsonRoot.class);

            assertThat(jr.getMembers().get(0).getName()).endsWith("Man");
            assertThat(jr.getMembers().get(1).getSecretIdentity()).hasToString("Jane Wilson");
            assertThat(jr.isActive()).isTrue();
        }
    }
}
