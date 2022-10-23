package utfpr.edu.br.estacionamentoutfpr.service;

import utfpr.edu.br.estacionamentoutfpr.model.CarBrandModelDTO;
import utfpr.edu.br.estacionamentoutfpr.model.Veicle;

import java.util.List;
import java.util.UUID;

public interface VeicleService extends CrudService<Veicle, UUID> {

    public List<CarBrandModelDTO> getCarBrands();
}
