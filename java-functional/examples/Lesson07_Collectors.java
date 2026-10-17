package examples;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Module 07 — Collectors
 */
public class Lesson07_Collectors {

    record Employee(String name, String dept, int salary, String level) {}

    public static void main(String[] args) {
        List<Employee> employees = List.of(
            new Employee("Ada", "Eng", 140_000, "Senior"),
            new Employee("Bob", "Eng", 110_000, "Mid"),
            new Employee("Cy", "HR", 90_000, "Mid"),
            new Employee("Di", "Eng", 160_000, "Senior"),
            new Employee("Eve", "HR", 95_000, "Junior")
        );

        Map<String, List<Employee>> byDept =
            employees.stream().collect(Collectors.groupingBy(Employee::dept));
        System.out.println("byDept keys=" + byDept.keySet());

        Map<String, Long> counts =
            employees.stream().collect(Collectors.groupingBy(Employee::dept, Collectors.counting()));
        System.out.println("counts=" + counts);

        Map<String, Double> avgPay =
            employees.stream().collect(
                Collectors.groupingBy(Employee::dept, Collectors.averagingInt(Employee::salary)));
        System.out.println("avgPay=" + avgPay);

        Map<String, List<String>> namesByDept =
            employees.stream().collect(
                Collectors.groupingBy(Employee::dept,
                    Collectors.mapping(Employee::name, Collectors.toList())));
        System.out.println("namesByDept=" + namesByDept);

        Map<String, Map<String, List<Employee>>> nested =
            employees.stream().collect(
                Collectors.groupingBy(Employee::dept, Collectors.groupingBy(Employee::level)));
        System.out.println("Eng levels=" + nested.get("Eng").keySet());

        Map<Boolean, List<Employee>> rich =
            employees.stream().collect(Collectors.partitioningBy(e -> e.salary() > 100_000));
        System.out.println("rich count=" + rich.get(true).size());

        // teeing: count + average in one pass
        record Stats(long count, double avg) {}
        Stats stats = employees.stream().collect(
            Collectors.teeing(
                Collectors.counting(),
                Collectors.averagingInt(Employee::salary),
                Stats::new
            )
        );
        System.out.println("stats=" + stats);

        // joining
        String csv = employees.stream().map(Employee::name).collect(Collectors.joining(", "));
        System.out.println("csv=" + csv);

        // toMap with merge
        Map<String, Integer> nameToSalary = employees.stream().collect(
            Collectors.toMap(Employee::name, Employee::salary, Integer::max));
        System.out.println(nameToSalary);

        // top earners per dept (pattern from hard Q22)
        Map<String, List<Employee>> top2 = employees.stream().collect(
            Collectors.groupingBy(Employee::dept,
                Collectors.collectingAndThen(Collectors.toList(), list ->
                    list.stream()
                        .sorted(Comparator.comparingInt(Employee::salary).reversed())
                        .limit(2)
                        .toList())));
        System.out.println("top2 Eng=" + top2.get("Eng"));

        DoubleSummaryStatistics summary = employees.stream()
            .collect(Collectors.summarizingDouble(Employee::salary));
        System.out.println("summary=" + summary);
    }
}
