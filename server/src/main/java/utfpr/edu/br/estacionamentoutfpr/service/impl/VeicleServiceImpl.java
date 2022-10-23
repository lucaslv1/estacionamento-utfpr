package utfpr.edu.br.estacionamentoutfpr.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import utfpr.edu.br.estacionamentoutfpr.model.CarBrandModelDTO;
import utfpr.edu.br.estacionamentoutfpr.model.Veicle;
import utfpr.edu.br.estacionamentoutfpr.repository.VeicleRepository;
import utfpr.edu.br.estacionamentoutfpr.service.VeicleService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class VeicleServiceImpl extends CrudServiceImpl<Veicle, UUID> implements VeicleService {

    private final VeicleRepository veicleRepository;

    @Override
    protected JpaRepository<Veicle, UUID> getRepository() {
        return this.veicleRepository;
    }

    /*
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
     */
    @Override
    public List<CarBrandModelDTO> getCarBrands() {
        List<CarBrandModelDTO> brands = new ArrayList<CarBrandModelDTO>();
        CarBrandModelDTO brand = new CarBrandModelDTO();
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
                    brand.setJson(response.replace("Label", "label").replace("Value","value"));
                })
                .join();
        while(brand.getJson().equals("")) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            brands = new ObjectMapper().readValue(brand.getJson(),  new TypeReference<List<CarBrandModelDTO>>(){});
            brand.setJson(null);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return brands;
    }
}
