package midianet.pratico.representation;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TorreRep{
    private Integer id;

    @NotNull
    @Size(min = 4 , max = 20)
    private String nome;

    public TorreRep(){}

    public TorreRep(final Integer id, final String nome){
        this.id = id;
        this.nome = nome;
    }

}