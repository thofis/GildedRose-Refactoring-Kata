package com.gildedrose;

class GildedRose {
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            calculateNewQuality(i);
            calculateNewSellIn(i);
        }
    }

    private void calculateNewSellIn(int index) {
        if (!items[index].name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
            items[index].sellIn = items[index].sellIn - 1;
        }

        if (items[index].sellIn < 0) {
            if (!items[index].name.equals(AGED_BRIE)) {
                if (!items[index].name.equals(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT)) {
                    if (items[index].quality > 0) {
                        if (!items[index].name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
                            items[index].quality = items[index].quality - 1;
                        }
                    }
                } else {
                    items[index].quality = items[index].quality - items[index].quality;
                }
            } else {
                if (items[index].quality < 50) {
                    items[index].quality = items[index].quality + 1;
                }
            }
        }
    }

    private void calculateNewQuality(int index) {
        if (!items[index].name.equals(AGED_BRIE)
                && !items[index].name.equals(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT)) {
            if (items[index].quality > 0) {
                if (!items[index].name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
                    items[index].quality = items[index].quality - 1;
                }
            }
        } else {
            if (items[index].quality < 50) {
                items[index].quality = items[index].quality + 1;

                if (items[index].name.equals(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT)) {
                    if (items[index].sellIn < 11) {
                        if (items[index].quality < 50) {
                            items[index].quality = items[index].quality + 1;
                        }
                    }

                    if (items[index].sellIn < 6) {
                        if (items[index].quality < 50) {
                            items[index].quality = items[index].quality + 1;
                        }
                    }
                }
            }
        }
        if (items[index].quality > 50) {
            items[index].quality = 50;
        }
        if (items[index].quality < 0) {
            items[index].quality = 0;
        }
    }
}
