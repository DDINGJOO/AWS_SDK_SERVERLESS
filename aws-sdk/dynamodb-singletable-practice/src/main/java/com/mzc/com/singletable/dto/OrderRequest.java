package com.mzc.com.singletable.dto;

import java.util.List;

public record OrderRequest(
		List<OrderItemRequest> items
) {
}
