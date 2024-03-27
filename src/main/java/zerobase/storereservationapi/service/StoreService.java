package zerobase.storereservationapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.storereservationapi.dto.RegisterStore;
import zerobase.storereservationapi.repository.StoreRepository;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public RegisterStore.Response registerStore(RegisterStore.Request request) {
        if (storeRepository.existsByNameAndLocation(
                request.getName(),
                request.getLocation()
        )) {
            throw new RuntimeException();
        }

        return RegisterStore.Response.toDto(
                storeRepository.save(RegisterStore.Request.toEntity(request))
        );
    }
}
