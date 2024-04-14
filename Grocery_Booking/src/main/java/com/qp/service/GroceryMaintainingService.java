package com.qp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qp.entities.GroceryItem;
import com.qp.exceptions.GroceryItemNotFoundException;
import com.qp.repository.GroceryItemRepository;

@Service
public class GroceryMaintainingService {

	@Autowired
	private GroceryItemRepository groceryItemRepository;

	public GroceryItem createGroceryItem(GroceryItem groceryItem) {
		return groceryItemRepository.save(groceryItem);
	}

	public Optional<GroceryItem> getGroceryItemById(Long id) {
		Optional<GroceryItem> existingGroceryItemOpt = groceryItemRepository.findById(id);
		return existingGroceryItemOpt;
	}

	public void deleteGroceryItemById(Long id) {
		Optional<GroceryItem> item = groceryItemRepository.findById(id);
		if (Optional.empty().equals(item)) {
			throw new GroceryItemNotFoundException(id + " : itemId not found");
		} else {
			groceryItemRepository.deleteById(id);
		}
	}

	public void deleteGroceryAllItems() {
		groceryItemRepository.deleteAll();
	}

	public GroceryItem updateGroceryItem(GroceryItem groceryItem) {
		GroceryItem existingGroceryItem = groceryItemRepository.findById(groceryItem.getGrocerySkuId())
				.orElseThrow(() -> new GroceryItemNotFoundException(groceryItem.getGrocerySkuId() + " : itemId not found"));

		existingGroceryItem.setDescription(groceryItem.getDescription());
		existingGroceryItem.setName(groceryItem.getName());
		existingGroceryItem.setPrice(groceryItem.getPrice());
		existingGroceryItem.setStockQty(groceryItem.getStockQty());
		return groceryItemRepository.save(existingGroceryItem);
	}

}
