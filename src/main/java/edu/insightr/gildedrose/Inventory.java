package edu.insightr.gildedrose;

public class Inventory {

    public static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
    private Item[] items;

    public Inventory(Item[] items) {
        super();
        this.items = items;
    }
    public Item[] getItems() {return items;}

    public Inventory() {
        super();
        items = new Item[]{
                new Item("+5 Dexterity Vest", 10, 20),
                new Item("Aged Brie", 2, 0),
                new Item("Elixir of the Mongoose", 5, 7),
                new Item("Sulfuras, Hand of Ragnaros", 0, 80),
                new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
                new Item("Conjured Mana Cake", 3, 6)
        };

    }

    public void printInventory() {
        System.out.println("***************");
        for (Item item : items) {
            System.out.println(item);
        }
        System.out.println("***************");
        System.out.println("\n");
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {

            // "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
            if (!items[i].getName().equals("Sulfuras, Hand of Ragnaros")) {

                // considering that the update is at the start of the day
                items[i].setSellIn(items[i].getSellIn() - 1);

                //"Aged Brie" increases in Quality the older it gets
                if (items[i].getName().equals("Aged Brie") && items[i].getQuality() < 50) {
                    items[i].setQuality(items[i].getQuality() + 1);
                } else {

                    // Backstage case
                    if (items[i].getName().equals("Backstage passes to a TAFKAL80ETC concert")) {

                        // increases in Quality as it's SellIn svalue approaches
                        if (items[i].getSellIn() >= 0 && items[i].getQuality() < 50) {
                            items[i].setQuality(items[i].getQuality() + 1);

                            //Quality increases by 2 when there are 10 days or less
                            if (items[i].getSellIn() <= 10 && items[i].getQuality() < 50) {
                                items[i].setQuality(items[i].getQuality() + 1);

                                //Quality increases by 3 when there are 5 days or less
                                if (items[i].getSellIn() <= 5 && items[i].getQuality() < 50) {
                                    items[i].setQuality(items[i].getQuality() + 1);
                                }
                            }
                        } else {
                            //Quality drops to 0 after the concert
                            items[i].setQuality(0);
                        }
                    } else {
                        int decrease = 1;

                        // Once the sell by date has passed, Quality degrades twice as fast
                        if (items[i].getSellIn() < 0) {
                            decrease = decrease * 2;
                        }

                        // "Conjured" items degrade in Quality twice as fast as normal items
                        if (items[i].getName().equals("Conjured Mana Cake")) {
                            decrease = decrease * 2;
                        }
                        if (items[i].getQuality() > decrease) {
                            items[i].setQuality(items[i].getQuality() - decrease);
                        } else {
                            items[i].setQuality(0);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        for (int i = 0; i < 10; i++) {
            inventory.updateQuality();
            inventory.printInventory();
        }
    }
}
