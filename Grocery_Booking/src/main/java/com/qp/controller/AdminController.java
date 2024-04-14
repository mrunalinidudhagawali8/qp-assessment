package com.qp.controller;

import java.net.URI;
import java.time.Duration;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.qp.entities.GroceryItem;
import com.qp.exceptions.GroceryItemNotFoundException;
import com.qp.service.GroceryMaintainingService;

@RestController
@RequestMapping("/admin/api")
public class AdminController {

	@Autowired
	private GroceryMaintainingService service;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {

		return builder.setConnectTimeout(Duration.ofMillis(3000)).setReadTimeout(Duration.ofMillis(3000)).build();
	}

	/**
	 * Add new Grocery
	 * 
	 * @throws Exception
	 */
	@PostMapping(path = "/add-grocery", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GroceryItem> createGroceryItem(@Valid @RequestBody GroceryItem groceryItem,
			HttpServletRequest request) throws Exception {

		GroceryItem savedItem = service.createGroceryItem(groceryItem);
		if (savedItem != null) {
			URI location = ServletUriComponentsBuilder.fromRequestUri(request).buildAndExpand(savedItem.getGrocerySkuId())
					.toUri();
			return ResponseEntity.created(location).body(savedItem);
		} else {
			throw new Exception("Error in creating the User resource. Try Again.");
		}

	}

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
	 * Delete a item
	 */
	@DeleteMapping("/{id}")
	public HttpStatus deleteGroceryItemById(@PathVariable Long id) {
		try {
			service.deleteGroceryItemById(id);
			return HttpStatus.OK;
		} catch (Exception e) {
			throw new GroceryItemNotFoundException("Cannot Delete GroceryItem with ID:" + id);
		}
	}

	/**
	 * Delete all groceryItems
	 */
	@DeleteMapping("/delete-all")
	public HttpStatus deleteGroceryAllItems() {

		try {
			service.deleteGroceryAllItems();
			return HttpStatus.OK;
		} catch (Exception e) {
			throw new GroceryItemNotFoundException("Some issue occured while trying to delete all items");
		}
	}

	@PutMapping("/update-grocery")
	public ResponseEntity<GroceryItem> updateGroceryItem(@RequestBody GroceryItem groceryItem) throws Exception {
		GroceryItem updatedItem =  service.updateGroceryItem(groceryItem);
		if(updatedItem!=null)
		{
			return new ResponseEntity<>(updatedItem, HttpStatus.OK);
		}else
		{
			throw new Exception("Could not update the resource with id:" + groceryItem.getGrocerySkuId());
		}
	}
	
}
