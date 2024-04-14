package com.qp.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.qp.entities.GroceryItem;
import com.qp.entities.Order;
import com.qp.exceptions.GroceryItemNotFoundException;
import com.qp.exceptions.InsufficientStockException;
import com.qp.service.GroceryMaintainingService;
import com.qp.service.UserService;

@RestController
@RequestMapping("/user/api")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private GroceryMaintainingService service;

	/**
	 * Get info for an GroceryItem
	 */
	@GetMapping("/{id}")
	public ResponseEntity<GroceryItem> getGroceryItemById(@PathVariable Long id) {
		Optional<GroceryItem> groceryItemopt = service.getGroceryItemById(id);
		if (groceryItemopt.isEmpty()) {
			throw new GroceryItemNotFoundException("Grocery Item not found with ItemId:{}" + id);
		}
		return ResponseEntity.status(HttpStatus.OK).body(groceryItemopt.get());
	}

	/**
	 * Get info for an GroceryItem
	 */
	@GetMapping("/all-items")
	public ResponseEntity<List<GroceryItem>> getAllGroceryItems() {
		List<GroceryItem> groceryItemsList = userService.getAllGroceryItemsList();
		if (groceryItemsList.isEmpty()) {
			throw new GroceryItemNotFoundException("No Grocery Items found in inventory");
		}
		return ResponseEntity.status(HttpStatus.OK).body(groceryItemsList);
	}

	/**
	 * Place new Order
	 */
	@PostMapping(path = "/new-order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> createNewOrder(@RequestBody Order order, HttpServletRequest request)
			throws InsufficientStockException {
		try {
			userService.createNewOrder(order);
			URI location = ServletUriComponentsBuilder.fromRequestUri(request).buildAndExpand(order.getId()).toUri();
			return ResponseEntity.created(location).body(Boolean.TRUE);
		} catch (InsufficientStockException exceptionDetails) {
			throw new InsufficientStockException("Cannot place order due to insufficient stocks, here is more info:"
					+ exceptionDetails.getMessage());
		}

	}

}
