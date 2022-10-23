package utfpr.edu.br.estacionamentoutfpr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import utfpr.edu.br.estacionamentoutfpr.model.CarBrandModelDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class test {

    @Test
    public void testaModeloFinal() {
        List<CarBrandModelDTO> cars = new ArrayList<CarBrandModelDTO>();
        CarBrandModelDTO car = new CarBrandModelDTO();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CarBrandModelDTO.URL_BRAND))
                .header("Content-Type", "application/json")
                .header("chave" , CarBrandModelDTO.KEY)
                .POST(HttpRequest.BodyPublishers.ofString(CarBrandModelDTO.JSON_REFERENCE_TABLE +
                                CarBrandModelDTO.REFERENCE_TABLE +
                                CarBrandModelDTO.JSON_VEICLE_TYPE + "}"))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                     car.setJson(response.replace("Label", "label").replace("Value","value"));
                })
                .join();
        while(car.getJson() == "") {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            System.out.println(car.getJson());
            cars = new ObjectMapper().readValue(car.getJson(),  new TypeReference<List<CarBrandModelDTO>>(){});
            car.setJson(null);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(cars);
    }

    @Test
    public void testaModelo() {
        String body = "{\n" +
                "\t\"codigoTabelaReferencia\": " + CarBrandModelDTO.REFERENCE_TABLE + ",\n" +
                "\t\"codigoTipoVeiculo\": " + CarBrandModelDTO.VEICLE_TYPE +"\n" +
                "}";
        System.out.println(body);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CarBrandModelDTO.URL_BRAND))
                .header("Content-Type", "application/json")
                .header("chave" , "$2y$10$8IAZn7HKq7QJWbh37N3GOOeRVv")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        String teste = "";
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    try {
                        response = response.replace("Label", "label").replace("Value","value");
                        List<CarBrandModelDTO> cars = new ObjectMapper().readValue(response,  new TypeReference<List<CarBrandModelDTO>>(){});
                        System.out.println(cars);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .join();
        //new ObjectMapper().readValue( , CarBrandDTO[].class);
        //new ObjectMapper().readValue( ,  new TypeReference<List<CarBrandDTO>>(){});

    }
}
