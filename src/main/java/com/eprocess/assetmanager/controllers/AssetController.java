package com.eprocess.assetmanager.controllers;

import com.eprocess.assetmanager.dtos.ResponseDto;
import com.eprocess.assetmanager.dtos.asset.AssetResponseDto;
import com.eprocess.assetmanager.dtos.asset.CreateAssetRequestDto;
import com.eprocess.assetmanager.dtos.asset.ReturnAssetRequestDto;
import com.eprocess.assetmanager.exceptions.AssetReturnedException;
import com.eprocess.assetmanager.exceptions.NotFoundException;
import com.eprocess.assetmanager.services.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/assets")
@Validated
public class AssetController {
    private final AssetService service;

    @GetMapping(path = "/{assetId}")
    public ResponseEntity<ResponseDto> getSingleUserAsset(@PathVariable Long assetId) {
        try {
            AssetResponseDto data = service.getSingleUserAsset(assetId);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.builder()
                    .data(data)
                    .message("asset fetched successfully")
                    .status("success")
                    .build());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.builder()
                    .data(null)
                    .message("asset not found")
                    .status("error")
                    .build());
        }

    }
    @GetMapping
    public ResponseEntity<ResponseDto> getAllUserAssets() {
        List<AssetResponseDto> data = service.getAllUserAssets();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.builder()
                .data(data)
                .message("assets fetched successfully")
                .status("success")
                .build());
    }
    @PostMapping
    public ResponseEntity<ResponseDto> createUserAsset(@Valid @RequestBody CreateAssetRequestDto request) {
        AssetResponseDto data = service.createUserAsset(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.builder()
                .data(data)
                .message("asset created successfully")
                .status("success")
                .build());
    }
    @PatchMapping
    public ResponseEntity<ResponseDto> returnUserAsset(@RequestBody ReturnAssetRequestDto request) {
        try {
            AssetResponseDto data = service.returnUserAsset(request);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.builder()
                    .data(data)
                    .message("asset returned successfully")
                    .status("success")
                    .build());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.builder()
                    .data(null)
                    .message("asset not found")
                    .status("error")
                    .build());
        } catch (AssetReturnedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.builder()
                    .data(null)
                    .message("asset already returned")
                    .status("error")
                    .build());
        }
    }
}
