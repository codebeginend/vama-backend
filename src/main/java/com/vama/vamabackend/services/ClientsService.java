package com.vama.vamabackend.services;

import com.vama.vamabackend.models.clients.ClientsAdminResponse;
import com.vama.vamabackend.persistence.entity.users.UserEntity;
import com.vama.vamabackend.persistence.repository.UsersJdbcRepository;
import com.vama.vamabackend.persistence.repository.UsersRepository;
import com.vama.vamabackend.persistence.repository.types.UsersAdminType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientsService {

    private UsersJdbcRepository usersJdbcRepository;
    private UsersRepository usersRepository;

    public List<ClientsAdminResponse> findAllClientsForAdmin(String searchText){
        List<UsersAdminType> clientTypes = usersJdbcRepository.getAllForAdmin(searchText);
        List<ClientsAdminResponse> response = clientTypes.stream().map(m ->
                ClientsAdminResponse.builder()
                        .id(m.getId())
                        .name(m.getName())
                        .phoneNumber(m.getPhoneNumber())
                        .orderCount(m.getOrderCount())
                        .totalSum(m.getTotalSum())
                        .build()).collect(Collectors.toList());
        return response;
    }

    public ClientsAdminResponse findDetailsByIdForAdmin(Long id){
        try {
            UserEntity userEntity = usersRepository.findByClient(id);
            ClientsAdminResponse response = ClientsAdminResponse.builder()
                    .id(userEntity.getId())
                    .name(userEntity.getName())
                    .phoneNumber(userEntity.getUsername())
                    .email(userEntity.getEmail())
                    .build();
            return response;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }
}
