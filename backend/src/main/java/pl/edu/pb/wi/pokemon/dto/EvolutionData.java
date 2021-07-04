package pl.edu.pb.wi.pokemon.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pb.wi.core.dto.DTO;

@Entity
@Table(name = "evolutions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EvolutionData implements DTO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "pokemon_id", nullable = false)
    private int pokemonNationalDex;

    @Column(name = "evolution_id", nullable = false)
    private int evolutionNationalDex;

    @Column(name = "level")
    private int requiredLevel;

    @Column(name = "item")
    private String requiredItem;

    @Column(name = "additional")
    private String additionalRequirements;
}
