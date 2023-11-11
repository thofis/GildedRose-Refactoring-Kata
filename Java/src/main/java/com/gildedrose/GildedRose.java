package com.gildedrose;

class GildedRose {
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
    public static final String CONJURED_MANA_CAKE = "Conjured Mana Cake";
    private Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int index = 0; index < items.length; index++) {
            calculateQualityBeforeSellIn(index);
            decrementSellIn(index);
            calculateQualityAfterSellIn(index);

            ensureQualityIsGreaterThanOrEqualTo0(index);
            ensureQualityIsLessThanOrEqualTo50(index);
        }
    }

    private void calculateQualityBeforeSellIn(int index) {
        if (isStandardItem(index)) {
            decrementQuality(index);
            if (isConjuredItem(index)) {
                decrementQuality(index);
            }
        } else {
            incrementQuality(index);
            incrementQualityForBackStagePassesAdditionally(index);
        }
    }

    private boolean isStandardItem(int index) {
        return !isIncrementedItem(index) && !isLegendaryItem(index);
    }

    private boolean isIncrementedItem(int index) {
        return items[index].name.equals(AGED_BRIE)
            || items[index].name.equals(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT);
    }

    private void incrementQuality(int index) {
        if (!isLegendaryItem(index)) {
            items[index].quality += 1;
        }
    }

    private void decrementQuality(int index) {
        decrementQualityBy(index, 1);
    }

    private void decrementQualityBy(int index, int value) {
        if (!isLegendaryItem(index)) {
            items[index].quality -= value;
        }
    }

    private void incrementQualityForBackStagePassesAdditionally(int index) {
        if (items[index].name.equals(BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT)) {
            if (items[index].sellIn < 11) {
                incrementQuality(index);
            }

            if (items[index].sellIn < 6) {
                incrementQuality(index);
            }
        }
    }

    private void ensureQualityIsLessThanOrEqualTo50(int index) {
        if (items[index].quality > 50 && !isLegendaryItem(index)) {
            items[index].quality = 50;
        }
    }

    private void ensureQualityIsGreaterThanOrEqualTo0(int index) {
        if (items[index].quality < 0) {
            items[index].quality = 0;
        }
    }

    private void calculateQualityAfterSellIn(int index) {
        if (items[index].sellIn < 0) {
            switch (items[index].name) {
                case AGED_BRIE -> incrementQuality(index);
                case BACKSTAGE_PASSES_TO_A_TAFKAL_80_ETC_CONCERT -> items[index].quality = 0;
                case CONJURED_MANA_CAKE -> decrementQualityBy(index, 2);
                default -> decrementQualityBy(index, 1);
            }
        }
    }

    private void decrementSellIn(int index) {
        if (!isLegendaryItem(index)) {
            items[index].sellIn -= 1;
        }
    }

    private boolean isLegendaryItem(int index) {
        return items[index].name.equals(SULFURAS_HAND_OF_RAGNAROS);
    }

    private boolean isConjuredItem(int index) {
        return items[index].name.equals(CONJURED_MANA_CAKE);
    }

    String getItemname(int index) {
        return items[index].name;
    }
}
