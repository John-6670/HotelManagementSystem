package models.service;

// TODO: make prices real

/**
 * Enum class for services that can be provided to a room.
 * Each service has a price associated with it.
 *
 * @author John
 */
public enum Services {
    CLEAN_ROOM(1),
    CHARGE(2),
    BABY_KIT(2),
    TOWEL_REPLACE(1),
    EXTRA_BLANKETS(1);

    private final double price;

    Services(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Returns the name of the service with each word capitalized.
     *
     * @return the name of the service with each word capitalized
     */
    @Override
    public String toString() {
        String[] words = name().split("_");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            result.append(word.charAt(0)).append(word.substring(1).toLowerCase()).append(" ");
        }

        return result.toString().trim();
    }
}
