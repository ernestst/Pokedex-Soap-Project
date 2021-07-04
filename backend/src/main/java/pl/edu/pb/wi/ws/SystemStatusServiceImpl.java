package pl.edu.pb.wi.ws;

import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

@WebService(endpointInterface = "pl.edu.pb.wi.ws.SystemStatusService")
@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
public class SystemStatusServiceImpl implements SystemStatusService {
    @Override
    public String getStatus() {
        return "Status: OK";
    }
}
