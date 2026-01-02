package com.mzc.com.singletable.controller;

import com.mzc.com.singletable.dto.OrderCreateResponse;
import com.mzc.com.singletable.dto.OrderRequest;
import com.mzc.com.singletable.dto.OrderResponse;
import com.mzc.com.singletable.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	
	@PostMapping
	public ResponseEntity<OrderCreateResponse> createOrder(@RequestBody OrderRequest request) {
		OrderCreateResponse response = orderService.createOrder(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponse> getOrder(@PathVariable String orderId) {
		OrderResponse response = orderService.getOrder(orderId);
		if (response == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(response);
	}
}
