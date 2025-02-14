package neoris.infraestructure.adapters.in.rest.mapper;

import neoris.domain.Client;
import neoris.infraestructure.adapters.in.rest.dto.ClientDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDTO toClientDTO(Client client);

    List<ClientDTO> toClientDTOs(List<Client> clients);

    Client toClient(ClientDTO clientDTO);
}
