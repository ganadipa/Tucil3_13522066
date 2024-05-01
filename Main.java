

public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog("Bobby");
        System.out.println(dog.getName());

        changeName(dog, "Tommy");
        System.out.println(dog.getName());
    }

    public static void changeName(Dog dog, String name) {
        dog = new Dog(name);
        dog.setName(name);
    }
}
