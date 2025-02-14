package neoris.infraestructure.adapters.out.sql.mapper;

import neoris.domain.Client;
import neoris.infraestructure.adapters.out.sql.entity.ClientEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientEntityMapper {
    Client toClient(ClientEntity clientEntity);

    List<Client> toClients(List<ClientEntity> clientEntities);

    ClientEntity toClientEntity(Client client);
}
