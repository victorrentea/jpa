//package victor.training.jpa.app;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.MappedSuperclass;
//
//public class InheritanceExplicat {
//}
//
//class A {
//    @Id
//    private Long id; // campul asta nu apare in tabela lui A
//}
//@MappedSuperclass
//class B { // de ob se cheama AbstractEntity
//    @Id
//    private Long id; // orice camp al acestei clase va aparea in tabelele subclaselor
//}
//
//@Entity
////class E extends A { gresit
//class E extends B {
//}
//@Entity
//class E2 extends B {
//}
//
// /// ---- gata zenul ------------
//
//@Entity
//abstract class Animal extends B {
//    private String name; // apare si el il tabela DOG
//}
//@Entity
/////
//class Dog extends Animal {
//    String barkType;
//}
//@Entity
//class Cat extends Animal {
//    String meowType;
//}