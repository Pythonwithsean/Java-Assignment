import java.util.*;

/**
 * The warehouse.
 * It is divided into a rectangular grid for storing items.
 * Each item consists of a part number and a quantity
 * Only one type of part is stored in any location of the grid.
 *
 * TODO: Complete parts 1, 4, 5, 6, 7 and 8 in this class.
 */
public class Warehouse {
    // The maximum quantity in any location of the grid.
    private static final int MAX_AMOUNT = 500;
    // The number of rows and columns.
    private final int numRows, numCols;
    // The grid.
    // Empty locations must be stored as null values.
    private final Item[][] grid;

    /**
     * Create an empty warehouse of the given number of rows and columns.
     * 
     * @param numRows The number of rows.
     * @param numCols The number of columns.
     */
    public Warehouse(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        grid = new Item[this.numRows][this.numCols];
    }

    /**
     * TODO: Part 1a.
     * Get a list of all the locations currently storing parts.
     * 
     * @return a list of locations containing parts.
     */
    public List<Location> getPartLocations() {
        List<Location> partLocations = new ArrayList<>();
        // TODO: find all the locations in the warehouse that contain parts.
        // In other words, grid[aLocation.row()][aLocation.col()] is not null.
        // Store all the occupied locations in the partLocations list.
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid.length; col++) {
                if (grid[row][col] != null) {
                    partLocations.add(new Location(row, col));
                }
            }
        }

        return partLocations;
    }

    /**
     * TODO: Part 1b
     * Get the item (if any) at the given location.
     * 
     * @param theLocation The location.
     * @return The item, or null if the location is empty.
     */
    public Item getItemAt(Location theLocation) {
        // TODO: Return the item at the given location.

        return grid[theLocation.row()][theLocation.col()] != null ? grid[theLocation.row()][theLocation.col()] : null;

    }

    /**
     * TODO: Part 1c.
     * Get the total quantity of the given part.
     * 
     * @param partCode The part code.
     * @return the total quantity of the part.
     */
    public int getPartCount(int partCode) {
        int count = 0;
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid.length; y++) {
                if (grid[x][y] != null) {
                    if (grid[x][y].getPartCode() == partCode) {
                        count += grid[x][y].getQuantity();
                    }
                } else {
                    continue;
                }

            }
        }

        // TODO find the locations with given part code and total their quantities.

        return count;
    }

    /**
     * TODO: Part 1d.
     * Get the list of locations of the given part.
     * 
     * @param partCode The part to locate.
     * @return A list of locations; may be empty.
     * 
     */
    public List<Location> findPart(int partCode) {
        List<Location> locationList = new ArrayList<>();
        // TODO: Store all locations with the given part code in locationList.

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid.length; y++) {
                if (grid[x][y] != null) {
                    if (grid[x][y].getPartCode() == partCode) {

                        locationList.add(new Location(x, y));
                    }
                } else {
                    continue;
                }

            }
        }

        return locationList;
    }

    // NB: TODO Part 2 is in the PartsInventory class.
    // NB: TODO Part 3 is in the DatabaseHandler class.

    /**
     * TODO: Part 4.
     * Find out whether all the items in the order are fully in stock.
     * 
     * @param order The order to check.
     * @return true if all the items are in stock, false otherwise.
     */
    public boolean canBeFilled(ClientOrder order) {
        // TODO: For each item in the customer's order, check if there
        // enough in the warehouse to meet the customer's needs.
        ItemInventory itemInventory = order.getItemInventory();
        Boolean c = false;
        for (Item item : itemInventory.getItems()) {
            c = getPartCount(item.getPartCode()) > item.getQuantity() ? true : false;
        }

        return c;
    }

    /**
     * TODO: Part 5.
     * Create a purchase order to restock any items that are not in
     * the warehouse.
     * 
     * @param partsInventory The inventory of parts.
     * @return A purchase order, or null if none need restocking.
     */
    public PurchaseOrder createRestockOrder(PartsInventory partsInventory) {
        // A unique order number.
        int orderNumber = PurchaseOrder.getNextOrderNumber();
        // How many to order.
        int amountRequired = 50;
        ItemInventory purchaseInventory = new ItemInventory();
        PurchaseOrder thePurchaseOrder = new PurchaseOrder(orderNumber, Date.getNow(), purchaseInventory, false);
        // TODO: For each product/part in the parts inventory that is not in the
        // warehouse
        // add an Item to the purchase inventory to buy amountRequired of that product.
        for (Part part : partsInventory.getParts()) {
            if (getPartCount(part.partCode()) == 0) {
                purchaseInventory.addItem(new Item(part.partCode(), amountRequired));
            }
        }
        if (purchaseInventory.getItems().isEmpty())
            return null;
        return thePurchaseOrder;
    }

    /**
     * TODO: Part 6.
     * Create a purchase order for the missing parts in the client's order.
     * 
     * @param clientOrder The client order.
     * @return The purchase order.
     */
    public PurchaseOrder createPurchaseOrder(ClientOrder clientOrder) {
        assert !canBeFilled(clientOrder);
        // Need a unique order number.
        int orderNumber = PurchaseOrder.getNextOrderNumber();
        ItemInventory purchaseInventory = new ItemInventory();
        PurchaseOrder thePurchaseOrder = new PurchaseOrder(orderNumber, Date.getNow(), purchaseInventory, false);
        // TODO: Work out which parts need to be ordered.
        // Create an Item for each (part number and quantity) that needs to be
        // ordered and add it to the purchase inventory.

        return thePurchaseOrder;
    }

    /**
     * TODO: Part 7.
     * Use the order to create a pick list: a list of the locations
     * from which the parts must be taken to fulfil the order.
     * 
     * @param order The client's order.
     * @return The pick list.
     */
    public List<PickListItem> createAPickList(ClientOrder order) {
        List<PickListItem> pickListItems = new ArrayList<>();
        // TODO: For each item in the customer's order, find the locations
        // where the part is stored and create enough PickItems to fulfil
        // the order.

        assert locationsOk();
        return pickListItems;
    }

    /**
     * TODO: Part 8.
     * Store the items in the delivery.
     * Where an item is already present in the warehouse, add it to the
     * existing locations up to the MAX_AMOUNT in a location.
     * Where an item is not present - or all existing locations of the
     * item are already full, store it in any empty location.
     * At the end of this method, no location may contain more than
     * MAX_AMOUNT of any item.
     * 
     * @param theDelivery The delivery to be distributed in the warehouse.
     * @return A list of where the items were stored.
     */
    public List<Location> storeDelivery(Delivery theDelivery) {
        List<Location> whereStored = new ArrayList<>();
        for (Item theItem : theDelivery.getItemInventory().getItems()) {
            // TODO:
            // Where an item is already present in the warehouse,
            // add the quantity to the existing locations up to the
            // MAX_AMOUNT in any one location.
            //
            // Where an item is not present - or all existing locations of the
            // item are already full, store it in any empty location.
            // Add to the whereStored list the location(s) each item is stored in.

        }

        // At the end of this method, no location may contain more than
        // MAX_AMOUNT of any item.
        assert locationsOk();
        return whereStored;
    }

    /**
     * Get a list of available part codes.
     * 
     * @return the list of codes.
     */
    public List<Integer> getAvailablePartCodes() {
        Set<Integer> partCodes = new HashSet<>();
        for (int row = 0; row < numRows; row++) {
            Item[] theRow = grid[row];
            for (int col = 0; col < numCols; col++) {
                Item item = theRow[col];
                if (item != null && item.getQuantity() != 0) {
                    partCodes.add(item.getPartCode());
                }
            }
        }
        return new ArrayList<>(partCodes);
    }

    /**
     * Add the given item to the warehouse.
     * If there is already an item there then the part codes
     * must be identical.
     * 
     * @param theLocation Where to store the item.
     * @param anItem      The item to be stored.
     */
    public void addToWarehouse(Location theLocation, Item anItem) {
        Item currentItem = grid[theLocation.row()][theLocation.col()];
        assert anItem.getQuantity() > 0;

        if (currentItem == null) {
            grid[theLocation.row()][theLocation.col()] = anItem;
        } else {
            assert currentItem.getPartCode() == anItem.getPartCode()
                    : "Attempt to store an item where a different type is stored";
            currentItem.increaseQuantity(anItem.getQuantity());
        }

        assert grid[theLocation.row()][theLocation.col()] != null;
        assert grid[theLocation.row()][theLocation.col()].getQuantity() <= MAX_AMOUNT;
    }

    /**
     * Print the occupied locations in the warehouse.
     */
    public void printOccupiedLocations() {
        for (int row = 0; row < numRows; row++) {
            Item[] theRow = grid[row];
            for (int col = 0; col < numCols; col++) {
                Item item = theRow[col];
                if (item != null) {
                    System.out.printf("%d,%d: %s%n", row, col, item);
                }
            }
        }
    }

    /**
     * Print a map of the warehouse.
     */
    public void printMap() {
        System.out.println("Warehouse contents in a 2D visual format:");

        // Print the column numbers first
        System.out.print("    |");
        for (int col = 0; col < numCols; col++) {
            System.out.printf(" %2d|", col);
        }
        System.out.println();
        for (int w = 0; w < numCols * 4 + 5; w++) {
            System.out.print("-");
        }
        System.out.println();
        for (int row = 0; row < numRows; row++) {
            Item[] theRow = grid[row];

            System.out.printf("  %2d|", row);
            for (int col = 0; col < numCols; col++) {
                if (theRow[col] != null) {
                    System.out.printf("%3d", theRow[col].getQuantity());
                } else {
                    System.out.print("   ");
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }

    /**
     * Check that no location has more than the MAX_AMOUNT of parts
     * or zero parts.
     * 
     * @return true if everything is ok, false otherwise.
     */
    public boolean locationsOk() {
        for (int row = 0; row < numRows; row++) {
            Item[] theRow = grid[row];
            for (int col = 0; col < numCols; col++) {
                Item anItem = theRow[col];
                if (anItem != null &&
                        anItem.getQuantity() > MAX_AMOUNT &&
                        anItem.getQuantity() > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Get the number of parts at the given location.
     * 
     * @param theLocation the location.
     * @return The number of parts.
     */
    private int getQuantityAt(Location theLocation) {
        Item theItem = grid[theLocation.row()][theLocation.col()];
        if (theItem != null) {
            return theItem.getQuantity();
        } else {
            return 0;
        }
    }

    /**
     * Get a randomised list of all the empty locations.
     * 
     * @return a list of empty locations.
     */
    private List<Location> getEmptyLocations() {
        List<Location> emptyLocations = new ArrayList<>();
        for (int row = 0; row < numRows; row++) {
            Item[] theRow = grid[row];
            for (int col = 0; col < numCols; col++) {
                Item anItem = theRow[col];
                if (anItem == null) {
                    emptyLocations.add(new Location(row, col));
                }
            }
        }
        Collections.shuffle(emptyLocations);
        return emptyLocations;
    }

}
