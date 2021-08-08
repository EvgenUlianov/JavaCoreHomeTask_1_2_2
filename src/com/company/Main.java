package com.company;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        System.out.println("Задача 2: Перепись населения");


        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        long countNonAdult = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("несоврешеннолетних " + countNonAdult);

        long countСonscript = persons.stream()
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27)
                .filter(person -> person.getSex() == Sex.MAN)
                .count();
        System.out.println("призыников " + countСonscript);

        Predicate<Person> workableHigher = Person -> {
            Sex sex = Person.getSex();
            Integer age = Person.getAge();
            Education education = Person.getEducation();
            if (education != Education.HIGHER) return false;
            if (age < 18) return false;
            if ((sex == Sex.MAN) && (age <= 65)) return true;
            if ((sex == Sex.WOMAN) && (age <= 60)) return true;
            return false;
        }; // сделал так, потому что иначе нечитаемо

        Collection<Person> workablesHigher = persons.stream()
                .filter(workableHigher::test)
                .sorted(Person::compareTo) // не совсем по заданию, надеюсь, так можно?
                .collect(Collectors.toList());

        workablesHigher.stream()
                .limit(200) // иначе слишком много даже не глядя
                .forEach(System.out::println);
    }
}
