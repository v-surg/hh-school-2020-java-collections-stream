package tasks;

import common.Person;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  private long count;

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public List<String> getNames(List<Person> persons) {
    if (persons.size() == 0) {
      return Collections.emptyList();
    }
    //    persons.remove(0);
    //    наверное можно короче, но у меня классический вариант
    ArrayList<String> result = new ArrayList<>(persons.size());
    Iterator<Person> it = persons.iterator();
    for (it.next(); it.hasNext();){
      result.add(it.next().getFirstName());
    }
    return result;  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons)); // stream смотрится красиво, но кажется здесь это уже перебор
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {
    // говорят, лучше через Builder, но я точно не знаю, ибо javа не изучал вообще
    StringBuilder result = new StringBuilder();

    if (person.getFirstName() != null) {
      result.append(person.getFirstName());
    }

    if (person.getSecondName() != null) {
      if (result.length() > 0) // избавляемся от лишного пробела в начале
        result.append(" ");
      result.append(person.getSecondName());
    }
    return result.toString();
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    Map<Integer, String> map = new HashMap<>(persons.size()); // пожалуй, лучше задать размер сразу, а больше не знаю что менять
    for (Person person : persons) {
      if (!map.containsKey(person.getId())) {
        map.put(person.getId(), convertPersonToString(person));
      }
    }
    return map;
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    boolean has = false;
    for (Person person1 : persons1) {
      if (persons2.contains(person1)){ // наверное будет быстрее работать для map/set
        has = true;
        break; // это очевидно
      }
    }
    return has;
  }

  //...
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count(); // лаконичнеее
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = false;
    boolean reviewerDrunk = true;
    return codeSmellsGood || reviewerDrunk;
  }
}
