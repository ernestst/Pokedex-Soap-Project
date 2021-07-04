package pl.edu.pb.wi.pokemon.mapper;

import pl.edu.pb.wi.core.mapper.Mapper;
import pl.edu.pb.wi.pokemon.dto.EvolutionData;
import pl.edu.pb.wi.ws.resposne.Evolution;

public class EvolutionMapper implements Mapper<EvolutionData, Evolution> {
    @Override
    public EvolutionData map(Evolution wsResponse) {
        EvolutionData evolution = new EvolutionData();
        evolution.setPokemonNationalDex(wsResponse.getSourceNationalDex());
        evolution.setEvolutionNationalDex(wsResponse.getEvolutionNationalDex());
        evolution.setRequiredItem(wsResponse.getItem());
        evolution.setRequiredLevel(wsResponse.getLevel());
        evolution.setAdditionalRequirements(wsResponse.getAdditional());

        return evolution;
    }

    @Override
    public Evolution map(EvolutionData entity) {
        Evolution evolution = new Evolution();
        evolution.setSourceNationalDex(entity.getPokemonNationalDex());
        evolution.setEvolutionNationalDex(entity.getEvolutionNationalDex());
        evolution.setLevel(entity.getRequiredLevel());
        evolution.setItem(entity.getRequiredItem());
        evolution.setAdditional(entity.getAdditionalRequirements());

        return evolution;
    }
}
