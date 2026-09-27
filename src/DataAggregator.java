import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class DataAggregator {

    public DataAggregator() {
    }

    public ProductInfo aggregateProductInfo(String productName) {

        CompletableFuture<Double> price = CompletableFuture.supplyAsync(() -> {
            return fetchPrice();
        }).exceptionally(exs -> 0.0);

        CompletableFuture<String> desc = CompletableFuture.supplyAsync(() -> {
            return fetchDescription();
        }).exceptionally(exs -> "Нет данных");

        CompletableFuture<Double> rating = CompletableFuture.supplyAsync(() -> {
            return fetchRating();
        }).exceptionally(exs -> 0.0);

        CompletableFuture<Void> all = CompletableFuture.allOf(price, desc, rating);

        all.join();

        return new ProductInfo(productName, price.join(), desc.join(), rating.join());
    }


    public double fetchPrice() {
        Random random = new Random();

        try {
            int delay = random.nextInt(1, 5);
            Thread.sleep(1000 * delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        double price = random.nextDouble(500, 9999);
        price = Math.round(price * 100) / 100.0;

        int randomException = random.nextInt(1, 6);

        if (randomException == 1) {
            throw new RuntimeException("Ошибка");
        }

        return price;
    }


    public String fetchDescription() {
        Random random = new Random();

        try {
            int delay = random.nextInt(1, 6);
            Thread.sleep(1000 * delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String[] descriptionAray = new String[]{"Крутая вещь", "Не очень крутая", "Обычная вещь", "Своей цены не стоит"};

        String description = descriptionAray[random.nextInt(0, 4)];


        int randomException = random.nextInt(0, 5);

        if (randomException == 1) {
            throw new RuntimeException("Ошибка");
        }

        return description;
    }

    public double fetchRating() {
        Random random = new Random();

        try {
            int delay = random.nextInt(1, 6);
            Thread.sleep(1000 * delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        double rating = random.nextDouble(0, 5.1);
        rating = Math.round(rating * 100) / 100.0;

        int randomException = random.nextInt(1, 6);

        if (randomException == 1) {
            throw new RuntimeException("Ошибка");
        }

        return rating;
    }


}
