package utfpr.edu.br.estacionamentoutfpr.model;

import lombok.Data;

import java.util.List;

@Data
public class ModelDTO {

    private List<CarBrandModelDTO> carBrandModelDTO;

    private List<YearModelDTO> anos;
}
