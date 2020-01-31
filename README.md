# Java Inventory System

To view only one page of this program, here: [Inventory](https://github.com/markwartman1/inventorySystem/blob/master/src/model/Inventory.java)
Application tour via [YouTube](https://youtu.be/BfDBhmaJHQ0)

## Model Classes: Inventory, Product, & Part

### Product & Part Classes

Essentially a [Product](https://github.com/markwartman1/inventorySystem/blob/master/src/model/Product.java) is made of Part's in this program.  The Part class is Abstract; therefore, the classes InHouse & Outsourced extend Part, which is Inheritance, a pilar of Object Oriented Programming.

### Inventory Class

The [Inventory](https://github.com/markwartman1/inventorySystem/blob/master/src/model/Inventory.java) Class is where the majority of the programming takes place in this program.

#### ExceptionUtility Class

The ExceptionUtility Class handles all of the Exception handeling of the program where it is programmed in a way that it is reused by multiple classes.

## View & Controller Classes

Product is split into Add or Modify.  Part is also split into Add or Modify.  Other than that, the Main-Menu is the default View & Controller.

