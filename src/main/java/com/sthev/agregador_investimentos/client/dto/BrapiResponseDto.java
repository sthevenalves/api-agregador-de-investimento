package com.sthev.agregador_investimentos.client.dto;

import java.util.List;

public record BrapiResponseDto(List<BrapiStockDto> result) {
}
