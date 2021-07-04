package pl.edu.pb.wi.ws.pokedex;

import java.awt.Image;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.MTOM;
import javax.xml.ws.soap.SOAPBinding;

@MTOM
@WebService
@BindingType(value = SOAPBinding.SOAP11HTTP_MTOM_BINDING)
@HandlerChain(file = "handler-chain.xml")
public interface ImageService {
    @WebMethod
    String uploadImage(@WebParam(name = "image") Image data, @WebParam(name = "code") String code);

    @WebMethod
    Image downloadImage(@WebParam(name = "code") String code);
}
