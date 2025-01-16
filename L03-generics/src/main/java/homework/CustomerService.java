package homework;

import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    // todo: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final TreeMap<Customer, String> dataByCustomer = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        // Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        final Map.Entry<Customer, String> entry = dataByCustomer.firstEntry();
        return new AbstractMap.SimpleEntry<>(
                new Customer(
                        entry.getKey().getId(),
                        entry.getKey().getName(),
                        entry.getKey().getScores()),
                entry.getValue()); // это "заглушка, чтобы скомилировать"
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        final Map.Entry<Customer, String> entry = dataByCustomer.higherEntry(customer);
        if (entry != null) {
            return new AbstractMap.SimpleEntry<>(
                    new Customer(
                            entry.getKey().getId(),
                            entry.getKey().getName(),
                            entry.getKey().getScores()),
                    entry.getValue());
        }
        return null; // это "заглушка, чтобы скомилировать"
    }

    public void add(Customer customer, String data) {
        dataByCustomer.put(customer, data);
    }
}
