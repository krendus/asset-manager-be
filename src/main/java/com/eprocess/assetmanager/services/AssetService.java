package com.eprocess.assetmanager.services;

import com.eprocess.assetmanager.dtos.asset.AssetResponseDto;
import com.eprocess.assetmanager.dtos.asset.CreateAssetRequestDto;
import com.eprocess.assetmanager.dtos.asset.ReturnAssetRequestDto;
import com.eprocess.assetmanager.exceptions.AssetReturnedException;
import com.eprocess.assetmanager.exceptions.NotAuthenticatedException;
import com.eprocess.assetmanager.exceptions.NotFoundException;
import com.eprocess.assetmanager.models.Asset;
import com.eprocess.assetmanager.models.User;
import com.eprocess.assetmanager.repositories.AssetRepository;
import com.eprocess.assetmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.eprocess.assetmanager.services.UserService.getUserDetails;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetService {
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AssetResponseDto getSingleUserAsset(Long assetId) throws RuntimeException {
        User user = getCurrentUser();
        if(user == null) {
            throw new NotAuthenticatedException("unauthenticated");
        }
        Optional<Asset> asset = assetRepository.getAssetByIdAndUserId(assetId, user.getId());
        if(asset.isEmpty()) {
            throw new NotFoundException("asset not found");
        }
        return modelMapper.map(asset.get(), AssetResponseDto.class);
    }

    @Transactional
    public List<AssetResponseDto> getAllUserAssets() throws RuntimeException {
        User authUser = getCurrentUser();
        if(authUser == null) {
            throw new NotAuthenticatedException("user not authenticated");
        }
        Optional<User> userWithAssets = userRepository.findById(authUser.getId());
        if(userWithAssets.isEmpty()) {
            throw new NotAuthenticatedException("unauthenticated");
        }
        List<Asset> assets = userWithAssets.get().getAssets();
        return assets.stream().map(asset -> modelMapper.map(asset, AssetResponseDto.class)).collect(Collectors.toList());
    }
    public AssetResponseDto createUserAsset(CreateAssetRequestDto request) throws RuntimeException {
        User authUser = getCurrentUser();
        if(authUser == null) {
            throw new NotAuthenticatedException("unauthenticated");
        }
        Optional<User> userWithAssets = userRepository.findById(authUser.getId());
        if(userWithAssets.isEmpty()) {
            throw new NotAuthenticatedException("unauthenticated");
        }
        User user = userWithAssets.get();
        Asset asset = Asset.builder()
                .imageURL(request.getImageURL())
                .name(request.getName())
                .receivedDate(request.getReceivedDate())
                .accessories(request.getAccessories())
                .createdAt(new Date())
                .updatedAt(new Date())
                .receivedDate(request.getReceivedDate())
                .isReturned(false)
                .build();
        asset.setUser(user);
        user.getAssets().add(asset);
        Asset savedAsset = assetRepository.save(asset);
        return modelMapper.map(savedAsset, AssetResponseDto.class);
    }
    public AssetResponseDto returnUserAsset(ReturnAssetRequestDto request) throws RuntimeException {
        User user = getCurrentUser();
        if(user == null) {
            throw new NotAuthenticatedException("unauthenticated");
        }
        Optional<Asset> result = assetRepository.findById(request.getId());
        if(result.isEmpty()) {
            throw new NotFoundException("asset not found");
        }
        Asset asset = result.get();
        if(asset.getIsReturned()) {
            throw new AssetReturnedException("asset returned");
        }
        asset.setIsReturned(true);
        asset.setReturnImageURL(request.getReturnImageURL());
        asset.setReturnReason(request.getReturnReason());
        asset.setReturnDate(request.getReturnDate());
        Asset savedAsset = assetRepository.save(asset);
        return modelMapper.map(savedAsset, AssetResponseDto.class);
    }
    private User getCurrentUser() {
        return getUserDetails();
    }
}
