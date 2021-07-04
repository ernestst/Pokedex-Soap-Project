package pl.edu.pb.wi.ws.resposne;

import lombok.Data;

@Data
public class Pokemon implements Response {
    private int nationalDex;
    private String code;
    private String name;
    private Type primaryType;
    private Type secondType;
}

