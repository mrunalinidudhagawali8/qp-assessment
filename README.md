## Grocery Booking API

This document serves as a guide for the Grocery Booking API, a SpringBoot application designed for managing grocery inventory and facilitating user orders.

### Features

* **Grocery Management (Admin):** (Requires "admin_role")
    * Add new grocery items to the inventory.
    * Retrieve information about a specific grocery item.
    * Update existing grocery item details.
    * Delete a particular grocery item.
    * Delete all grocery items from the inventory (for data cleansing purposes).
* **Grocery Browsing (User):** (Requires "user_role")
    * View the list of available grocery items.
    * Retrieve information about a specific grocery item.
* **Order Placement (User):** (Requires "user_role")
    * Place a new order for groceries. 

### Technologies Used

* Spring Boot
* Spring Data JPA
* H2 Database

### API Documentation

**Base URL:** http://localhost:8080 (Default Spring Boot port)

**Roles:**

* Currently, there are only two predefined roles: "admin_role" and "user_role". 
* Implement proper authentication mechanisms to assign these roles to users.

** H2 Database Access - `http://localhost:8080/h2-console`

**Admin API (Requires "admin_role"):**

* **Add Grocery Item:**
    * **Method:** POST
    * **URL:** `/add-grocery`
    * **Request Body:** JSON object representing the new `GroceryItem`
    * `{
    "name": "Cow Milk",
    "description": "Cow's Fresh Milk",
    "price": 25.0,
    "stockQty": 5
      }`
    * **Response:** Created (201) with the newly created `GroceryItem` details and location URI.
* **Get Grocery Item by ID:**
    * **Method:** GET
    * **URL:** `/get-item/{id}` (Replace `{id}` with the actual grocery item ID)
    * **Response:** OK (200) with the retrieved `GroceryItem` details if found, throws `GroceryItemNotFoundException` otherwise.
* **Update Grocery Item:**
    * **Method:** PUT
    * **URL:** `/update-grocery`
    * **Request Body:** JSON object representing the updated `GroceryItem` details
      * `{
      "groceryId":3,
    "name": "Buffalo-Milk",
    "description": "Fresh Buffalo milk",
    "price": 55,
    "stockQty": 500
    }`
    * **Response:** OK (200) with the updated `GroceryItem` details if successful, throws exception otherwise.
* **Delete Grocery Item by ID:**
    * **Method:** DELETE
    * **URL:** `/delete-item/{id}` (Replace `{id}` with the actual grocery item ID)
    * **Response:** OK (200) upon successful deletion, throws `GroceryItemNotFoundException` otherwise.
* **Delete All Grocery Items:**
    * **Method:** DELETE
    * **URL:** `/delete-all-items`
    * **Response:** OK (200) upon successful deletion of all grocery items, throws exception otherwise.

**User API (Requires "user_role"):**

* **Get Grocery Item by ID:**
    * **Method:** GET
    * **URL:** `/get-item/{id}` (Replace `{id}` with the actual grocery item ID)
    * **Response:** OK (200) with the retrieved `GroceryItem` details if found, throws `GroceryItemNotFoundException` otherwise.
* **Get All Grocery Items:**
    * **Method:** GET
    * **URL:** `/get-all-items`
    * **Response:** OK (200) with a list of all available `GroceryItem` objects, throws `GroceryItemNotFoundException` if no items found.
* **Place New Order:**
    * **Method:** POST
    * **URL:** `/place-order`
    * **Request Body:** JSON object representing the new `Order`
    * `{
  "userId": 1,
  "purchasedItemsMap": {
    "1": 1,
    "2":4
  }
}
`
    * **Response:** Created (201) with a boolean flag indicating successful order placement, throws `InsufficientStockException` if insufficient stock exists.

**Error Handling:**

The API utilizes custom exception classes to handle errors gracefully. These exceptions are mapped to appropriate HTTP status codes for informative responses.

### Notes

* This is a basic API structure, and additional features like user authentication and authorization with proper role-based access control are crucial for production use.
* A Docker image has also been created for this application to facilitate easier deployment. Refer to the project documentation for details on building and running the Docker image.

