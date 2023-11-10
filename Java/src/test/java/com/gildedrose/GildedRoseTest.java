package com.gildedrose;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void foo() {
        Item[] items = new Item[]{new Item("foo", 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }

//    -------- day 1 --------
//    name, sellIn, quality
//+5 Dexterity Vest, 9, 19
//    Aged Brie, 1, 1
//    Elixir of the Mongoose, 4, 6
//    Sulfuras, Hand of Ragnaros, 0, 80
//    Sulfuras, Hand of Ragnaros, -1, 80
//    Backstage passes to a TAFKAL80ETC concert, 14, 21
//    Backstage passes to a TAFKAL80ETC concert, 9, 50
//    Backstage passes to a TAFKAL80ETC concert, 4, 50
//    Conjured Mana Cake, 2, 5

    @Test
    void agedBrie_quality_always_increases() {
        int initialQuality = 0;
        TestSetup setup = getTestSetup(new Item("Aged Brie", 2, initialQuality));
        assumeThat(setup.item().quality).isEqualTo(initialQuality);

        setup.app().updateQuality();
        assertThat(setup.item().quality).isEqualTo(initialQuality + 1);

        setup.app().updateQuality();
        assertThat(setup.item().quality).isEqualTo(initialQuality + 2);

        // quality degrades twice as fast after selldate

        setup.app().updateQuality();
        assertThat(setup.item().quality).isEqualTo(initialQuality + 4);

        setup.app().updateQuality();
        assertThat(setup.item().quality).isEqualTo(initialQuality + 6);
    }

    @Test
    void sulfuras_quality_never_changes() {
        int initialQuality = 80;
        TestSetup setup = getTestSetup(new Item("Sulfuras, Hand of Ragnaros", 0, initialQuality));

        for (int i = 0; i < 10; i++) {
            setup.app().updateQuality();
            assertThat(setup.item().quality).isEqualTo(initialQuality);
        }
    }

    @Test
    @Disabled("erst am Ende aktivieren, wenn Tests für andere Requirements umgesetzt und Refactoring vollzogen")
    void conjured_items_degrade_twice_as_fast() {
        int initialQuality = 20;
        TestSetup setup = getTestSetup(new Item("Conjured Mana Cake", 3, initialQuality));

        setup.app.updateQuality();
        assertThat(setup.item.quality).isEqualTo(initialQuality - 2);

        setup.app.updateQuality();
        assertThat(setup.item.quality).isEqualTo(initialQuality - 4);

        setup.app.updateQuality();
        assertThat(setup.item.quality).isEqualTo(initialQuality - 6);

        setup.app.updateQuality();
        assertThat(setup.item.quality).isEqualTo(initialQuality - 10);

        setup.app.updateQuality();
        assertThat(setup.item.quality).isEqualTo(initialQuality - 14);
    }

    private static TestSetup getTestSetup(Item item) {
        GildedRose app = new GildedRose(new Item[]{item});
        TestSetup result = new TestSetup(item, app);
        return result;
    }

    private record TestSetup(Item item, GildedRose app) {
    }


}
