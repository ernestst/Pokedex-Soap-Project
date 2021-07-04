package pl.edu.pb.wi.pokemon.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pb.wi.core.dto.DTO;
import pl.edu.pb.wi.core.dto.EntityPage;
import pl.edu.pb.wi.ws.resposne.Type;

@Entity
@Table(name = "pokemons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PokemonData extends EntityPage<PokemonData> implements DTO {
    @Id
    @Column(name = "id")
    private int nationalDex;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "primary_type", nullable = false)
    private Type primaryType;

    @Column(name = "secondary_type")
    private Type secondType;
}
