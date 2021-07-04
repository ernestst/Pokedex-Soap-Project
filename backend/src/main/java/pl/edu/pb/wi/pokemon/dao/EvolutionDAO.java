package pl.edu.pb.wi.pokemon.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import pl.edu.pb.wi.core.dao.AbstractDAO;
import pl.edu.pb.wi.pokemon.dto.EvolutionData;

public class EvolutionDAO extends AbstractDAO<EvolutionData> {
    public EvolutionDAO() {
        super(EvolutionData.class);
    }

    public List<EvolutionData> getEvolutionDetails(int nationalDex) {
        List<String> conditions = Collections.singletonList("e.pokemonNationalDex = :nationalDex");
        Map<String, Object> parameters = Collections.singletonMap("nationalDex", nationalDex);
        return getResults(conditions, parameters);
    }
}
