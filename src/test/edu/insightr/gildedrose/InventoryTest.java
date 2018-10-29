package edu.insightr.gildedrose;

import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryTest {

    @Test
    public void testUpdateQuality() throws Exception{

        Inventory inventory = new Inventory();
        Inventory inventoryAfterUpdate = new Inventory();

        int timeleft = 16; // to verify all the concert update quality

        while( timeleft > 0 ){

            inventoryAfterUpdate.updateQuality();
            for(int i = 0; i < inventoryAfterUpdate.getItems().length; i++){

                // "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
                if (inventoryAfterUpdate.getItems()[i].getName().equals("Sulfuras, Hand of Ragnaros")) {
                    assertEquals(80, inventoryAfterUpdate.getItems()[i].getQuality());
                } else {

                    // Verify that quality is always between 0 and 50

                    // Compare "Aged Brie"'s quality with the last update
                    if (inventoryAfterUpdate.getItems()[i].getName().equals("Aged Brie")) {
                        assertEquals(inventory.getItems()[i].getQuality() + 1,
                                inventoryAfterUpdate.getItems()[i].getQuality());
                    } else {

                        // Backstage case
                        if (inventoryAfterUpdate.getItems()[i].getName().equals("Backstage passes to " +
                                "a TAFKAL80ETC concert")) {

                            // increases in Quality as it's SellIn value approaches
                            if (inventoryAfterUpdate.getItems()[i].getSellIn() > 10) {
                                if(inventory.getItems()[i].getQuality() + 1 <= 50){
                                    assertEquals(inventory.getItems()[i].getQuality() + 1,
                                            inventoryAfterUpdate.getItems()[i].getQuality());
                                } else {
                                    assertEquals(50, inventoryAfterUpdate.getItems()[i].getQuality());
                                }
                            }
                            //Quality increases by 2 when there are 10 days or less
                            if (inventoryAfterUpdate.getItems()[i].getSellIn() <= 10
                                    && inventoryAfterUpdate.getItems()[i].getSellIn() > 5) {
                                if(inventory.getItems()[i].getQuality() + 2 <= 50){
                                    assertEquals(inventory.getItems()[i].getQuality() + 2,
                                            inventoryAfterUpdate.getItems()[i].getQuality());
                                } else {
                                    assertEquals(50, inventoryAfterUpdate.getItems()[i].getQuality());
                                }
                            }
                            //Quality increases by 3 when there are 5 days or less
                            if (inventoryAfterUpdate.getItems()[i].getSellIn() <= 5
                                    && inventoryAfterUpdate.getItems()[i].getSellIn() >= 0) {
                                if(inventory.getItems()[i].getQuality() + 3 <= 50){
                                    assertEquals(inventory.getItems()[i].getQuality() + 3,
                                            inventoryAfterUpdate.getItems()[i].getQuality());
                                } else {
                                    assertEquals(50, inventoryAfterUpdate.getItems()[i].getQuality());
                                }
                            }
                            //Quality drops to 0 after the concert
                            if(inventoryAfterUpdate.getItems()[i].getSellIn() < 0){
                                assertEquals(0, inventoryAfterUpdate.getItems()[i].getQuality());
                            }


                        } else {

                            int decrease = 1;

                            // Once the sell by date has passed, Quality degrades twice as fast
                            if (inventoryAfterUpdate.getItems()[i].getSellIn() < 0) {
                                decrease = decrease * 2;
                            }

                            // "Conjured" items degrade in Quality twice as fast as normal items
                            if (inventoryAfterUpdate.getItems()[i].getName().equals("Conjured Mana Cake")) {
                                decrease = decrease * 2;
                            }

                            if(inventory.getItems()[i].getQuality() - decrease > 0){
                                assertEquals(inventory.getItems()[i].getQuality() - decrease,
                                        inventoryAfterUpdate.getItems()[i].getQuality());
                            } else {
                                assertEquals(0, inventoryAfterUpdate.getItems()[i].getQuality());
                            }
                        }
                    }
                }
            }
            inventory.updateQuality();
            timeleft --;
        }
    }
}