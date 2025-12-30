package com.mzc.com.singletable.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopData {
	private String pk;      // ORD_20250101_bf41
	private String sk;      // INFO or ITEM#001, ITEM#002
	private String type;    // ORDER or ITEM
	private String info;    // 주문 성공 or 메뉴 이름
	private Integer amount; // 주문 총액 or 가격

	@DynamoDbPartitionKey
	public String getPk() {
		return pk;
	}

	@DynamoDbSortKey
	public String getSk() {
		return sk;
	}
}
