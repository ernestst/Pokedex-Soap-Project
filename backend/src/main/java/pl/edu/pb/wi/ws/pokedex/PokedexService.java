package pl.edu.pb.wi.ws.pokedex;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.soap.SOAPException;
import pl.edu.pb.wi.ws.resposne.Page;
import pl.edu.pb.wi.ws.resposne.Pokemon;
import pl.edu.pb.wi.ws.resposne.Type;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
@HandlerChain(file = "handler-chain.xml")
public interface PokedexService {
    @WebMethod
    Pokemon getPokemonById(@WebParam(name = "dex") int dex);

    @WebMethod
    Pokemon getPokemonByCode(@WebParam(name = "code") String code);

    @WebMethod
    Page<Pokemon> getPokemonsByName(@WebParam(name = "page") int page, @WebParam(name = "name") String name) throws Exception;

    @WebMethod
    Page<Pokemon> getPokemonsByType(@WebParam(name = "page") int page, @WebParam(name = "type") Type [] types) throws Exception;

    @WebMethod
    Page<Pokemon> getPokemonsAdvanced(@WebParam(name = "page") int page, @WebParam(name = "name") String name, @WebParam(name = "type") Type [] types) throws Exception;

    @WebMethod
    Page<Pokemon> getPokemons(@WebParam(name = "page") int page) throws Exception;

    @WebMethod
    void savePokemon(@WebParam(name = "pokemon") Pokemon pokemon) throws SOAPException;
}
