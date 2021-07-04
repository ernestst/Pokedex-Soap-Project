package pl.edu.pb.wi.pokemon.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import pl.edu.pb.wi.core.dao.AbstractPageableDAO;
import pl.edu.pb.wi.pokemon.dto.PokemonData;

public class PokemonDAO extends AbstractPageableDAO<PokemonData> {

    public PokemonDAO() {
        super(PokemonData.class);
    }

    public PokemonData getPokemon(int nationalDex) {
        List<String> conditions = Collections.singletonList("e.nationalDex = :nationalDex");
        Map<String, Object> parameters = Collections.singletonMap("nationalDex", nationalDex);
        return getEntity(conditions, parameters);
    }

    public PokemonData getPokemon(String code) {
        List<String> conditions = Collections.singletonList("e.code = :pokeCode");
        Map<String, Object> parameters = Collections.singletonMap("pokeCode", code);
        return getEntity(conditions, parameters);
    }
}
