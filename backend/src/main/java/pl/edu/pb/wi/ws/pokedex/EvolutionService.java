package pl.edu.pb.wi.ws.pokedex;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.soap.SOAPException;
import pl.edu.pb.wi.ws.resposne.Evolution;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
@HandlerChain(file = "handler-chain.xml")
public interface EvolutionService {
    @WebMethod
    Evolution [] getPokemonEvolutions(@WebParam(name = "dex") int dex);

    @WebMethod
    void savePokemonEvolution(@WebParam(name = "evolutionDetails") Evolution evolution) throws SOAPException;
}
