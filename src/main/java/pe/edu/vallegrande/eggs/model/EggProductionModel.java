package pe.edu.vallegrande.eggs.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Table("egg_production")
public class EggProductionModel {

    @Id
    private Integer id;

    @Column("quantity_eggs")
    private Integer quantityEggs;

    @Column("eggs_kilo")
    private Integer eggsKilo;

    @Column("price_kilo")
    private BigDecimal priceKilo;

    @Column("registration_date")
    private LocalDate registrationDate;

    @Column("estado")
    private String estado;
}
