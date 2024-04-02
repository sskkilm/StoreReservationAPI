package zerobase.storereservationapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.storereservationapi.domain.Store;
import zerobase.storereservationapi.dto.*;
import zerobase.storereservationapi.embedded.Location;
import zerobase.storereservationapi.repository.StoreRepository;

import java.util.List;

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

        // 요청된 매장 이름과 위치가 동일한 매장이 이미 존재하는 경우 예외 처리
        if (storeRepository.existsByNameAndLocation(
                request.getName(),
                request.getLocation()
        )) {
            throw new RuntimeException("이미 존재하는 매장입니다.");
        }

        store.updateStore(request.getName(), request.getLocation(), request.getDescription());

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

    public List<StoreDto> getStoreListOrderByAlphabet() {
        return storeRepository.findAllByOrderByName().stream()
                .map(StoreDto::toDto).toList();
    }

    public List<StoreDto> getStoreListOrderByDistance(Double latitude, Double longitude) {
        List<Store> storeList = storeRepository.findAll();

        return storeList.stream().sorted((o1, o2) -> {
            // 두 지점의 거리차를 기준으로 오름차순 정렬
            Double distDiff = getDistanceDifference(o1.getLocation(), o2.getLocation(), latitude, longitude);
            if (distDiff < 0) {
                return -1;
            } else if (distDiff == 0) {
                return 0;
            } else {
                return 1;
            }
        }).map(StoreDto::toDto).toList();
    }

    private Double getDistanceDifference(Location o1, Location o2, Double latitude, Double longitude) {
        // 두 지점의 거리 차 계산
        return calculateDistance(o1.getLatitude(), o1.getLongitude(), latitude, longitude)
                - calculateDistance(o2.getLatitude(), o2.getLongitude(), latitude, longitude);
    }

    private Double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        // 위도 및 경도를 라디안으로 변환
        double radLat1 = toRadians(lat1);
        double radLon1 = toRadians(lon1);
        double radLat2 = toRadians(lat2);
        double radLon2 = toRadians(lon2);

        // 위도 차이와 경도 차이 계산
        double deltaLat = radLat2 - radLat1;
        double deltaLon = radLon2 - radLon1;

        // Haversine 공식을 사용하여 거리 계산
        double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 결과값 반환 (지구 반지름 = 6371) (단위: km)
        return 6371 * c;
    }

    private Double toRadians(Double value) {
        return Math.toRadians(value);
    }

    public List<StoreRatingDto> getStoreListOrderByRating() {
        List<Store> storeList = storeRepository.findAll();

        // 별점 순으로 내림차순 정렬 (별점 높은 순 정렬)
        return storeList.stream().sorted((o1, o2) -> {
            Double rating1 = o1.getRatingSum() / o1.getReviewCount();
            Double rating2 = o2.getRatingSum() / o2.getReviewCount();
            if (rating1 - rating2 < 0) {
                return 1;
            } else if (rating1 - rating2 == 0) {
                return 0;
            } else {
                return -1;
            }
        }).map(e -> StoreRatingDto.builder()
                .rating(Double.parseDouble(String.format("%.2f", e.getRatingSum() / e.getReviewCount())))
                .name(e.getName())
                .location(e.getLocation())
                .build()
        ).toList();

    }
}
