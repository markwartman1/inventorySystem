# Java Inventory System

To view only one page of this program, here: [Inventory](https://github.com/markwartman1/inventorySystem/blob/master/src/model/Inventory.java)

Application tour via [YouTube](https://youtu.be/BfDBhmaJHQ0)

## Model Classes: Inventory, Product, & Part

### Product & Part Classes

Essentially a [Product](https://github.com/markwartman1/inventorySystem/blob/master/src/model/Product.java) is made of Part's in this program.  The Part class is Abstract; therefore, the classes InHouse & Outsourced extend Part, which is Inheritance, a pillar of Object Oriented Programming.

### Inventory Class

The [Inventory](https://github.com/markwartman1/inventorySystem/blob/master/src/model/Inventory.java) Class is where the majority of the programming takes place in this program.

#### ExceptionUtility Class

The ExceptionUtility Class handles all of the Exception handeling of the program where it is programmed in a way that it is reused by multiple classes.

## View & Controller Classes

Product is split into Add or Modify.  Part is also split into Add or Modify.  Other than that, the Main-Menu is the default View & Controller.

## Object Oriented Programming

Abstraction with Generalization & Specialization are present with the following:

#### Abstraction: Generalization
Part Class is the Abstract Class that handles the Generalization.

#### Inheritance: Generalization
Therefore: InHouse & Outsourced use Inheritance to extend the Part Class to take advantage of the Generalization aspects.

#### Abstraction: Specialization
The Specialization aspect is essentially a MachineId for the Inhouse Class and CompanyName for the Outsourced Class. 

#### Polymorphism
Polymorphism is present in the Inventory Class, where lookupPart & lookupProduct each have Overloaded Methods that handle String & Integer arguments.

#### Encapsulation
Encapsulation is present in each of the Models in the typical fashion of private fields with accessors/getters & mutators/setters to manipulate the data.