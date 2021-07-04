package pl.edu.pb.wi.ws.resposne;

import lombok.Data;

@Data
public class Evolution implements Response {
    private int sourceNationalDex;
    private int evolutionNationalDex;
    private int level;
    private String item;
    private String additional;
}

