package com.mzc.com.singletable.dto;

import java.util.List;

public record OrderResponse(
		String orderId,
		String info,
		Integer amount,
		List<OrderItemResponse> items
) {
}
