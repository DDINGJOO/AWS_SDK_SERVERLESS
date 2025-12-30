package com.mzc.com.singletable.repository;

import com.mzc.com.singletable.entity.ShopData;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ShopDataRepository {

	private final DynamoDbEnhancedClient enhancedClient;
	private DynamoDbTable<ShopData> shopDataTable;

	@PostConstruct
	public void init() {
		shopDataTable = enhancedClient.table(
				"ShopData-ddingjoo", TableSchema.fromBean(ShopData.class)
		);
	}

	public void save(ShopData shopData) {
		shopDataTable.putItem(shopData);
	}

	public void saveAll(List<ShopData> shopDataList) {
		for (ShopData shopData : shopDataList) {
			shopDataTable.putItem(shopData);
		}
	}

	public List<ShopData> findByPk(String pk) {
		QueryConditional queryConditional = QueryConditional.keyEqualTo(
				Key.builder().partitionValue(pk).build()
		);

		QueryEnhancedRequest request = QueryEnhancedRequest.builder()
				.queryConditional(queryConditional)
				.build();

		return shopDataTable.query(request)
				.items()
				.stream()
				.collect(Collectors.toList());
	}
}
