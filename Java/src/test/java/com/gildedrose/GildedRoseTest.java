package com.gildedrose;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    public static final String STANDARD_ITEM = "standard item";

    @Test
    void foo() {
        Item[] items = {new Item("foo", 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.getItemname(0));
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
    void an_items_quality_is_never_negative() {
        TestSetup setup = getTestSetup(new Item(STANDARD_ITEM, 2, 5));
        for (int i = 0; i < 10; i++) {
            setup.app.updateQuality();
            assertThat(setup.item.quality).isGreaterThanOrEqualTo(0);
        }
    }

    @Test
    void an_items_quality_is_never_more_that_50() {
        TestSetup setup = getTestSetup(new Item(STANDARD_ITEM, 2, 100));
        setup.app.updateQuality();
        assertThat(setup.item.quality).isLessThanOrEqualTo(50);
    }

    @Test
    void an_items_quality_decreases_twice_as_fast_when_sellin_is_reached() {
        int initialQuality = 5;
        TestSetup setup = getTestSetup(new Item(STANDARD_ITEM, 0, initialQuality));
        setup.app.updateQuality();
        assertThat(setup.item.quality).isEqualTo(initialQuality - 2);
        setup.app.updateQuality();
        assertThat(setup.item.quality).isEqualTo(initialQuality - 4);

    }

    @Test
    void agedBrie_quality_always_increases() {
        int initialQuality = 0;
        TestSetup setup = getTestSetup(new Item(GildedRose.AGED_BRIE, 2, initialQuality));
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
        TestSetup setup = getTestSetup(new Item(GildedRose.SULFURAS_HAND_OF_RAGNAROS, 0, initialQuality));

        for (int i = 0; i < 10; i++) {
            setup.app().updateQuality();
            assertThat(setup.item().quality).isEqualTo(initialQuality);
        }
    }


    //        "Backstage-Pässe" (backstage passes) werden - wie Aged Brie - hochwertiger,
//        solange das "Verkaufsdatum" noch nicht erreicht wurde.
//        Bei 10 Tagen oder weniger erhöht sich die Qualität um 2,
//        bei 5 Tagen oder weniger um 3, nach dem "Konzert" sinkt sie aber auf 0.
    @Test
    void quality_increases_for_backstage_passes_by_2_10_days_before_concert() {
        int initialQuality = 1;
        TestSetup setup = getTestSetup(new Item(GildedRose.BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 10, initialQuality));
        setup.app.updateQuality();
        assertThat(setup.item.quality).isEqualTo(initialQuality + 2);
    }
    @Test
    void quality_increases_for_backstage_passes_by_3_5_days_before_concert() {
        int initialQuality = 1;
        TestSetup setup = getTestSetup(new Item(GildedRose.BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 5, initialQuality));
        setup.app.updateQuality();
        assertThat(setup.item.quality).isEqualTo(initialQuality + 3);
    }
    @Test
    void quality_increases_for_backstage_passes_by_1_long_before_concert() {
        int initialQuality = 1;
        TestSetup setup = getTestSetup(new Item(GildedRose.BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 50, initialQuality));
        setup.app.updateQuality();
        assertThat(setup.item.quality).isEqualTo(initialQuality + 1);
    }
    @Test
    void quality_drops_to_0_for_backstage_passes_after_concert() {
        int initialQuality = 50;
        TestSetup setup = getTestSetup(new Item(GildedRose.BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT, 0, initialQuality));
        setup.app.updateQuality();
        assertThat(setup.item.quality).isEqualTo(0);
    }

    @Test
    void conjured_items_degrade_twice_as_fast() {
        int initialQuality = 20;
        TestSetup setup = getTestSetup(new Item(GildedRose.CONJURED_MANA_CAKE, 3, initialQuality));

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
