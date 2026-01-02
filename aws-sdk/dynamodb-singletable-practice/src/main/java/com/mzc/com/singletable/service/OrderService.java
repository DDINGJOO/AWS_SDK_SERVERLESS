package com.mzc.com.singletable.service;

import com.mzc.com.singletable.dto.*;
import com.mzc.com.singletable.entity.ShopData;
import com.mzc.com.singletable.repository.ShopDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final ShopDataRepository shopDataRepository;
	
	public OrderCreateResponse createOrder(OrderRequest request) {
		// 주문 ID 생성: ORD_년월일시분초_UUID
		String orderId = generateOrderId();
		
		List<ShopData> shopDataList = new ArrayList<>();
		
		// 주문 총액 계산
		int totalAmount = request.items().stream()
				.mapToInt(OrderItemRequest::price)
				.sum();
		
		// 1. 주문 정보(ORDER) 저장
		ShopData orderInfo = new ShopData();
		orderInfo.setPk(orderId);
		orderInfo.setSk("INFO");
		orderInfo.setType("ORDER");
		orderInfo.setInfo("주문 성공");
		orderInfo.setAmount(totalAmount);
		shopDataList.add(orderInfo);
		
		// 2. 주문 내역(ITEM) 저장
		int itemIndex = 1;
		for (OrderItemRequest item : request.items()) {
			ShopData itemData = new ShopData();
			itemData.setPk(orderId);
			itemData.setSk(String.format("ITEM#%03d", itemIndex));
			itemData.setType("ITEM");
			itemData.setInfo(item.name());
			itemData.setAmount(item.price());
			shopDataList.add(itemData);
			itemIndex++;
		}
		
		// DynamoDB에 저장
		shopDataRepository.saveAll(shopDataList);
		
		return new OrderCreateResponse(orderId, "주문이 성공적으로 생성되었습니다.");
	}
	
	public OrderResponse getOrder(String orderId) {
		// 한 번의 쿼리로 주문 정보와 주문 내역 모두 조회
		List<ShopData> dataList = shopDataRepository.findByPk(orderId);
		
		if (dataList.isEmpty()) {
			return null;
		}
		
		String info = null;
		Integer totalAmount = null;
		List<OrderItemResponse> items = new ArrayList<>();
		
		for (ShopData data : dataList) {
			if ("ORDER".equals(data.getType())) {
				// 주문 정보
				info = data.getInfo();
				totalAmount = data.getAmount();
			} else if ("ITEM".equals(data.getType())) {
				// 주문 내역
				items.add(new OrderItemResponse(data.getInfo(), data.getAmount()));
			}
		}
		
		return new OrderResponse(orderId, info, totalAmount, items);
	}
	
	private String generateOrderId() {
		String timestamp = LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String uuid = UUID.randomUUID().toString().substring(0, 4);
		return "ORD_" + timestamp + "_" + uuid;
	}
}
