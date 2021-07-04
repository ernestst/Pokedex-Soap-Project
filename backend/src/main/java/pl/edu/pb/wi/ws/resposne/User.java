package pl.edu.pb.wi.ws.resposne;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name="credentials")
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Response {
    @XmlElement(required = true)
    private String login;

    @XmlElement(required = true)
    private String password;
}
