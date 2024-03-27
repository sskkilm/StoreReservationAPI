package zerobase.storereservationapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.storereservationapi.domain.Store;
import zerobase.storereservationapi.dto.DeleteStore;
import zerobase.storereservationapi.dto.RegisterStore;
import zerobase.storereservationapi.dto.StoreDto;
import zerobase.storereservationapi.dto.UpdateStore;
import zerobase.storereservationapi.repository.StoreRepository;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public RegisterStore.Response registerStore(RegisterStore.Request request) {
        // 동일한 매장이 이미 존재하는 경우 예외 처리
        if (storeRepository.existsByNameAndLocation(
                request.getName(),
                request.getLocation()
        )) {
            throw new RuntimeException("이미 존재하는 매장입니다.");
        }

        return RegisterStore.Response.toDto(
                storeRepository.save(RegisterStore.Request.toEntity(request))
        );
    }

    @Transactional
    public UpdateStore.Response updateStore(Long id, UpdateStore.Request request) {
        // 수정하고자 하는 매장이 없을 경우 예외 처리
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("없는 매장입니다."));

        store.updateStore(request.getName(), request.getLocation(), request.getDescription());

        // 수정한 매장과 동일한 매장이 존재하는 경우 예외 처리
        if (storeRepository.existsByNameAndLocation(
                store.getName(),
                store.getLocation()
        )) {
            throw new RuntimeException("이미 존재하는 매장입니다.");
        }

        return UpdateStore.Response.toDto(store);
    }

    public DeleteStore.Response deleteStore(Long id) {
        // 삭제하고자 하는 매장이 없을 경우 예외 처리
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("없는 매장입니다."));

        storeRepository.delete(store);

        return DeleteStore.Response.toDto(store);
    }

    public StoreDto getStoreDetails(Long id) {
        // 조회하고자 하는 매장이 없을 경우 예외 처리
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("없는 매장입니다."));

        return StoreDto.toDto(store);
    }
}
