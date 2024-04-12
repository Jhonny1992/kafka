package pe.com.susaya.microservice05.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import pe.com.susaya.microservice05.producer.KafkaProducer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Configuration
@EnableScheduling
public class ReadXML {

    private final String zipFilePath;
    private final KafkaProducer kafkaProducer;
    private static final AtomicInteger idPerson = new AtomicInteger(0);

    public ReadXML(@Value("${file.ruta}") String zipFilePath, KafkaProducer kafkaProducer){
        this.kafkaProducer = kafkaProducer;
        this.zipFilePath = zipFilePath;
    }

    @PostConstruct
    @Scheduled(cron="0 */3 * * * *")
    private void readXmlFromZip(){
        File zipFile = new File(zipFilePath);
        if (zipFile.exists() && zipFile.isFile()) {
            try (FileInputStream zipFilles = new FileInputStream(zipFile);
                 ZipInputStream zipInputStream = new ZipInputStream(zipFilles)) {

                ZipEntry entry;
                while ((entry = zipInputStream.getNextEntry()) != null) {
                    if (!entry.isDirectory() && entry.getName().endsWith(".xml")) {
                        try {
                            String xmlContent = StreamUtils.copyToString(zipInputStream, StandardCharsets.UTF_8);
                            String jsonContent = convertXmlToJson(xmlContent);
                            kafkaProducer.publishMicro05(jsonContent);
                        } catch (IOException e) {
                            System.err.println("Error en procesar archivo");
                        }
                    }
                    zipInputStream.closeEntry();
                }
            } catch (IOException e) {
                System.err.println("Error de lectura");
            }
        } else {
            System.err.println("No existe archivo ZIP");
        }
        idPerson.set(0);
    }

    private static String convertXmlToJson(String xmlContent) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = xmlMapper.readTree(xmlContent);

        JsonNode ageNode = jsonNode.findPath("age");

        JsonNode personNode = jsonNode.get("person");

        if (personNode instanceof ObjectNode) {
            ((ObjectNode) personNode).put("age", ageNode.asText());
            ((ObjectNode) personNode).put("firstName", personNode.get("firstname").asText());
            ((ObjectNode) personNode).put("lastName", personNode.get("lastname").asText());
            ((ObjectNode) personNode).put("firstName2", personNode.get("firstname2").asText());
            ((ObjectNode) personNode).put("lastName2", personNode.get("lastname2").asText());

            ((ObjectNode) personNode).remove("firstname");
            ((ObjectNode) personNode).remove("lastname");
            ((ObjectNode) personNode).remove("firstname2");
            ((ObjectNode) personNode).remove("lastname2");

            int nextId = idPerson.incrementAndGet();
            ((ObjectNode) personNode).put("idPerson", nextId);
        }

        ((ObjectNode) personNode).put("random", jsonNode.get("random").asText());
        ((ObjectNode) personNode).put("randomFloat", jsonNode.get("random_float").asText());
        ((ObjectNode) personNode).put("bool", jsonNode.get("bool").asText());
        ((ObjectNode) personNode).put("dates", jsonNode.get("date").asText());
        ((ObjectNode) personNode).put("regEx", jsonNode.get("regEx").asText());
        ((ObjectNode) personNode).put("enume", jsonNode.get("enum").asText());

        List<String> eltList = new ArrayList<>();
        JsonNode eltNode = jsonNode.get("elt");
        if (eltNode.isArray()) {
            for (JsonNode node : eltNode) {
                eltList.add(node.asText());
            }
        }
        ((ObjectNode) personNode).put("elt", String.join(",", eltList));

        return objectMapper.writeValueAsString(personNode);
    }


}
