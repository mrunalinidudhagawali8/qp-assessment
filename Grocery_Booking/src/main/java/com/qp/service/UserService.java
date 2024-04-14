package com.qp.service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qp.entities.GroceryItem;
import com.qp.entities.Order;
import com.qp.exceptions.GroceryItemNotFoundException;
import com.qp.exceptions.InsufficientStockException;
import com.qp.repository.GroceryItemRepository;
import com.qp.repository.OrderRepository;

@Service
public class UserService {

	private static Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private GroceryItemRepository groceryRepo;

	@Autowired
	private GroceryMaintainingService groceryMaintainingService;

	@Autowired
	private OrderRepository orderRepository;

	public GroceryItem getGroceryItemById(Long id) {
		GroceryItem existingGroceryItem = groceryRepo.findById(id)
				.orElseThrow(() -> new GroceryItemNotFoundException(id + " : itemId not found"));
		return existingGroceryItem;
	}

	public List<GroceryItem> getAllGroceryItemsList() {
		return groceryRepo.findAll();
	}

	public Boolean createNewOrder(Order order) throws InsufficientStockException{
		/**
		 * map of items not present sufficiently in stock
		 * Map<GroceryItemId,AvailableNumberOfItems>
		 */
		Map<Long, Integer> ItemsNotPresentInStockMap = new HashMap<Long, Integer>();
		Map<Long, Integer> potentialOrderItems = order.getPurchasedItemsMap();
		Date orderTimestamp = Date.from(Instant.now());
		for (Map.Entry<Long, Integer> potentialOrderItem : potentialOrderItems.entrySet()) {
			GroceryItem tempGrocery = getGroceryItemById(potentialOrderItem.getKey());
			if (tempGrocery != null && tempGrocery.getStockQty() >= potentialOrderItem.getValue()) {
				order.setOrderDate(orderTimestamp);
				/**
				 * decrease the stock count & update the stock value
				 */
				tempGrocery.setStockQty(tempGrocery.getStockQty() - potentialOrderItem.getValue());
				groceryMaintainingService.updateGroceryItem(tempGrocery);
				log.info("Stock value for itemId:{}, ItemName:{} has been updated to Quantity:{}",
						tempGrocery.getGrocerySkuId(), tempGrocery.getName(), tempGrocery.getStockQty());
			} else {
				ItemsNotPresentInStockMap.put(potentialOrderItem.getKey(), tempGrocery.getStockQty());
				log.warn("The itemId:{} is only left with quantity:{}", potentialOrderItem.getKey(), tempGrocery.getStockQty());
			}
		}

		if (ItemsNotPresentInStockMap.isEmpty()) {
			orderRepository.save(order);
			log.info("Order created successfully with userId:{} and orderId:{}", order.getUserId(), order.getId());
			return true;
		} else {
			StringBuilder errorMsg = new StringBuilder();
			ItemsNotPresentInStockMap.forEach((itemId, qtyPresent) -> {
				errorMsg.append("{ItemId:").append(itemId).append(", availableStock:").append(qtyPresent).append("}");
			});
			log.error("Cannot place order due to insufficient stocks, here is more info:{}", errorMsg.toString());

			throw new InsufficientStockException(errorMsg.toString());
		}

	}

}
