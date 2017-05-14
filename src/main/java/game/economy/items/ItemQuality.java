package game.economy.items;

/**
 * ItemQuality is the quality of items. The quality of an item is determined by
 * the quality of the items used to produce it, weighted by how important each
 * item is to the overall quality of the final product, and how many of that
 * item went into production. If all instances of an item are the same quality,
 * the quality NORMAL should be used.
 * 
 * @author eukaryote
 *
 */
public enum ItemQuality {
	HIGH, NORMAL, LOW;
}
