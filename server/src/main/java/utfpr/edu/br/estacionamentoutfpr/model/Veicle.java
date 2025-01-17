package utfpr.edu.br.estacionamentoutfpr.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Veicle {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    private String make;

    private String model;

    private String year;

    @NotNull(message = "A placa não pode ser nula")
    private String licensePlate;

    private String color;

    private String documentFileName;

    @ManyToOne
    private Operator operator;
}
