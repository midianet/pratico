package midianet.pratico.representation;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AmbienteRep {
    private Integer id;

    @NotNull
    @Size(min = 4 , max = 20)
    private String nome;

    public AmbienteRep(){}

    public AmbienteRep(final Integer id , final String nome){
        this.id = id;
        this.nome = nome;
    }

}