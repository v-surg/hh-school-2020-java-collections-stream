package tasks;

import common.Person;
import common.Task;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 implements Task {

  // !!! Редактируйте этот метод !!!
  private static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                      Collection<Person> persons2,
                                                      int limit) {
    class PersonAgreaterB implements Comparator<Person> {
      @Override
      public int compare(Person A, Person B){
        return -1 * A.getCreatedAt().compareTo(B.getCreatedAt());
      }
    }

    class PersonAlessB implements Comparator<Person>{
      @Override
      public int compare(Person A, Person B){
        return A.getCreatedAt().compareTo(B.getCreatedAt());
      }
    }

    PersonAgreaterB comparator = new PersonAgreaterB();
    PriorityQueue<Person> pq = new PriorityQueue<Person>(limit, comparator);

    List<Collection<Person>> colls = List.of(persons1, persons2);

    for (Collection<Person> coll : colls) {
      for (Person p : coll) {
        if (pq.size() < limit) {
          pq.add(p);
        }
        else if (comparator.compare(p, pq.peek()) > 0) {
          pq.poll();
          pq.add(p);
        }
      }
    }

    ArrayList<Person> result = new ArrayList<>(pq);
    result.sort(new PersonAlessB());

    return result;
  }

  @Override
  public boolean check() {
    Instant time = Instant.now();
    Collection<Person> persons1 = Set.of(
        new Person(1, "Person 1", time),
        new Person(2, "Person 2", time.plusSeconds(1))
    );
    Collection<Person> persons2 = Set.of(
        new Person(3, "Person 3", time.minusSeconds(1)),
        new Person(4, "Person 4", time.plusSeconds(2))
    );
    return combineAndSortWithLimit(persons1, persons2, 3).stream()
        .map(Person::getId)
        .collect(Collectors.toList())
        .equals(List.of(3, 1, 2))
        && combineAndSortWithLimit(persons1, persons2, 5).stream()
        .map(Person::getId)
        .collect(Collectors.toList())
        .equals(List.of(3, 1, 2, 4));
  }
}
